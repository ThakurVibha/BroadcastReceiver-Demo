package com.example.fcmdemo.fcm

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.fcmdemo.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var mChannel: NotificationChannel? = null
    private var notifManager: NotificationManager? = null
    var TAG = "//"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e(TAG, remoteMessage.data.toString())
        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            displayCustomNotificationForOrders(title!!, body!!)
        }

        val intent1 = Intent("ACTION_INTENT")
        sendBroadcast(intent1)
//        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification()!!.getBody());
//
//
//        if ((title!= null) &&(body!=null)){
//            sendBroadcast(title, body)
//
//        };
// Check if message contains a data payload.
        if (remoteMessage.getData().size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification()!!.getBody());
            sendNotification(remoteMessage)
            val intent = Intent()
            intent.action = "ACTION_INTENT"
//            intent.putExtra("title", title)
//            intent.putExtra("body", body)
            sendBroadcast(intent)
        }

//        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra("SOME_ACTION", SOME_ACTION);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//      var  broadcaster = LocalBroadcastManager.getInstance(baseContext)
//
//        val intent = Intent()
//        intent.putExtra("Key", title)
//        intent.putExtra("key", body)
//        broadcaster.sendBroadcast(intent)
//        //Verify if the message contains data
//        if (remoteMessage.data.isNotEmpty()) {
//
//        }
//        //Verify if the message contains notification
//        if (remoteMessage.notification != null) {
//            Log.d(TAG, "Message body : " + remoteMessage.notification!!.body)
////            Toast.makeText(this, "HIIIIII" + remoteMessage, Toast.LENGTH_SHORT).show()
//            sendNotification(remoteMessage.notification!!.body, remoteMessage)
////            val intent = Intent(this, MyBroadcastReceiver::class.java)
////            intent.putExtra("value1", value1);
////            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//
//        }


//        val itent = Intent(this, MyBroadcastReceiver::class.java)
//        val pendingIntent =
//            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

//    private fun sendBroadcast(title: String, body: String?) {
//        val intent = Intent()
//        intent.action = "ACTION_INTENT"
//        intent.putExtra("title", title)
//        intent.putExtra("body", body)
//        intent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
//        sendBroadcast(intent)
//    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun displayCustomNotificationForOrders(title: String, description: String) {
        if (notifManager == null) {
            notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val importance = NotificationManager.IMPORTANCE_HIGH
            if (mChannel == null) {
                mChannel = NotificationChannel("0", title, importance)
                mChannel!!.setDescription(description)
                mChannel!!.enableVibration(true)
                notifManager!!.createNotificationChannel(mChannel!!)
            }
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "0")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(this, 1251, intent, PendingIntent.FLAG_ONE_SHOT)
            builder.setContentTitle(title)
                .setSmallIcon(getNotificationIcon()) // required
                .setContentText(description) // required
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
//                .setBadgeIconType(R.mipmap.sym_def_app_icon)
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            val notification: Notification = builder.build()
            notifManager!!.notify(0, notification)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            var pendingIntent: PendingIntent? = null
            pendingIntent =
                PendingIntent.getActivity(this, 1251, intent, PendingIntent.FLAG_ONE_SHOT)
            val defaultSoundUri: Uri =
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(baseContext, R.color.black))
                .setSound(defaultSoundUri)
                .setSmallIcon(getNotificationIcon())
                .setContentIntent(pendingIntent)
                .setStyle(
                    NotificationCompat.BigTextStyle().setBigContentTitle(title).bigText(
                        description
                    )
                )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1251, notificationBuilder.build())
        }
    }

    private fun getNotificationIcon(): Int {
        val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        return if (useWhiteIcon) R.mipmap.ic_launcher else R.mipmap.ic_launcher
    }


    private fun sendNotification(body: RemoteMessage) {

        var intent = Intent(this, MainActivity::class.java)
        //If set, and the activity being launched is already running in the current task,
        //then instead of launching a new instance of that activity, all of the other activities
        // on top of it will be closed and this Intent will be delivered to the (now on top)
        // old activity as a new Intent.
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("Notification", body)

        var pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT/*Flag indicating that this PendingIntent can be used only once.*/
        )
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var notificationBuilder = NotificationCompat.Builder(this, "Notification")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Push Notification FCM")
            .setContentText(body.toString())
            .setAutoCancel(true)
            .setSound(notificationSound)
            .setContentIntent(pendingIntent)

        var notificationManager: NotificationManager =
            this.getSystemService(String()) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())


    }

}