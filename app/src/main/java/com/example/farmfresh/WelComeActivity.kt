package com.example.farmfresh

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.farmfresh.databinding.ActivityMainBinding
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import com.example.farmfresh.Manager.FIRDatabaseManager
import com.example.farmfresh.Manager.IncomeData

class WelComeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.wellcome_activity)
        val iconImage: ImageView = findViewById(R.id.imageViewHomeIcon)
        iconImage.setImageResource(R.drawable.logo_img)

        val signInButton: Button = findViewById(R.id.signIn)
        val signUpButton: Button = findViewById(R.id.signUp)
        signInButton.setOnClickListener {
            var activity = SignInActivity()
            val a = Intent(this, activity::class.java)
            startActivity(a)
//            val income = IncomeData("","aaa","bbb","2000","ccc","dhhdhd")
//            FIRDatabaseManager.instance.updateIncome(income,"sanjee")

        }

        signUpButton.setOnClickListener {
            var activity = SignUpActivity()
            val a = Intent(this, activity::class.java)
            startActivity(a)
        }

    }

}