package com.pets.insplash.entity.constants

internal object Constants {

    const val REGISTRATION_BASEURL = "https://unsplash.com"
    const val AUTHORIZATION_URL = "$REGISTRATION_BASEURL/oauth/authorize"
    const val API_BASEURL = "https://api.unsplash.com"
    const val CLIENT_ID = "1uqN7imwX1vT0e-R2ALszCV2s0GA2gZT5vz2232tRFw"
    const val CLIENT_SECRET = "I56hShjIiIjYnVPKItVPcHHo6OiGO7JF_5EOpacTP-k"
    const val REDIRECT_URI = "com.pets.insplash://auth"
    const val REQUEST_GRANT_TYPE = "authorization_code"
    const val RESPONSE_TYPE = "code"
    const val SCOPE = "read_user public write_user read_photos " +
        "write_photos write_likes write_followers" +
        " read_collections write_collections"

    const val KEY_ENCRYPTED_SHARED_PREF = "Secret data"
    const val KEY_APP_SHARED_PREF = "InSplash shared preferences"
    const val KEY_IS_AUTHORIZED = "Is authorized"
    const val KEY_TOKEN = "App token"
    const val KEY_BUNDLE_PHOTO_ID = "Photos ID"
    const val KEY_BUNDLE_COLLECTION_ID = "Collection ID"

}