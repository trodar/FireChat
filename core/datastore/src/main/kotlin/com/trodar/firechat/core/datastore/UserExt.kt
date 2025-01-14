package com.trodar.firechat.core.datastore

import com.trodar.datastore.UserData
import com.trodar.model.User

fun UserData.toUser() = User(
    id = id,
    name = name,
    email = email,
    phone = phone,
    logo = logo,
    status = status,
)