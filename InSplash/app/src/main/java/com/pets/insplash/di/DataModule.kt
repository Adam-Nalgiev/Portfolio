package com.pets.insplash.di

import android.content.SharedPreferences
import com.pets.insplash.data.retrofit.NetworkClient
import com.pets.insplash.entity.constants.Constants
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideHeader (sharedPreferences: SharedPreferences): String {
        val token = sharedPreferences.getString(Constants.KEY_ENCRYPTED_SHARED_PREF, "")
        return "Bearer $token"
    }

    @Provides
    fun provideNetworkClient(): NetworkClient {
        return NetworkClient()
    }
}