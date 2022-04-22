package com.bagus.pemantauanbencana.ui.pushnotification

import android.app.*
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.ui.disasterdetail.DisasterDetailActivity
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
                dataPayload["id"].toString()
            )
        }
    }

    private fun generateNotification(title: String, message: String, id_logs: String) {
        val intent = Intent(this, DisasterDetailActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        intent.putExtra(DisasterDetailActivity.EXTRA_DISASTER, id_logs)
        Log.e("FCM SERVICE", id_logs)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, id_logs.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)


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

        val mNotification: Notification = builder.build()
        mNotification.flags = mNotification.flags or Notification.FLAG_AUTO_CANCEL

        mNotification.defaults = mNotification.defaults or Notification.DEFAULT_SOUND
        mNotification.defaults = mNotification.defaults or Notification.DEFAULT_VIBRATE

//        notificationManager.notify(0, mNotification)
        notificationManager.notify(CounterNotif.getNotificationID(baseContext), mNotification)

    }
}