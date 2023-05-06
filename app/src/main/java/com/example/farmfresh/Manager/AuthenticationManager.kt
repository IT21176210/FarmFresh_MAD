package com.example.farmfresh.Manager

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.content.Intent
import android.util.Log
import android.content.ContentValues.TAG
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.tasks.Task

class AuthenticationManager {

    companion object {
        val instance = AuthenticationManager()
    }

    fun isSignIn():Boolean{
     var auth  =  Firebase.auth
       if (auth.currentUser != null){
           return  true
       }
        return  false
    }

    fun getUserId():String?{
        var auth  =  Firebase.auth
        if (auth.currentUser != null){
            return  auth.currentUser?.uid
        }
        return  null
    }

}

