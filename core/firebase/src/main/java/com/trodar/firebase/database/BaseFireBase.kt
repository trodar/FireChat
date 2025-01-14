package com.trodar.firebase.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

open class BaseFireBase @Inject constructor() {

    @Inject lateinit var auth: FirebaseAuth
    @Inject lateinit var database: DatabaseReference

}