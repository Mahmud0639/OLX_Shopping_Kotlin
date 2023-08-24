package com.manuni.olx_shopping.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.manuni.olx_shopping.R
import com.manuni.olx_shopping.databinding.ActivityRegisterEmailBinding
import com.manuni.olx_shopping.utils.AppUsed

class RegisterEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterEmailBinding
    private lateinit var progressDialog:ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String = ""
    private var password: String = ""
    private var confirmPass: String = ""


    companion object{
        private const val TAG = "MyTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.alreadyHaveAccount.setOnClickListener {
            startActivity(Intent(this@RegisterEmailActivity,LoginEmailActivity::class.java))
        }

            binding.registerBtn.setOnClickListener {
            email = binding.emailTextInputL.editText?.text.toString().trim()
            password = binding.passTextInputL.editText?.text.toString().trim()
            confirmPass = binding.confirmPassTextInputL.editText?.text.toString().trim()

            validateData(email,password,confirmPass)
        }
    }



    private fun register(uEmail:String,uPass:String){
        progressDialog.setMessage("Creating account...")
        progressDialog.show()

       firebaseAuth.createUserWithEmailAndPassword(uEmail,uPass).addOnSuccessListener {


           uploadUserInfo()

       }.addOnFailureListener{e->
           Log.d(TAG, "register: ${e.message}")
           progressDialog.dismiss()
       }

    }

    private fun uploadUserInfo(){
        progressDialog.setMessage("Saving user info...")
        val timeStamp = AppUsed.getTimestamp()
        val registeredUserEmail = firebaseAuth.currentUser!!.email
        val registeredUserId = firebaseAuth.uid

        val hashMap = HashMap<String,Any>()
        hashMap["name"] = ""
        hashMap["phoneCode"] = ""
        hashMap["phoneNumber"] = ""
        hashMap["profileImageUrl"] = ""
        hashMap["dob"] = ""
        hashMap["userType"] = "email"
        hashMap["typingTo"] = ""
        hashMap["timestamp"] = timeStamp
        hashMap["onlineStatus"] = true
        hashMap["email"] = "$registeredUserEmail"
        hashMap["uid"] = "$registeredUserId"

        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.child(registeredUserId!!).setValue(hashMap).addOnSuccessListener {
            progressDialog.dismiss()
            startActivity(Intent(this@RegisterEmailActivity,MainActivity::class.java))
           finishAffinity()

        }.addOnFailureListener{e->
            Log.e(TAG, "uploadUserInfo: ${e.message}", )

        }


    }

    private fun validateData(userEmail:String,userPass:String,userConfirmPass:String){
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            binding.emailTextInputL.error = "Invalid email format"
            binding.emailTextInputL.requestFocus()
        }else if (TextUtils.isEmpty(userEmail)){
            binding.passTextInputL.error = "Filed can't be empty"
            binding.passTextInputL.requestFocus()
        }else if (TextUtils.isEmpty(userPass)){
            binding.passTextInputL.error = "Field can't be empty"
            binding.passTextInputL.requestFocus()
        }else if (userPass.length<6) {
            AppUsed.toast(this,"Password is too short.")
        } else if (TextUtils.isEmpty(userConfirmPass)){
            binding.confirmPassTextInputL.error = "Field can't be empty"
            binding.confirmPassTextInputL.requestFocus()
        }else{
            if (userPass.equals(userConfirmPass)){
                register(userEmail,userPass)
            }else{
                AppUsed.toast(this,"Password doesn't matched.")
            }

        }
    }

}