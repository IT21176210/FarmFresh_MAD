package com.example.farmfresh

import android.app.ProgressDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.farmfresh.Manager.AuthenticationManager
import com.example.farmfresh.Manager.DataManager
import com.example.farmfresh.Manager.FIRDatabaseManager
import ir.mahozad.android.PieChart

class FinancilOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financil_overview)
        loadData()
        configFinanceActivity()
        val incomeView: TextView = findViewById(R.id.incomeValue)
        val expenseView: TextView = findViewById(R.id.expenseValue)
        incomeView.text = ""
        expenseView.text = ""
        val homeButton: ImageButton = findViewById(R.id.backBtnOfViewProfile)
        homeButton.setOnClickListener {
            finish()
        }

    }

    fun loadData(){
        val userId:String = AuthenticationManager.instance.getUserId()!!
        if (DataManager.instance.isWantToLoadInCome || DataManager.instance.isWantToLoadInExpense){
            val pd = ProgressDialog(this)
            pd.setMessage("loading");
            pd.show()
            FIRDatabaseManager.instance.getIncomeData(userId){
                FIRDatabaseManager.instance.getExpenseData(userId){
                    configFinanceActivity()
                    pd.dismiss()
                }
            }
        }
    }

    fun configFinanceActivity(){
        var incomePer = getTotalIncome().toFloat()/(getTotalIncome() + getTotalExpense()).toFloat()
        var expensePer = getTotalExpense().toFloat()/(getTotalIncome() + getTotalExpense()).toFloat()

        val pieChart = findViewById<PieChart>(R.id.pieChart)
        pieChart.slices = listOf(
            PieChart.Slice(expensePer, Color.RED),
            PieChart.Slice(incomePer, Color.BLUE)
        )

        val incomeView: TextView = findViewById(R.id.incomeValue)
        val expenseView: TextView = findViewById(R.id.expenseValue)
        incomeView.text = "Income "  + getTotalIncome().toString()
        expenseView.text = "Expense " + getTotalExpense().toString()
    }

    fun getTotalExpense():Int{
        var amount = 0
        for (data in DataManager.instance.expenseDataArray){
            amount += data.amount?.toInt() ?: 0
        }
        return  amount
    }

    fun getTotalIncome():Int{
        var amount = 0
        for (data in DataManager.instance.incomeDataArray){
            amount += data.amount?.toInt() ?: 0
        }
        return  amount
    }


}