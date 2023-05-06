package com.example.farmfresh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.example.farmfresh.Manager.AuthenticationManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val iconImage: ImageView = findViewById(R.id.imageViewHomeIcon)
        iconImage.setImageResource(R.drawable.launch)
        val handler = Handler()
        handler.postDelayed(Runnable { // Do something after 5s = 5000ms
            if (AuthenticationManager.instance.isSignIn()){
                var activity = MainActivity()
                val a = Intent(this, activity::class.java)
                startActivityForResult(a, 1)
            }else {
                var activity = WelComeActivity()
                val a = Intent(this, activity::class.java)
                startActivityForResult(a, 1)
            }
        }, 1200)

    }
}