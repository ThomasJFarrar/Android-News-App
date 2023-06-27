package com.example.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Handles sending out notifications.
 */
public class ReminderBroadcast extends BroadcastReceiver {
    /**
     * Sends out a notification.
     * @param context The application environment.
     * @param intent Action request.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "notify")
                .setSmallIcon(R.drawable.ic_news)
                .setContentTitle("See today's top stories")
                .setContentText("Tap to read more about the latest news")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);


        if (ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(200, builder.build());
    }
}
