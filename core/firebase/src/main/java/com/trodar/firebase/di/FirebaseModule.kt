package com.trodar.firebase.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.trodar.firebase.Authentication
import com.trodar.firebase.FirebaseAuthentication
import com.trodar.firebase.FirebaseDataSource
import com.trodar.firebase.StorageDataSource
import com.trodar.firebase.database.FirebaseFetchData
import com.trodar.firebase.storage.FireStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {


    @Binds
    abstract fun bindAuthentication(
        firebaseAuthentication: FirebaseAuthentication
    ): Authentication

    @Binds
    abstract fun bindFirebaseApi(
        firebaseApi: FirebaseFetchData
    ): FirebaseDataSource

    @Binds
    abstract fun bindStorage(
        firebaseStorage: FireStorage
    ): StorageDataSource

}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseAuthModule{

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseDataBase(): DatabaseReference = Firebase.database.reference


    @Provides
    @Singleton
    fun provideFirebaseStorage(): StorageReference = Firebase.storage.reference
}


