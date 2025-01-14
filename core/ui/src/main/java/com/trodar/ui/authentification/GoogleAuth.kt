package com.trodar.ui.authentification

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.trodar.ui.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun googleResult(
    context: Context,
    scope: CoroutineScope,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit,
) {
    val credentialManager = CredentialManager.create(context)

    val googleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setAutoSelectEnabled(true)
        .setServerClientId( context.getString(R.string.core_ui_default_web_client_id))
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    scope.launch {
        try {
            val result = credentialManager.getCredential(
                context = context,
                request = request,
            )
            val credential = result.credential
            val googleTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val googleToken = googleTokenCredential.idToken
            onSuccess(googleToken)
        } catch (e: NoCredentialException) {
            onError(e.toString())
        }
    }
}







































