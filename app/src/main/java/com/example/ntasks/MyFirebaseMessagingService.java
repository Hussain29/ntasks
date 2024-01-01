package com.example.ntasks;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String token) {
        // If you need to handle the token refresh, you can do it here
        Log.d(TAG, "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if the message contains data payload
        if (remoteMessage.getData().size() > 0) {
            handleDataPayload(remoteMessage.getData());
        }

        // Check if the message contains a notification payload
        if (remoteMessage.getNotification() != null) {
            handleNotificationPayload(remoteMessage.getNotification().getBody());
        }
    }

    private void handleDataPayload(Map<String, String> data) {
        // Example logic for handling data payload
        String taskName = data.get("taskName");
        String assignedUser = data.get("assignedUser");

        // You can use the retrieved data to perform actions or update UI
        Log.d(TAG, "Handling Data Payload - Task Name: " + taskName + ", Assigned User: " + assignedUser);

        // If needed, you can also send a local broadcast or update UI components
        // to reflect the received data.
    }

    private void handleNotificationPayload(String notificationBody) {
        // Example logic for handling notification payload
        // This can include displaying a notification, updating UI, etc.
        Log.d(TAG, "Handling Notification Payload: " + notificationBody);

        // If you want to display a notification, you can use the NotificationManager
        // or any other method suitable for your app's notification implementation.
        // For simplicity, let's assume you have a method showNotification:
        showNotification(notificationBody);
    }

    private void showNotification(String message) {
        // Implement your notification logic here
        // You can use NotificationCompat.Builder to create and display a notification
        // For simplicity, I'll print the message to log
        Log.d(TAG, "Showing Notification: " + message);
        // You should implement the actual notification creation and display here
        // For example, using NotificationCompat.Builder and NotificationManager
    }
}
