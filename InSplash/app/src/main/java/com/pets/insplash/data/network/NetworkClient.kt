package com.pets.insplash.data.network

import com.pets.insplash.entity.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NetworkClient @Inject constructor() {

    val registrationRequest: RegistrationAPI = Retrofit.Builder()
        .baseUrl(Constants.REGISTRATION_BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RegistrationAPI::class.java)

    val request: API = Retrofit.Builder()
        .baseUrl(Constants.API_BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(API::class.java)

}