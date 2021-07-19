package com.example.fcmdemo.broadcastreceiver.wifichange

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import com.example.fcmdemo.R
import kotlinx.android.synthetic.main.activity_broadcast_receiver.*

class WifiReceiverActivity : AppCompatActivity() {
    lateinit var switch: Switch
    var TAG="//"
    lateinit var wifiManager: WifiManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_receiver)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        onSwitch()
    }

    private fun onSwitch() {
        wifiSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                wifiManager.isWifiEnabled = true
                wifiSwitch.text = "WiFi is ON"
            } else {
                wifiManager.isWifiEnabled = false
                wifiSwitch.text = "WiFi is OFF"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentFilter)
    }

    //to unregsiter receiver
    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiStateReceiver)
    }

    private val wifiStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent!!.getIntExtra(
                WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN
            )) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    wifiSwitch.isChecked = true
                    textView.text = "wifi is on"
                    Toast.makeText(this@WifiReceiverActivity, "Wifi is on", Toast.LENGTH_SHORT)
                        .show()
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    wifiSwitch.isChecked = false
                    textView.text = "wifi is off"
                    Toast.makeText(
                        this@WifiReceiverActivity,
                        "Wifi is off",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }


}
