package com.example.farmfresh

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.farmfresh.Manager.*

class AddReportsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reports)

        val homeButton: ImageButton = findViewById(R.id.backBtnOfViewProfile)
        homeButton.setOnClickListener {
            finish()
        }

        val signInButton: Button = findViewById(R.id.addReprt)
        signInButton.setOnClickListener {
            submitIncomeData()
        }

    }

    fun submitIncomeData(){
        val getDate: EditText = findViewById(R.id.getDate)
        val income: EditText = findViewById(R.id.Income)
        val expenses: EditText = findViewById(R.id.Expenses)
        val description: EditText = findViewById(R.id.description)

        val dateString: String = getDate.text.toString()
        val incomeString: String = income.text.toString()
        val expensString: String = expenses.text.toString()
        val discriptionString: String = description.text.toString()

        if (isValidText(discriptionString)){
            if (isValidText(dateString) && isNumeric(dateString)) {
                if (isValidText(incomeString) && isNumeric(incomeString)) {
                    if (isValidText(expensString) && isNumeric(expensString)) {
                            val reportDate = ReportData("",incomeString,expensString,discriptionString,dateString)
                            FIRDatabaseManager.instance.updateReportData(reportDate,
                                AuthenticationManager.instance.getUserId()!!){
                                if (it){
                                    Toast.makeText(this, "Sucess", 2000).show();
                                    DataManager.instance.reportDataArray.add(reportDate)
                                    var intent = getIntent()
                                    intent.putExtra("reloadState",true)
                                    setResult(Activity.RESULT_OK,intent)
                                    finish()
                                }else{
                                    Toast.makeText(this, "Fail", 2000).show();
                                }
                            }
                        }else{
                        //sourse error
                        Toast.makeText(this, "your date is not valid", 2000).show();
                    }
                    }else{
                        Toast.makeText(this, "your expense text is not valid", 2000).show();
                    }
                }else{
                    Toast.makeText(this, "your income text is not valid", 2000).show();
                }
            }else{
                Toast.makeText(this, "your discription is not valid", 2000).show();
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