package com.taiyeoloriade.measureme.ui.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;


import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.utility.NotificationID;

import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by OLORIAKE KEHINDE on 4/9/2017.
 */


public class AlarmReciever1 extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        int notifyInt = NotificationID.getID();
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Measure Me")
                .setContentText("How did yor day go? track it").setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        notificationManager.notify(notifyInt, mNotifyBuilder.build());
        notifyInt++;
    }

}





