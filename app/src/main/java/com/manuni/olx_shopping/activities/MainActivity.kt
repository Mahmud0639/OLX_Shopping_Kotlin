package com.manuni.olx_shopping.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.manuni.olx_shopping.R
import com.manuni.olx_shopping.databinding.ActivityMainBinding
import com.manuni.olx_shopping.fragments.AccountFragment
import com.manuni.olx_shopping.fragments.ChatsFragment
import com.manuni.olx_shopping.fragments.HomeFragment
import com.manuni.olx_shopping.fragments.MyAdsFragment
import com.manuni.olx_shopping.utils.AppUsed


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser == null){
            goForLoginOptions()
        }


        showHomeFragment()

        binding.bottomNav.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.menu_home ->{
                    showHomeFragment()
                    true
                }
                R.id.chats ->{
                    if (firebaseAuth.currentUser==null){
                        AppUsed.toast(this,"You need to sign in first")
                        goForLoginOptions()
                        false
                    }else{
                        showChatsFragment()
                        true
                    }

                }
                R.id.my_ads ->{
                    if (firebaseAuth.currentUser==null){
                        AppUsed.toast(this,"You need to sign in first")
                        goForLoginOptions()
                        false
                    }else{
                        showMyAdsFragment()
                        true
                    }

                }
                R.id.account ->{

                    if (firebaseAuth.currentUser==null){
                        AppUsed.toast(this,"You need to sign in first")
                        goForLoginOptions()
                        false
                    }else{
                        showAccountFragment()
                        true
                    }

                }else->{
                    false
                }
            }
        }

    }


    @SuppressLint("CommitTransaction")
    private fun showHomeFragment(){
        binding.toolBarTxt.text = "Home"

        val homeFragment = HomeFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentsFL.id,homeFragment,"HomeFragment")
        fragmentTransaction.commit()
    }

    private fun showChatsFragment(){
        binding.toolBarTxt.text = "Chats"
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentsFL.id,ChatsFragment(),"ChatsFragment")
        fragmentTransaction.commit()
    }

    private fun showMyAdsFragment(){
        binding.toolBarTxt.text = "My Ads"
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentsFL.id,MyAdsFragment(),"MyAdsFragment")
        fragmentTransaction.commit()
    }

    private fun showAccountFragment(){
        binding.toolBarTxt.text = "Account"
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentsFL.id,AccountFragment(),"AccountFragment")
        fragmentTransaction.commit()
    }
    private fun goForLoginOptions(){
        startActivity(Intent(this,LoginOptionsActivity::class.java))
    }
}