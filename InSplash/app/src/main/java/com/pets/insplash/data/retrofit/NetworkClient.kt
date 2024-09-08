package com.pets.insplash.data.retrofit

import com.pets.insplash.entity.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {

    companion object {
        val retrofitReg: RegistrationAPI = Retrofit.Builder()
            .baseUrl(Constants.GET_TOKEN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RegistrationAPI::class.java)

        val retrofit: API = Retrofit.Builder()
            .baseUrl(Constants.API_BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }
}