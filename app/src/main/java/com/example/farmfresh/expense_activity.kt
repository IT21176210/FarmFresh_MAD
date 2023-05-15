package com.example.farmfresh

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmfresh.Manager.AuthenticationManager
import com.example.farmfresh.Manager.DataManager
import com.example.farmfresh.Manager.FIRDatabaseManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult

class expense_activity : AppCompatActivity() {

    lateinit var recycleView: RecyclerView
    lateinit var adapter: ExpenseRecycleView

    var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            var isReload = result.data?.getBooleanExtra("reloadState",false)
            if (isReload == true){
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)
        val signInButton: ImageButton = findViewById(R.id.btnHome)
        signInButton.setOnClickListener {
            finish()
        }

        val addIncomeButton: Button = findViewById(R.id.addIncome)
        addIncomeButton.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            resultLauncher.launch(intent)
        }

        configView()
        if (DataManager.instance.isWantToLoadInExpense){
            configData()
        }
    }

    fun configView(){
        recycleView = findViewById(R.id.incomerecyclerView)
        recycleView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseRecycleView()
        recycleView.adapter = adapter
    }

    fun configData(){
        val userId = AuthenticationManager.instance.getUserId()
        if (userId != null) {
            val pd = ProgressDialog(this)
            pd.setMessage("loading");
            pd.show()
            FIRDatabaseManager.instance.getExpenseData(userId){
                adapter.notifyDataSetChanged()
                pd.dismiss()
            }
        }
    }

}