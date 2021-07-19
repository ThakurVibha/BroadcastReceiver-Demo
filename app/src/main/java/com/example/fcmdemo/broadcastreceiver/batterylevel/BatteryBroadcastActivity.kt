package com.example.fcmdemo.broadcastreceiver.batterylevel


import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.Intent.ACTION_BATTERY_CHANGED
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.fcmdemo.R
import kotlinx.android.synthetic.main.activity_battery_broadcast.*

class BatteryBroadcastActivity : AppCompatActivity() {
    //    var currentBatteryStatus = "Battery Info is :"
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery_broadcast)
        onclick()
        batterylevel()
        batterystatus()
        fastChargingIndicator()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun fastChargingIndicator() {
//        val fc = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager
//        val chargingIndicator:Int=fc.getIntProperty(BatteryManager.BATTERY_STATUS_FULL)
//        Toast.makeText(applicationContext, "Battery percentage is: $chargingIndicator", Toast.LENGTH_LONG).show()



    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onclick() {
        btngetInfo.setOnClickListener {
            batterylevel()
            batterystatus()
            fastChargingIndicator()
//            progressBar.visibility=View.GONE

        }
    }

    //to get the battery percentage
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun batterylevel() {
//        progressBar.visibility= View.VISIBLE
//        / Call battery manager service
        val bm = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager
        // Get the battery percentage and store it in a INT variable
        val batLevel: Int = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        // Display the variable using a Toast
//        progressBar.visibility= View.GONE
        Toast.makeText(applicationContext, "Battery percentage is: $batLevel", Toast.LENGTH_LONG).show()
        tvpercentage.text = "$batLevel%"
    }

    // to get battery status
    private fun batterystatus() {
//        progressBar.visibility= View.VISIBLE
        val batteryStatus: Intent? =
            IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
                applicationContext.registerReceiver(null, ifilter)
            }

        // isCharging if true indicates charging is ongoing and vice-versa
        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL

        // Display whatever the state in the form of a Toast
        if (isCharging) {
//            progressBar.visibility= View.GONE
            Toast.makeText(applicationContext, "Charging", Toast.LENGTH_LONG).show()
            tvstatus.text = "Charging"
        } else {
            Toast.makeText(applicationContext, "Not Charging", Toast.LENGTH_LONG).show()
            tvstatus.text = "Not Charging"
        }

    }


}





