package com.pets.insplash.presentation.screen.authorization

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pets.insplash.data.network.dto.TokenBodyDTO
import com.pets.insplash.domain.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(private val getTokenUseCase: GetTokenUseCase) :
    ViewModel() {

    private val _isAuthSuccess = Channel<Boolean>()
    val isAuthSuccess = _isAuthSuccess.receiveAsFlow()

    fun openBrowser(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, composeUrl())
        context.startActivity(intent)
    }

    fun handleDeepLink(intent: Intent) {
        val deepLinkUrl = intent.data
        if (intent.action != Intent.ACTION_VIEW || deepLinkUrl == null) return
        if (deepLinkUrl.queryParameterNames.contains(RESPONSE_TYPE)) {
            val authorCode = deepLinkUrl.getQueryParameter(RESPONSE_TYPE) ?: return
            getToken(authorCode)
        }
    }

    private fun composeUrl(): Uri =
        Uri.parse(AUTHORIZATION_URL)
            .buildUpon()
            .appendQueryParameter("client_id", CLIENT_ID)
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("response_type", RESPONSE_TYPE)
            .appendQueryParameter("scope", SCOPE)
            .build()

    private fun getToken(authCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getTokenUseCase.execute(TokenBodyDTO(code = authCode))
            }.fold(
                onFailure = {
                    Log.d("GET TOKEN", "FAILURE!!!!!")
                    _isAuthSuccess.send(false)
                },
                onSuccess = {
                    _isAuthSuccess.send(true)
                })
        }
    }

    companion object {
        private const val AUTHORIZATION_URL = "https://unsplash.com/oauth/authorize"
        private const val RESPONSE_TYPE = "code"
        private const val SCOPE = "read_user public write_user read_photos " +
                "write_photos write_likes write_followers" +
                " read_collections write_collections"
        private const val REDIRECT_URI = "com.pets.insplash://auth"
        private const val CLIENT_ID = "1uqN7imwX1vT0e-R2ALszCV2s0GA2gZT5vz2232tRFw"
    }
}