package com.example.fcmdemo.fcm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fcmdemo.R
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_broadcast.*
import kotlinx.android.synthetic.main.activity_broadcast_receiver.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var receiver: MyMessagingActivity? = null
    private var TAG = "Main Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        receiver = MyMessagingActivity()
        //Get the token
        //Use the token only for received a push notification this device
        var token = FirebaseMessaging.getInstance().token
        Log.d(
            TAG,
            "Token : " + token
        )   //TODO: unable to extract token due to some issue token ADD code for the same
        var bodyMessage = intent.getStringExtra("Notification")
        if (bodyMessage != null) {
            body_text_view.text = bodyMessage
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver);
    }

    override fun onResume() {
        super.onResume()
        configureReceiver()
        val intent = Intent()
        intent.action = "ACTION_INTENT"
        sendBroadcast(intent)

//                val value2 = intent.getStringExtra("key")
//                Toast.makeText(context, value1, Toast.LENGTH_SHORT).show()
    }

    private fun configureReceiver() {
        val filter = IntentFilter()
        filter.addAction("ACTION_INTENT")
        registerReceiver(receiver, filter)
    }

//    private val newReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            Toast.makeText(context, "HIII", Toast.LENGTH_SHORT).show()
//            val myNewActivity = Intent(context, DashboardActivity::class.java)
//            startActivity(myNewActivity)
//        }
//
//    }

}

