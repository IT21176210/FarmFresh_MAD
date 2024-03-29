package com.example.farmfresh

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmfresh.Manager.AuthenticationManager
import com.example.farmfresh.Manager.DataManager
import com.example.farmfresh.Manager.FIRDatabaseManager

class ReportsActivity : AppCompatActivity() {

    lateinit var recycleView: RecyclerView
    lateinit var adapter: ReportRecycleActivity

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
        setContentView(R.layout.activity_reports)
        val signInButton: ImageButton = findViewById(R.id.btnHome)
        signInButton.setOnClickListener {
            finish()
        }

        val addReportutton: Button = findViewById(R.id.addReport)
        addReportutton.setOnClickListener {
            val intent = Intent(this, AddReportsActivity::class.java)
            resultLauncher.launch(intent)
        }

        configView()
        if (DataManager.instance.isWantToLoadReport){
            configData()
        }

    }

    fun configData(){
        val userId = AuthenticationManager.instance.getUserId()
        if (userId != null) {
            val pd = ProgressDialog(this)
            pd.setMessage("loading");
            pd.show()
            FIRDatabaseManager.instance.getReportsData(userId){
                adapter.notifyDataSetChanged()
                pd.dismiss()
            }
        }
    }

    fun configView(){
        recycleView = findViewById(R.id.reportrecyclerView)
        recycleView.layoutManager = LinearLayoutManager(this)

        adapter = ReportRecycleActivity()
        recycleView.adapter = adapter
    }

}