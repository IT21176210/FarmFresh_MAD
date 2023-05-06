package com.example.farmfresh

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.farmfresh.Manager.DataManager
import com.example.farmfresh.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        val mybtnButton: Button = findViewById(R.id.btnMyAccount)
        mybtnButton.setOnClickListener {
            var activity = MyAccountActivity()
            val a = Intent(this, activity::class.java)
            startActivity(a)
        }

        val incomeButton: Button = findViewById(R.id.btnIncome)
        incomeButton.setOnClickListener {
            var activity = IncomeActivity()
            val a = Intent(this, activity::class.java)
            startActivity(a)
        }

        val expensesButton: Button = findViewById(R.id.btnExpenses)
        expensesButton.setOnClickListener {
            var activity = expense_activity()
            val a = Intent(this, activity::class.java)
            startActivity(a)
        }

        val overViewButton: Button = findViewById(R.id.btnOverview)
        overViewButton.setOnClickListener {
            var activity = FinancilOverviewActivity()
            val a = Intent(this, activity::class.java)
            startActivity(a)
        }

        val reportButton: Button = findViewById(R.id.btnReports)
        reportButton.setOnClickListener {
            var activity = ReportsActivity()
            val a = Intent(this, activity::class.java)
            startActivity(a)
        }

        val inventoryButton: Button = findViewById(R.id.btnInventory)
        inventoryButton.setOnClickListener {
            var activity = InventoryActivity()
            val a = Intent(this, activity::class.java)
            startActivity(a)
        }

        val signUpButton: Button = findViewById(R.id.btnSignOut)
        signUpButton.setOnClickListener {
            var auth = Firebase.auth
            auth.signOut()
            DataManager.instance.clearManager()
            var activity = WelComeActivity()
            val a = Intent(this, activity::class.java)
            startActivity(a)
        }

    }
}