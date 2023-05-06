package com.example.farmfresh

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmfresh.Manager.AuthenticationManager
import com.example.farmfresh.Manager.DataManager
import com.example.farmfresh.Manager.FIRDatabaseManager
import com.example.farmfresh.Manager.IncomeData

class AddIncomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_income_activity)
        val signInButton: Button = findViewById(R.id.addIncome)
        signInButton.setOnClickListener {
            submitIncomeData()
        }

        val homeButton: ImageButton = findViewById(R.id.backBtnOfViewProfile)
        homeButton.setOnClickListener {
            finish()
        }

        val resetButton: Button = findViewById(R.id.reset)
        resetButton.setOnClickListener {
            val source: EditText = findViewById(R.id.getType)
            val data: EditText = findViewById(R.id.getDate)
            val amount: EditText = findViewById(R.id.getAmount)
            val clint: EditText = findViewById(R.id.clint)
            val discription: EditText = findViewById(R.id.description)
            source.setText("")
            data.setText("")
            amount.setText("")
            clint.setText("")
            discription.setText("")
        }

    }

    fun submitIncomeData(){
        val source: EditText = findViewById(R.id.getType)
        val data: EditText = findViewById(R.id.getDate)
        val amount: EditText = findViewById(R.id.getAmount)
        val clint: EditText = findViewById(R.id.clint)
        val discription: EditText = findViewById(R.id.description)

        val sourceString: String = source.text.toString()
        val dataString: String = data.text.toString()
        val amountString: String = amount.text.toString()
        val clintString: String = clint.text.toString()
        val discriptionString: String = discription.text.toString()

        if (isValidText(sourceString)){
            if (isValidText(dataString)) {
                if (isValidText(clintString)) {
                    if (isValidText(discriptionString)) {
                        if (isValidText(amountString) && isNumeric(amountString)){
                            val incomeDate = IncomeData("",sourceString,dataString,amountString,clintString,discriptionString)
                            FIRDatabaseManager.instance.updateIncome(incomeDate,AuthenticationManager.instance.getUserId()!!){
                                if (it){
                                    Toast.makeText(this, "Sucess", 2000).show();
                                    DataManager.instance.incomeDataArray.add(incomeDate)
                                    var intent = getIntent()
                                    intent.putExtra("reloadState",true)
                                    setResult(Activity.RESULT_OK,intent)
                                    finish()
                                }else{
                                    Toast.makeText(this, "Fail", 2000).show();
                                }
                            }
                        }else{
                            Toast.makeText(this, "your amount text is not valid", 2000).show();
                        }
                    }else{
                        Toast.makeText(this, "your discription text is not valid", 2000).show();
                    }
                }else{
                    Toast.makeText(this, "your clint text is not valid", 2000).show();
                }
            }else{
                Toast.makeText(this, "your date is not valid", 2000).show();
            }
        }else{
            //sourse error
            Toast.makeText(this, "your source is not valid", 2000).show();
        }

    }

    internal fun isValidText(password: String): Boolean {
        if (password.length <= 0) return false
        return true
    }

    fun isNumeric(toCheck: String): Boolean {
        return toCheck.all { char -> char.isDigit() }
    }

}