package com.example.telecallerapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class FollowUpNotificationHelper {

    public static void scheduleReminder(Context context, long time, String leadName, String leadPhone) {

        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("leadName", leadName);
        intent.putExtra("leadPhone", leadPhone);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int) System.currentTimeMillis(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
        );
    }
}