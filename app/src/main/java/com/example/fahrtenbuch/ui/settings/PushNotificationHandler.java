package com.example.fahrtenbuch.ui.settings;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class PushNotificationHandler {

        Notification notification;
        NotificationManagerCompat notificationManagerCompat;
        Context myContext;
       public PushNotificationHandler(Context context){
           myContext = context;
           if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               NotificationChannel channel = new NotificationChannel("myChannel", "PushNotifier" , NotificationManager.IMPORTANCE_DEFAULT);

               NotificationManager manager = myContext.getSystemService(NotificationManager.class);
               manager.createNotificationChannel(channel);
           }


       }

       public void pushNotifcation(){

           NotificationCompat.Builder  builder = new NotificationCompat.Builder(myContext, "myChannel")
                   .setSmallIcon(android.R.drawable.stat_sys_warning) //Das Icon der Notifikation
                   .setContentTitle("Eine Neue Fahrt!")
                   .setContentText("Ihre Fahrt ging von bis blabla");

           notification = builder.build();

           notificationManagerCompat = NotificationManagerCompat.from(myContext);
           notificationManagerCompat.notify(1, notification);

       }
     }

