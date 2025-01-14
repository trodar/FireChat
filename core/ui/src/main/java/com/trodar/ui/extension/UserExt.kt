package com.trodar.ui.extension

import com.trodar.datastore.UserData
import com.trodar.model.User

fun User.toStoreUser(newLogo: String): UserData = UserData.newBuilder()
    .setId(id)
    .setLogo(newLogo)
    .setName(name)
    .setEmail(email)
    .setStatus(status)
    .setPhone(phone)
    .build()