package com.pets.insplash.presentation.authorization.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pets.insplash.domain.GetTokenUseCase
import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.TokenBodyDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(private val getTokenUseCase: GetTokenUseCase): ViewModel() {

    private val _isAuthSuccess = Channel<Boolean>()
    val isAuthSuccess = _isAuthSuccess.receiveAsFlow()

    fun openBrowser(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, composeUrl())
        context.startActivity(intent)
    }

    fun handleDeepLink(intent: Intent) {
        val deepLinkUrl = intent.data
        if (intent.action != Intent.ACTION_VIEW || deepLinkUrl == null) return
        if (deepLinkUrl.queryParameterNames.contains(Constants.RESPONSE_TYPE)) {
            val authorCode = deepLinkUrl.getQueryParameter(Constants.RESPONSE_TYPE) ?: return
            getToken(authorCode)
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

    private fun getToken(authCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getTokenUseCase.execute(TokenBodyDTO(code = authCode))
            }.fold(
                onFailure = {
                    _isAuthSuccess.send(false)
                },
                onSuccess = {
                    _isAuthSuccess.send(true)
                })
        }
    }
}