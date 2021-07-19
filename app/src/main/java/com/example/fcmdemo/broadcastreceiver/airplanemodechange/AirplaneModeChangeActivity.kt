package com.example.fcmdemo.broadcastreceiver.airplanemodechange

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.fcmdemo.R
import kotlinx.android.synthetic.main.activity_broadcast_receiver.*
import kotlinx.android.synthetic.main.activity_data_broadcast_receiver.*

class AirplaneModeChangeActivity : AppCompatActivity() {
    var TAG = "//"
    lateinit var receiver: AirplaneModeChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_broadcast_receiver)
        receiver = AirplaneModeChangeReceiver()
        register()
    }

    private fun register() {
        // Intent Filter is useful to determine which apps wants to receive
        // which intents,since here we want to respond to change of
        // airplane mode
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
            Log.e(TAG, "this is receiver" + receiver)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

}