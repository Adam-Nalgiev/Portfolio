package com.pets.insplash.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NetworkClient @Inject constructor() {

    val registrationRequest: RegistrationAPI = Retrofit.Builder()
        .baseUrl(REGISTRATION_BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RegistrationAPI::class.java)

    val request: API = Retrofit.Builder()
        .baseUrl(API_BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(API::class.java)

    companion object {
        const val REGISTRATION_BASEURL = "https://unsplash.com"
        const val API_BASEURL = "https://api.unsplash.com"
    }
}