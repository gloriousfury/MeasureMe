package com.taiyeoloriade.measureme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.taiyeoloriade.measureme.ui.activity.AlarmReciever1;
import com.taiyeoloriade.measureme.ui.activity.AlarmReciever2;
import com.taiyeoloriade.measureme.ui.activity.MainActivity;

import java.util.Calendar;

/**
 * Created by OLORIAKE KEHINDE on 8/18/2017.
 */

public class AlarmService extends Service {
    String NOTIFICATION_ID = "notification_id";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 17);
        calendar.set(Calendar.SECOND, 0);


        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 10);
        calendar2.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            int day = 86400000;
            int notificationId = 1;
            Intent intent1 = new Intent(this, AlarmReciever1.class);
            intent1.putExtra(NOTIFICATION_ID,notificationId);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + day, AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            int notificationId = 1;
            Intent intent1 = new Intent(this, AlarmReciever1.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }


//        if (calendar2.getTimeInMillis() <= System.currentTimeMillis()) {
//            int day = 86400000;
//            int notificationId = 2;
//            Intent intent1 = new Intent(this, AlarmReciever2.class);
//            intent1.putExtra(NOTIFICATION_ID,notificationId);
//
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//            AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
//            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis() + day, AlarmManager.INTERVAL_DAY, pendingIntent);
//            Toast.makeText(this,"came here",Toast.LENGTH_LONG).show();
//        } else {
//            int notificationId = 2;
//            Intent intent1 = new Intent(this, AlarmReciever2.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//            AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
//            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//            Toast.makeText(this,"came here",Toast.LENGTH_LONG).show();
//        }


        return super.onStartCommand(intent, flags, startId);
    }
}
