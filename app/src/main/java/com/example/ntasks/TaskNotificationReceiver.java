/*
package com.example.ntasks;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class TaskNotificationReceiver extends BroadcastReceiver {

    private static final String TAG = "TaskNotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Notification received");

        String taskId = intent.getStringExtra("taskId");
        String taskName = intent.getStringExtra("taskName");
        String assignedUser = intent.getStringExtra("assignedUser");

        showNotification(context, taskId, taskName, assignedUser);
    }

    private void showNotification(Context context, String taskId, String taskName, String assignedUser) {
        String channelId = "channel_id"; // Replace with your actual channel ID
        String channelName = "Task Notifications"; // Replace with your actual channel name

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Set the notification time to 10 AM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Check if the notification time is in the past, if so, set it for the next day
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        Log.d(TAG, "Notification scheduled for: " + calendar.getTime());

        // Create a PendingIntent for the notification
        Intent notificationIntent = new Intent(context, TaskNotificationReceiver.class);
        notificationIntent.putExtra("taskId", taskId);
        notificationIntent.putExtra("taskName", taskName);
        notificationIntent.putExtra("assignedUser", assignedUser);

        int uniqueRequestCode = taskId.hashCode(); // Use a unique request code for each notification
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, uniqueRequestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.osd)
                .setContentTitle("Task Reminder")
                .setContentText("Task: " + taskName + "\nAssigned to: " + assignedUser)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Schedule the notification at the specified time
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Log.d(TAG, "Notification scheduled successfully");

        if (notificationManager != null) {
            notificationManager.notify(uniqueRequestCode, builder.build());
        } else {
            Log.e(TAG, "NotificationManager is null");
        }
    }
}
*/
package com.example.ntasks;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class TaskNotificationReceiver extends BroadcastReceiver {

    private static final String TAG = "TaskNotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Notification received");

        // Extract data from the intent
        String taskId = intent.getStringExtra("taskId");
        String taskName = intent.getStringExtra("taskName");
        String assignedUser = intent.getStringExtra("assignedUser");

        // Show the notification
        showNotification(context, taskId, taskName, assignedUser);
    }

    private void showNotification(Context context, String taskId, String taskName, String assignedUser) {
        // Replace with your actual channel ID and channel name
        String channelId = "1234567";
        String channelName = "Task Notifications";

        // Get the NotificationManager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Set the notification time to 10 AM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);

        // Check if the notification time is in the past, if so, set it for the next day
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        Log.d(TAG, "Notification scheduled for: " + calendar.getTime());

        // Create a PendingIntent for the notification
        Intent notificationIntent = new Intent(context, TaskNotificationReceiver.class);
        notificationIntent.putExtra("taskId", taskId);
        notificationIntent.putExtra("taskName", taskName);
        notificationIntent.putExtra("assignedUser", assignedUser);

        int uniqueRequestCode = taskId.hashCode(); // Use a unique request code for each notification
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, uniqueRequestCode, notificationIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.osd)
                .setContentTitle("Task Reminder")
                .setContentText("Task: " + taskName + "\nAssigned to: " + assignedUser)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Schedule the notification at the specified time
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Log.d(TAG, "Notification scheduled successfully");

        // Notify the NotificationManager
        if (notificationManager != null) {
            notificationManager.notify(uniqueRequestCode, builder.build());
        } else {
            Log.e(TAG, "NotificationManager is null");
        }
    }
}

