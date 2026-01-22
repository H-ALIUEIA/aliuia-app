package io.github.h_aliueia;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import io.github.h_aliueia.services.notificationreceiver;

public class notificationhelper
{
    public static final String SPORTS_CHANNEL_ID = "99";

    public static void createChannel(Context context)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager != null && notificationManager.getNotificationChannel(SPORTS_CHANNEL_ID) == null)
            {
                NotificationChannel channel = new NotificationChannel(SPORTS_CHANNEL_ID,"sports", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("something des");
                channel.enableLights(false);
                channel.setLightColor(Color.RED);
                channel.enableVibration(false);
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public static void sendNotification(Context context,String title,String message, String source, int fragtoload)
    {
        Intent in = new Intent(context, MainActivity.class);
        in.putExtra("frgtoload", fragtoload);
        in.putExtra("quoteid", source);
        Intent in2 = new Intent(context, notificationreceiver.class);
        in2.putExtra("texttocopy", source);
        int pendingnum = 0;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            pendingnum = PendingIntent.FLAG_IMMUTABLE;
        }
        else
        {
            pendingnum = 0;
        }

        PendingIntent clickIntent = PendingIntent.getActivity(context, 5, in, pendingnum | PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent actionIntent = PendingIntent.getBroadcast(context, 4, in2, pendingnum | PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = null;
        if(fragtoload == 2)
        {
            builder = new NotificationCompat.Builder(context, SPORTS_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSmallIcon(R.drawable.prophecyicon4)
                .setAutoCancel(true)
                .addAction(R.drawable.prophecyicon4, "Αντίγραψε", actionIntent)
                .setContentIntent(clickIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        else if (fragtoload == 4)
        {
            builder = new NotificationCompat.Builder(context, SPORTS_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSmallIcon(R.drawable.prophecyicon4)
                .setAutoCancel(true)
                .setContentIntent(clickIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        notificationManagerCompat.notify(5654,builder.build());
    }
}
