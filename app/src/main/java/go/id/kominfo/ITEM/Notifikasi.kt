package go.id.kominfo.ITEM

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import go.id.kominfo.ACTIVITY.PenjualanActivity
import android.media.RingtoneManager
import go.id.kominfo.R.*


class  Notifikasi {
    lateinit var mBulder: NotificationCompat.Builder
    lateinit var mManager: NotificationManager
    lateinit var mIntent : Intent
    lateinit var mPendingIntent: PendingIntent
    lateinit var notif : Notification

    fun notif(ctx: Context, title: String,pesan: String,sub: String,id:Int) {
        print("notif dikirim \n")
        mIntent = Intent(ctx,PenjualanActivity::class.java)
        mPendingIntent = PendingIntent.getActivity(ctx,0,mIntent,0)
        mManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        mBulder = NotificationCompat.Builder(ctx,"ch1")
                .setContentIntent(mPendingIntent)
                .setSmallIcon(drawable.ic_notifications)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.resources,drawable.ic_notifications))
                .setContentTitle(title)
                .setContentText(pesan)
                .setSubText(sub)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
                .setPriority(2)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("ch1","oreo",NotificationManager.IMPORTANCE_DEFAULT)
            mBulder.setChannelId("ch1")
            if (mManager != null){
                mManager.createNotificationChannel(channel)
            }
        }

        notif = mBulder.build()

        mManager.notify(id,notif)
        print("notif \n")
    }
}