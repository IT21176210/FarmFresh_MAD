package com.example.farmfresh

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.farmfresh.Manager.AuthenticationManager
import com.example.farmfresh.Manager.FIRDatabaseManager
import com.example.farmfresh.Manager.User
import com.example.farmfresh.databinding.ActivityMainBinding

class MyAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var isEditable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_account_activity)
        val signInButton: ImageButton = findViewById(R.id.btnHome)
        signInButton.setOnClickListener {
            finish()
        }

        val fullNameButton: EditText = findViewById(R.id.fullname)
        val phoneButton: EditText = findViewById(R.id.phone)
        val emailButton: EditText = findViewById(R.id.email)
        val frameFrashButton: EditText = findViewById(R.id.farmname)
        val dateButton: EditText = findViewById(R.id.date)
//        fullNameButton.setFocusable(false)
//        phoneButton.setFocusable(false)
//        emailButton.setFocusable(false)
//        frameFrashButton.setFocusable(false)
//        dateButton.setFocusable(false)

         configData()

        val editButton: Button = findViewById(R.id.bunnon)
        editButton.setOnClickListener {
//            if (!isEditable){
//                isEditable = true
//                fullNameButton.setFocusable(true)
//                phoneButton.setFocusable(true)
//                emailButton.setFocusable(true)
//                frameFrashButton.setFocusable(true)
//                dateButton.setFocusable(true)
//            }else{
                updateUser()
           // }

        }
    }

    fun updateUser(){
        val fullNameButton: EditText = findViewById(R.id.fullname)
        val phoneButton: EditText = findViewById(R.id.phone)
        val frameFrashButton: EditText = findViewById(R.id.farmname)
        val dateButton: EditText = findViewById(R.id.date)
        val emailButton: EditText = findViewById(R.id.email)

        val fullNameButtonStr: String = fullNameButton.text.toString()
        val phoneButtonStr: String = phoneButton.text.toString()
        val frameFrashButtonStr: String = frameFrashButton.text.toString()
        val dateButtonStr: String = dateButton.text.toString()
        val useId = AuthenticationManager.instance.getUserId()
        var email:String = emailButton.text.toString()

        val user = User(useId!!,fullNameButtonStr,dateButtonStr,phoneButtonStr,email,frameFrashButtonStr)
        val pd = ProgressDialog(this)
        pd.setMessage("loading");
        pd.show()
        FIRDatabaseManager.instance.updateUserData(user,useId){
            pd.dismiss()
            finish()
        }

    }

    fun configData(){
        val useId = AuthenticationManager.instance.getUserId()!!
        val pd = ProgressDialog(this)
        pd.setMessage("loading");
        pd.show()
        FIRDatabaseManager.instance.getUserData(useId){
            pd.dismiss()
            val fullNameButton: EditText = findViewById(R.id.fullname)
            val phoneButton: EditText = findViewById(R.id.phone)
            val emailButton: EditText = findViewById(R.id.email)
            val frameFrashButton: EditText = findViewById(R.id.farmname)
            val dateButton: EditText = findViewById(R.id.date)

            fullNameButton.setText(it.fullName)
            phoneButton.setText(it.phone)
            emailButton.setText(it.email)
            frameFrashButton.setText(it.farmName)
            dateButton.setText(it.date)
        }
    }

}