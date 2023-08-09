package com.example.learing1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {


  private  val ksa:String="KSA"
  private  val usa:String="USA"
  private  val gbp:String="GBP"
  private  val egy:String="EGY"
  private  var dropDownFrom:AutoCompleteTextView?=null
  private  var dropDownTo: AutoCompleteTextView ?=null
  private var resultEt:TextInputEditText?=null
  private  var  convertBtn:Button?=null
  private var amountEt:TextInputEditText?=null
  private var toolBar:Toolbar?=null

  private val values= mapOf(
      ksa to 1.0,
      usa to 0.2665,
      gbp to 0.2094,
      egy to 8.23
  )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

           initViews()
           dropDown()
           menuOp()

           amountEt!!.addTextChangedListener {
               resultCoins()
           }


           dropDownFrom!!.setOnItemClickListener { parent, view, position, id ->
             resultCoins()
           }

           dropDownTo!!.setOnItemClickListener { parent, view, position, id ->
            resultCoins()
        }
    }

    private fun resultCoins(){
        if (amountEt!!.text.toString().isNotEmpty()){
            val amount:Double=amountEt!!.text.toString().toDouble()
            val toDropDown=  values[dropDownTo!!.text.toString()]
            val fromDropDown=values[dropDownFrom!!.text.toString()]
            val result =amount!!.times(toDropDown!!.div(fromDropDown!!))
            val formatRes=String.format("%.2f",result)
            resultEt!!.setText(formatRes)

        }else{
            amountEt!!.error = "Amount filed require"
        }
    }

    private  fun dropDown(){
        val listCoins= listOf(ksa , usa , gbp , egy)
        val adapter =ArrayAdapter(this , R.layout.drop_down_list , listCoins)
        dropDownTo!!.setAdapter(adapter)
        dropDownFrom!!.setAdapter(adapter)
    }

    private fun initViews(){
        resultEt=findViewById(R.id.resultEditText)
        convertBtn = findViewById(R.id.convertBtn)
        amountEt=findViewById(R.id.amountEditText)
        dropDownFrom=findViewById(R.id.dropDown1)
        dropDownTo=findViewById(R.id.filed2)
        toolBar=findViewById(R.id.toolBar)
    }

     private  fun menuOp(){

      toolBar!!.inflateMenu(R.menu.option_menu)
      toolBar!!.setOnMenuItemClickListener { item ->
          when(item.itemId){
              R.id.shareAction ->  {
                  val message="${amountEt!!.text.toString()} ${dropDownFrom!!.text.toString()} is equal to ${resultEt!!.text.toString()} ${dropDownTo!!.text.toString()} "
                  val sendIntent=Intent(Intent.ACTION_SEND)
                  sendIntent.type="text/plain"
                  sendIntent.putExtra(Intent.EXTRA_TEXT , message)
                  if (sendIntent.resolveActivity(packageManager)!=null){
                      startActivity(sendIntent)
                  }else{
                      Toast.makeText(this , "error" , Toast.LENGTH_SHORT).show()
                  }
              }
          }
          true
      }
  }
}