package com.pets.insplash.presentation.authorization

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.pets.insplash.domain.GetTokenUseCase
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.TokenBodyDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthorizationViewModel: ViewModel() {

    private val _isAuthSuccess = Channel<Boolean>()
    val isAuthSuccess = _isAuthSuccess.receiveAsFlow()

    fun openBrowser(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, composeUrl())
        context.startActivity(intent)
    }

    fun handleDeepLink(intent: Intent, context: Context) {
        Log.d("ing", "DEEP LINKING STARTED")
        Log.d("Deep Link CONTEXT", "$context")
        val deepLinkUrl = intent.data
        if (intent.action != Intent.ACTION_VIEW || deepLinkUrl == null) return
        Log.d("DEEP LINK", "$intent")
        if (deepLinkUrl.queryParameterNames.contains(Constants.RESPONSE_TYPE)) {
            val authorCode = deepLinkUrl.getQueryParameter(Constants.RESPONSE_TYPE) ?: return
            getToken(authorCode, context)
        }
    }

    private fun composeUrl(): Uri =
        Uri.parse(Constants.AUTHORIZATION_BASE_URL)
            .buildUpon()
            .appendQueryParameter("client_id", Constants.CLIENT_ID)
            .appendQueryParameter("redirect_uri", Constants.REDIRECT_URI)
            .appendQueryParameter("response_type", Constants.RESPONSE_TYPE)
            .appendQueryParameter("scope", Constants.SCOPE)
            .build()

    private fun getToken(authCode: String, context: Context) {
        Log.d("GET TOKEN CONTEXT", "$context")
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                GetTokenUseCase().execute(TokenBodyDTO(code = authCode))
            }.fold(
                onFailure = {
                    Log.d("GET TOKEN", "GET TOKEN IS FAILURE $it")
                    saveAuthState(context, null)
                    _isAuthSuccess.send(false)
                },
                onSuccess = {
                    Log.d("GET TOKEN", "GET TOKEN IS SUCCESS $it")
                    saveAuthState(context, it.access_token)
                    _isAuthSuccess.send(true)
                })
        }
    }

    private fun saveToken(context: Context, token: String) {
        val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            Constants.KEY_ENCRYPTED_SHARED_PREF,
            masterKeys,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ).edit()

        encryptedSharedPreferences.putString(Constants.KEY_TOKEN, token).apply()
    }

    private fun saveAuthState(context: Context, token: String?) {
        val sharedPreferences = context.getSharedPreferences(
            Constants.KEY_APP_SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        ).edit()

        if (token != null) {
            saveToken(context, token)
            sharedPreferences.putBoolean(Constants.KEY_IS_AUTHORIZED, true)
            sharedPreferences.apply()
        } else {
            sharedPreferences.putBoolean(Constants.KEY_IS_AUTHORIZED, false)
            sharedPreferences.apply()
        }
    }


}