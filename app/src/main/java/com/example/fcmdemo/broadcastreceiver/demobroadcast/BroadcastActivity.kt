package com.example.fcmdemo.broadcastreceiver.demobroadcast

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fcmdemo.R
import kotlinx.android.synthetic.main.activity_broadcast.*


class BroadcastActivity : AppCompatActivity() {
    var receiver: ConnectionActivity? = null
    var intentFilter: IntentFilter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast)
        receiver = ConnectionActivity()
        intentFilter = IntentFilter("SOME_ACTION")
        onClick()

    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver);
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, intentFilter);

    }
    private fun onClick() {
        btnBroadcast.setOnClickListener {
            sendBroadcast(intent)
        }
    }

}