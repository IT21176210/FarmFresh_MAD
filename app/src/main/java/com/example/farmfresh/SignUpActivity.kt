package com.example.farmfresh

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmfresh.Manager.FIRDatabaseManager
import com.example.farmfresh.Manager.User
import com.example.farmfresh.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.signup_activity)
        val iconImage: ImageView = findViewById(R.id.imageViewHomeIcon)
        iconImage.setImageResource(R.drawable.logo_img)
        val signUpButton: Button = findViewById(R.id.b1)
        signUpButton.setOnClickListener {
            createAccount()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload()
        }
    }

    fun createAccount(){
        val emailView:EditText = findViewById(R.id.phoneoremail)
        val password:EditText = findViewById(R.id.password)
        var cenformPassword:EditText = findViewById(R.id.con_password)
        val fullNameView:EditText = findViewById(R.id.fullname)
        val farmName:EditText = findViewById(R.id.farmname)
        val emailText = emailView.text
        val  paswordText = password.text
        val passwordCenform = cenformPassword.text
        val user = User("",fullNameView.text.toString(),"","",emailText.toString(),farmName.text.toString())

        if (!isValidEmail(emailText)) {
            Toast.makeText(this, "your email is not valid", 2000).show();
        }else{
            if (isValidPassword(paswordText.toString())){
                if (paswordText.toString() == passwordCenform.toString()){
                    createAccount(emailText.toString(),paswordText.toString(),user)
                }else{
                    Toast.makeText(this, "your password is not valid", 2000).show();
                }
            }else{
                Toast.makeText(this, "your password is not valid", 2000).show();
            }
        }

    }

    private fun createAccount(email: String, password: String,userData: User) {
        // [START create_user_with_email]
        val pd = ProgressDialog(this)
        pd.setMessage("loading");
        pd.show()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    var userModel = userData
                    userModel.userId = user!!.uid

                    FIRDatabaseManager.instance.updateUserData(userModel,userModel.userId){
                        pd.dismiss()
                        var activity = MainActivity()
                        val a = Intent(this, activity::class.java)
                        startActivity(a)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    pd.dismiss()
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                  //  updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    internal fun isValidPassword(password: String): Boolean {
        if (password.length < 6) return false

        return true
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}
