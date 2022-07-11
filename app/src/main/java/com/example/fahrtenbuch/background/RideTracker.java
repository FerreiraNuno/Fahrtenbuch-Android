package com.example.fahrtenbuch.background;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;

import com.example.fahrtenbuch.MainActivity;
import com.example.fahrtenbuch.R;


public class RideTracker extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, "serviceChannel")
                .setContentTitle("Fahrt gestartet!")
                .setContentText("Die Fahrt wird jetzt getrackt, bis die Bluetooth Verbindung getrennt wurde")
                .setSmallIcon(R.drawable.auto)
                .setContentIntent(pendingIntent)
                .setTicker("Hello")
                .build();

        startForeground(1, notification);

        while (true) {
            System.out.println("waiting");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
