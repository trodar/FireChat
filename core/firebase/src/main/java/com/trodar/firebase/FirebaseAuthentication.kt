package com.trodar.firebase

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
import com.trodar.datastore.UserData
import com.trodar.firebase.model.AccountNetwork
import com.trodar.firebase.utils.Result
import com.trodar.firebase.utils.asResult
import com.trodar.firechat.core.datastore.SettingPreferencesDataSource
import com.trodar.utils.extension.getActivity
import com.trodar.utils.extension.getImageExtension
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Clock
import javax.inject.Inject


class FirebaseAuthentication @Inject constructor(
    private val auth: FirebaseAuth,
    private val databaseApi: FirebaseDataSource,
    private val settingsPreferences: SettingPreferencesDataSource,
    private val storageDataSource: StorageDataSource,
    @ApplicationContext private val context: Context,
) : Authentication {

    private val userPhotoPath = "user/%s/image/profile.%s"

    override suspend fun emailAuthentication(
        email: String,
        password: String,
        userName: String?,
        isLogin: Boolean,
        photo: Uri?,
    ): Flow<Boolean> = channelFlow {

        if (isLogin)
            auth.signInWithEmailAndPassword(email, password).await()
        else {
            auth.createUserWithEmailAndPassword(email, password).await()
            val remotePath = String.format(
                userPhotoPath,
                auth.currentUser?.uid,
                photo?.getImageExtension(context = context)
            )
            photo?.let { uri ->
                storageDataSource.upsertPhoto(uri, remotePath)
                    .asResult()
                    .collect {
                        when (it) {
                            is Result.Success -> {
                                val profile = userProfileChangeRequest {
                                    displayName = userName
                                    photoUri = it.data
                                }
                                auth.currentUser?.updateProfile(profile)?.await()
                                addNewAccount()
                            }

                            is Result.Error -> { throw it.exception }

                            is Result.Loading -> {
                            }
                        }
                    }
            }
        }
        updateUserProto()
        this.trySendBlocking(true)
        awaitClose()
    }


    override suspend fun facebookAuthentication(token: String): Flow<Boolean> = channelFlow {

        val credential = FacebookAuthProvider.getCredential(token)

        auth.signInWithCredential(credential).await()
        addNewAccount()
        updateUserProto()
        this.trySendBlocking(true)
        awaitClose()
    }

    override suspend fun googleAuthentication(token: String): Flow<Boolean> = channelFlow {

        val googleCredential = GoogleAuthProvider.getCredential(token, null)

        auth.signInWithCredential(googleCredential).await()
        addNewAccount()
        updateUserProto()
        this.trySendBlocking(true)
        awaitClose()
    }

    override suspend fun twitterAuthentication(context: Context): Flow<Boolean> = channelFlow {

        val pendingResultTask = auth.pendingAuthResult

        pendingResultTask?.also {

            pendingResultTask.await()
            if (pendingResultTask.isSuccessful) {
                this.trySendBlocking(true)
            } else {
                throw Exception(pendingResultTask.exception)
            }
        } ?: kotlin.run {
            val provider = OAuthProvider.newBuilder("twitter.com")
            auth.startActivityForSignInWithProvider(context.getActivity(), provider.build()).await()
            if (auth.currentUser != null) {
                addNewAccount()
                updateUserProto()
                this.trySendBlocking(true)
            } else {
                throw Exception(auth.pendingAuthResult?.exception)
            }
        }
    }

    private suspend fun addNewAccount() {
        if (auth.currentUser == null) return

        val fireUser = auth.currentUser!!

        databaseApi.upsertAccount(
            AccountNetwork(
                accountId = fireUser.uid,
                name = fireUser.displayName ?: "User",
                logo = fireUser.photoUrl?.toString() ?: "",
                lastOnline = Clock.System.now().toEpochMilliseconds(),
            )
        )
    }

    private suspend fun updateUserProto(){
        val fireUser = auth.currentUser!!

        settingsPreferences.setUserData(
            UserData.newBuilder()
                .setId(fireUser.uid)
                .setLogo(fireUser.photoUrl?.toString() ?: "")
                .setName(fireUser.displayName ?: "User")
                .setEmail(fireUser.email ?: "")
                .setStatus("")
                .setPhone(fireUser.phoneNumber ?: "")
                .build()
        )
    }
}

