package com.example.fcmdemo.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseInstanceIDService : FirebaseMessagingService() {
    private var TAG = "MyFirebaseInstanceIDService"

    fun onTokenRefresh() {
        //Get updated token
        var refreshedToken = FirebaseMessaging.getInstance().token
        Log.d(TAG, "New Token : " + refreshedToken)

    }
}