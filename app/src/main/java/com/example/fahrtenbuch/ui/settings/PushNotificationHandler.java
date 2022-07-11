package com.example.fahrtenbuch.ui.settings;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.fahrtenbuch.MainActivity;
import com.example.fahrtenbuch.R;


public class PushNotificationHandler {
        SettingsFragment sf = new SettingsFragment();
        Notification notification;
        NotificationManagerCompat notificationManagerCompat;
        Context myContext;
       public PushNotificationHandler(Context context){
           this.myContext = context;
           if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               NotificationChannel channel = new NotificationChannel("myChannel", "PushNotifier" , NotificationManager.IMPORTANCE_DEFAULT);
               NotificationManager manager = myContext.getSystemService(NotificationManager.class);
               manager.createNotificationChannel(channel);
           }
       }

       public void pushNotifcation(Bundle arg){
           if(!sf.allowPushNotifications()) return;
           NavDeepLinkBuilder navigationBuilder = new NavDeepLinkBuilder(myContext)
                    .setComponentName(MainActivity.class)
                    .setGraph(R.navigation.mobile_navigation)
                    .setDestination(R.id.editRideFragment)
                    .setArguments(arg);

           PendingIntent pendingIntent = navigationBuilder.createPendingIntent();

           NotificationCompat.Builder  builder = new NotificationCompat.Builder(myContext, "myChannel")
                   .setSmallIcon(R.drawable.auto) //Das Icon der Notifikation
                   .setContentTitle("Ihre Fahrt ging "+ arg.getInt("Strecke") + " KM")
                   .setContentText("Hier klicken um die Fahrt zu bearbeiten!")
                   .setColor(200)
                   .setSilent(true)
                   //.addAction()
                   .setContentIntent(pendingIntent);
           builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
           builder.setAutoCancel(true);
           notification = builder.build();

           notificationManagerCompat = NotificationManagerCompat.from(myContext);
           notificationManagerCompat.notify(1, notification);
       }
     }

