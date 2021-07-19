package com.example.fcmdemo.fcm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.Intents.Insert.ACTION
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fcmdemo.R
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.Constants.FCM_WAKE_LOCK
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_broadcast_receiver.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var TAG = "Main Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Get the token
        //Use the token only for received a push notification this device
        var token = FirebaseMessaging.getInstance().token
        Log.d(
            TAG,
            "Token : " + token
        )   //TODO: unable to extract due to some issue token ADD code for the same
        var bodyMessage = intent.getStringExtra("Notification")
        if (bodyMessage != null) {
            body_text_view.text = bodyMessage
        }
//        val myReceiver = MyBroadcastReceiver()
//        val intentFilter = IntentFilter(Constants.FCM_WAKE_LOCK)
//        registerReceiver(myReceiver, intentFilter)

    }
    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(FCM_WAKE_LOCK)
        registerReceiver(myReceiver, intentFilter)
    }

    //to unregsiter receiver
    override fun onStop() {
        super.onStop()
        unregisterReceiver(myReceiver)
    }
    private val myReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "This is notification", Toast.LENGTH_SHORT).show()

        }
    }



}
