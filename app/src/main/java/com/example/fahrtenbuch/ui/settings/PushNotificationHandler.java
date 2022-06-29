package com.example.fahrtenbuch.ui.settings;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fahrtenbuch.ui.rides.CreateRideFragment;


public class PushNotificationHandler {

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

       public void pushNotifcation(String Start, String Ziel, int strecke, int typ){

          /* Intent resultInt = new Intent(myContext, CreateRideFragment.class);
           TaskStackBuilder stackBuilder = TaskStackBuilder.create(myContext);
           stackBuilder.addNextIntentWithParentStack(resultInt);
           PendingIntent resultPendInt =
                   stackBuilder.getPendingIntent(0,
                           PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);*/

           NotificationCompat.Builder  builder = new NotificationCompat.Builder(myContext, "myChannel")
                   .setSmallIcon(android.R.drawable.star_big_on) //Das Icon der Notifikation
                   .setContentTitle("Eine Neue Fahrt!")
                   .setContentText("Ihre Fahrt ging von "+ Start + " bis " + Ziel + " und ging " + strecke + " km :D")
                   //.setContentIntent(resultPendInt)
                   ;

           notification = builder.build();

           notificationManagerCompat = NotificationManagerCompat.from(myContext);
           notificationManagerCompat.notify(1, notification);

       }
     }

