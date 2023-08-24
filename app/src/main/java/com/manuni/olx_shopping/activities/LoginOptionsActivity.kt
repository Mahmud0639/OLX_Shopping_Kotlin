package com.manuni.olx_shopping.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manuni.olx_shopping.R
import com.manuni.olx_shopping.databinding.ActivityLoginOptionsBinding

class LoginOptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeBtn.setOnClickListener{
            onBackPressed()
        }

        binding.continueWithEmail.setOnClickListener {
            startActivity(Intent(this@LoginOptionsActivity,RegisterEmailActivity::class.java))
        }


    }
}