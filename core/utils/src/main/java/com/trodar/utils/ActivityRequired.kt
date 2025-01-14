package com.trodar.utils

import android.app.Activity

interface ActivityRequired {
    fun onCreated(activity: Activity)

    fun onStarted()

    fun onStopped()

    fun onDestroyed()

}