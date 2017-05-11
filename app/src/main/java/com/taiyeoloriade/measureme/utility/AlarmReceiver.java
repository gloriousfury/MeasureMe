package com.taiyeoloriade.measureme.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.taiyeoloriade.measureme.R;
import com.taiyeoloriade.measureme.ui.activity.MainActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by OLORIAKE KEHINDE on 2/9/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

            Notification.Builder mBuilder =
                    new Notification.Builder(context)
//                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("Here is my Title")
                            .setContentText("Here is my text");
            Intent resultIntent = new Intent(context, MainActivity.class);
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//            stackBuilder.addParentStack(HomeActivity.class);
//            stackBuilder.addNextIntent(resultIntent);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);
//            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            mNotificationManager.notify(1, mBuilder.build());
        }

}