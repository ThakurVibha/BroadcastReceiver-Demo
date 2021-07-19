package com.example.fcmdemo.broadcastreceiver.airplanemodechange

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneModeChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // intent contains the information about the broadcast
        // in our case broadcast is change of airplane mode
        // if getBooleanExtra contains null value,it will directly return back

        val isAirplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return
        if (isAirplaneModeEnabled) {
            Toast.makeText(context, "Airplane Mode Enabled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Airplane Mode is disabled", Toast.LENGTH_LONG).show()

        }
    }
}