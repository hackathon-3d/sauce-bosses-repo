package com.r0adkll.sparc.pillalarm.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.r0adkll.sparc.pillalarm.R;

/**
 * Created by r0adkll on 8/24/13.
 */
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equalsIgnoreCase("com.r0adkll.sparc.pillalarm.POST_NOTIFICATION")){

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            // Get extras
            Bundle xtras = intent.getExtras();
            String title = xtras.getString("title");
            String message = xtras.getString("message");
            long endTime = xtras.getLong("end_time", -1L);

            NotificationManager nman = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder mBuilder = new Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_launcher)
                    ;

            nman.notify(0, mBuilder.build());


            if(endTime != -1){

                if(System.currentTimeMillis() > endTime){
                    // Kill the broadcast
                    Intent deathIntent = new Intent("com.r0adkll.sparc.pillalarm.POST_NOTIFICATION");
                    PendingIntent pi = PendingIntent.getBroadcast(context, 1000, deathIntent, 0);
                    am.cancel(pi);
                }
            }
        }
    }
}
