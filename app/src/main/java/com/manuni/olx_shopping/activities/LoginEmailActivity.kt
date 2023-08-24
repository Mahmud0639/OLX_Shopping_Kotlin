package com.manuni.olx_shopping.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.manuni.olx_shopping.R
import com.manuni.olx_shopping.databinding.ActivityLoginEmailBinding
import com.manuni.olx_shopping.utils.AppUsed

class LoginEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEmailBinding

    private companion object{
        private const val TAG = "LOGIN_TAG"
    }
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.notHaveAccount.setOnClickListener {
            startActivity(Intent(this,RegisterEmailActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            validateData()
        }




    }

    var email = ""
    var password = ""

    private fun validateData(){
        email = binding.emailTextInputL.editText?.text.toString().trim()
        password = binding.passTextInputL.editText?.text.toString().trim()

        Log.d(TAG, "validateData: email: $email")
        Log.d(TAG, "validateData: password: $password")

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailTextInputL.editText?.error = "Invalid email format"
            binding.emailTextInputL.editText?.requestFocus()
        }else if (TextUtils.isEmpty(password)){
            binding.passTextInputL.editText?.error = "Field can't be empty"
            binding.passTextInputL.editText?.requestFocus()
        }else{
            loginUser()
        }
    }
    private fun loginUser(){
        Log.d(TAG, "loginUser: Logging")
        progressDialog.setMessage("Logging in...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            progressDialog.dismiss()
            Log.d(TAG, "loginUser: Logged In")
            startActivity(Intent(this@LoginEmailActivity,MainActivity::class.java))
            finishAffinity()
            AppUsed.toast(this@LoginEmailActivity,"Successfully Logged In")

        }.addOnFailureListener {e->
            Log.e(TAG, "loginUser: ${e.message}" )
            progressDialog.dismiss()

        }
    }
}