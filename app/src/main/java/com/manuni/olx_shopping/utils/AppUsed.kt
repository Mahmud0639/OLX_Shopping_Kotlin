package com.manuni.olx_shopping.utils

import android.content.Context
import android.widget.Toast

object AppUsed {

    fun getTimestamp():Long{
        return System.currentTimeMillis()
    }

    fun toast(context:Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()

    }
}