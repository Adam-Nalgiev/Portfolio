package com.pets.insplash.presentation.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.pets.insplash.entity.constants.Constants

class HomeViewModel : ViewModel() {


    fun authState(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(Constants.KEY_APP_SHARED_PREF, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getBoolean(Constants.KEY_IS_AUTHORIZED, false)
    }
}