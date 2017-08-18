package com.taiyeoloriade.measureme.ui.activity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.taiyeoloriade.measureme.AlarmService;
import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.utility.NotificationID;

/**
 * Created by OLORIAKE KEHINDE on 4/9/2017.
 */


public class AlarmBroadcastService extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent i = new Intent("com.taiyeoloriade.measureme.AlarmService");
//        i.setClass(context, AlarmService.class);
        Intent i = new Intent(context, AlarmService.class);
        context.startService(i);
    }

}





