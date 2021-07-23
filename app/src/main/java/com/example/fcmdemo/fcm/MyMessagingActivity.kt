package com.example.fcmdemo.fcm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.fcmdemo.broadcastreceiver.demobroadcast.BroadcastActivity


class MyMessagingActivity : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "HIII", Toast.LENGTH_SHORT).show()
//        val myNewActivity = Intent(context, DashboardActivity::class.java)
//        startActivity(myNewActivity)
//            var notificationManager: NotificationManager =
//                context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//            var bodyMessage = intent.getStringExtra("SOME_ACTION")

//        try {
//            val value1 = intent!!.getStringExtra("title")
//            val value2 = intent!!.getStringExtra("body")
//            Toast.makeText(context, "Title is " + value1, Toast.LENGTH_SHORT).show()
//            Toast.makeText(context, "Body is " + value2, Toast.LENGTH_SHORT).show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

//            var showToast: Boolean = notificationManager != null
//
//            if (showToast) {
//                Toast.makeText(context, "Hey hiiii", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(context, "HEY HIII", Toast.LENGTH_LONG)
//                    .show();
//            }
//        }
//            try {
//                val value1 = intent!!.getStringExtra("key")
//                val value2 = intent.getStringExtra("key")
//                Toast.makeText(context, value1, Toast.LENGTH_SHORT).show()
//                Toast.makeText(context, value2, Toast.LENGTH_SHORT).show()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }


    }

    private fun startActivity(myNewActivity: Intent) {
        startActivity(myNewActivity)

    }


}