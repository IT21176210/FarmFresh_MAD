package com.example.farmfresh

import InComeRecycleView
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmfresh.Manager.AuthenticationManager
import com.example.farmfresh.Manager.DataManager
import com.example.farmfresh.Manager.FIRDatabaseManager


class IncomeActivity : AppCompatActivity() {

    lateinit var recycleView: RecyclerView
    lateinit var adapter: InComeRecycleView

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            var isReload = result.data?.getBooleanExtra("reloadState",false)
            if (isReload == true){
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.income_activity)
        val signInButton: ImageButton = findViewById(R.id.btnHome)
        signInButton.setOnClickListener {
            finish()
        }

        val addIncomeButton: Button = findViewById(R.id.addIncome)
        addIncomeButton.setOnClickListener {
            val intent = Intent(this, AddIncomeActivity::class.java)
            resultLauncher.launch(intent)
        }

        configView()
        if (DataManager.instance.isWantToLoadInCome){
            configData()
        }

    }

    fun configView(){
        recycleView = findViewById(R.id.incomerecyclerView)
        recycleView.layoutManager = LinearLayoutManager(this)

        adapter = InComeRecycleView()
        recycleView.adapter = adapter
    }

    fun configData(){
        val userId = AuthenticationManager.instance.getUserId()
        if (userId != null) {
            val pd = ProgressDialog(this)
            pd.setMessage("loading");
            pd.show()
            FIRDatabaseManager.instance.getIncomeData(userId){
                adapter.notifyDataSetChanged()
                pd.dismiss()
            }
        }
    }


}