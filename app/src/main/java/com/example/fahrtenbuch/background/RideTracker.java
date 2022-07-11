package com.example.fahrtenbuch.background;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.fahrtenbuch.MainActivity;
import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.db.Database;
import com.example.fahrtenbuch.ui.settings.PushNotificationHandler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;


public class RideTracker extends Service {
    private static final String CHANNEL_ID = "serviceChannel";

    ArrayList<Location> locationArrayList;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    GeofencingClient geofencingClient;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationArrayList = new ArrayList<>();

        Notification notification = createNotificationChannel();
        startForeground(1, notification);

        startLocationUpdates();

        return START_STICKY;
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        geofencingClient = LocationServices.getGeofencingClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                System.out.println("\nLocation changed!");
                System.out.println("lat: " + locationResult.getLastLocation().getLatitude());
                System.out.println("long: " + locationResult.getLastLocation().getLongitude());
                locationArrayList.add(locationResult.getLastLocation());
                super.onLocationResult(locationResult);
            }
        };
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onDestroy() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        int distance = calculateDistance(locationArrayList);
        Date date = new Date();

        Database db = new Database(getApplicationContext());
        db.insertRide(date.getTime(), distance, 5);

        //Push Notifications
        Bundle bundle = new Bundle();
        Cursor c = db.getNewestRide();
        c.moveToFirst();
        bundle.putInt("Strecke", c.getInt(3));
        PushNotificationHandler pushNotifier = new PushNotificationHandler(this);
        pushNotifier.pushNotifcation(bundle);

        super.onDestroy();
    }


    private int calculateDistance(ArrayList<Location> locations) {
        if (locations.size() < 2) {
            return 0;
        }
        double sum = 0;
        for (int i = 1; i < locations.size(); i++) {
            double lat1 = locations.get(i - 1).getLatitude();
            double lon1 = locations.get(i - 1).getLongitude();
            double lat2 = locations.get(i).getLatitude();
            double lon2 = locations.get(i).getLongitude();
            sum += getDistanceFromLatLonInKm(lat1, lon2, lat2, lon2);
        }
        System.out.println("final distance: " + sum);
        return (int) (sum);
    }

    private double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        System.out.println(d);
        return d;
    }

    double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    private Notification createNotificationChannel() {
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID,
                "Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChannel);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Fahrt gestartet!")
                .setContentText("Die Fahrt wird jetzt getrackt, bis die Bluetooth Verbindung getrennt wurde")
                .setSmallIcon(R.drawable.auto)
                .setContentIntent(pendingIntent)
                .setTicker("Hello")
                .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
                .build();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
