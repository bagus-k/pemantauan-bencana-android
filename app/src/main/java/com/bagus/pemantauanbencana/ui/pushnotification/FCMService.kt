package com.bagus.pemantauanbencana.ui.pushnotification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.ui.main.MainActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging

class FCMService :  FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "notification_channel"
        const val CHANNEL_NAME = "com.bagus.pemantauanbencana"
    }

    override fun onMessageReceived(message: RemoteMessage) {
//        if (message.notification != null) {
//            if (message.data.isNotEmpty()) {
//                generateNotification(message.notification!!.title!!, message.notification!!.body!!, message.notification!!.tag!!.toString())
//                Firebase.messaging.subscribeToTopic("disaster")
//            }
//        }
        if (message.getData().size > 0) {
            Log.d("FCM SERVICE", "Message data payload: " + message.getData())
            val dataPayload: Map<String, String> = message.getData()
            generateNotification(
                dataPayload["title"].toString(),
                dataPayload["body"].toString(),
                dataPayload["tag"].toString()
            )
        }
    }

    private fun generateNotification(title: String, message: String, tag: String) {

//        val intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_stat_onesignal_default)
//            .setContentTitle(result.payload.title)
//            .setContentText(result.payload.body)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            // Set the intent that will fire when the user taps the notification
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(this)) {
//            // notificationId is a unique int for each notification that you must define
//            notify(22, builder.build())
//        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
//        intent.putExtra(DisasterDetailActivity.EXTRA_DISASTER, tag)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

//        val pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.bpbd_logo)
            .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.bpbd_logo))
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

//            builder = builder.setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.drawable.bpbd_logo)

//        notificationManager.notify(0,builder.build())

        val mNotification: Notification = builder.build()
        mNotification.flags = mNotification.flags or Notification.FLAG_AUTO_CANCEL

        mNotification.defaults = mNotification.defaults or Notification.DEFAULT_SOUND
        mNotification.defaults = mNotification.defaults or Notification.DEFAULT_VIBRATE

        notificationManager.notify(0, mNotification)
    }
}