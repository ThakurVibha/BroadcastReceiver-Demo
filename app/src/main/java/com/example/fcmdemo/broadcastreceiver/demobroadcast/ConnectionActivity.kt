package com.example.fcmdemo.broadcastreceiver.demobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

class ConnectionActivity : BroadcastReceiver() {
    var TAG = "//"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {

        Toast.makeText(context, intent!!.action, Toast.LENGTH_LONG).show();
        if (intent!!.getAction().equals("SOME_ACTION")) {

            var connectivityManager: ConnectivityManager =
                context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var network: Network? = connectivityManager.activeNetwork

            var isConnected: Boolean = network != null

            if (isConnected) {
                Toast.makeText(context, "Network is connected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Network is changed or reconnected", Toast.LENGTH_LONG)
                    .show();
            }
        }
    }
}