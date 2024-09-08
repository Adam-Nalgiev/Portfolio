package com.pets.insplash.entity.constants

internal class Constants {

    companion object {
        const val GET_TOKEN_URL = "https://unsplash.com"
        const val AUTHORIZATION_BASE_URL = "https://unsplash.com/oauth/authorize"
        const val API_BASEURL = "https://api.unsplash.com"
        const val CLIENT_ID = "1uqN7imwX1vT0e-R2ALszCV2s0GA2gZT5vz2232tRFw"
        const val CLIENT_SECRET = ""
        const val REDIRECT_URI = "com.pets.insplash://auth"
        const val REQUEST_GRANT_TYPE = "authorization_code"
        const val RESPONSE_TYPE = "code"
        const val SCOPE = "read_user public write_user read_photos " +
                "write_photos write_likes write_followers" +
                " read_collections write_collections"


        const val APP_SHARED_PREF = "InSplash shared preference"
        const val ENCRYPTED_SHARED_PREF = "Encrypted shared preference"
        const val KEY_AUTH_STATE = "authorization state"
        const val KEY_ACCESS_TOKEN = "Access token"
    }

}