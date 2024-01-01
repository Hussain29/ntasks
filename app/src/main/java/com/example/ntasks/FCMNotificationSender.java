package com.example.ntasks;

import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FCMNotificationSender {

    private String serverKey;

    private static final String TAG = "FCM_DEBUG";
    private String deviceToken;
    private String title;
    private String message;

    public FCMNotificationSender(String serverKey, String deviceToken, String title, String message) {
        this.serverKey = serverKey;
        this.deviceToken = deviceToken;
        this.title = title;
        this.message = message;
    }

    public void sendNotification() {
        try {
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + serverKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            Log.d(TAG, "Server Key: " + serverKey);
            Log.d(TAG, "Device Token: " + deviceToken);
            Log.d(TAG, "Title: " + title);
            Log.d(TAG, "Body: " + message);

            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", message);

            JSONObject data = new JSONObject();
            data.put("title", title);
            data.put("body", message);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("to", deviceToken);
            jsonBody.put("notification", notification);
            jsonBody.put("data", data);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jsonBody.toString().getBytes());

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Notification sent successfully
            } else {
                // Error handling
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
