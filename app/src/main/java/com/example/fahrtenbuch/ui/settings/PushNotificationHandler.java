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
               NotificationChannel channel = new NotificationChannel("myChannel2", "PushNotifier" , NotificationManager.IMPORTANCE_DEFAULT);

               NotificationManager manager = myContext.getSystemService(NotificationManager.class);
               channel.setSound(null, null);
               manager.createNotificationChannel(channel);




           }
       }

       public void pushNotifcation(String Start, String Ziel, int strecke, int typ){

          Intent resultInt = new Intent(myContext, CreateRideFragment.class);
           resultInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
          resultInt.putExtra("pushRide", "CreateRideFragment");
           PendingIntent resultPendInt = PendingIntent.getActivity(myContext, 0, resultInt, 0);



           NotificationCompat.Builder  builder = new NotificationCompat.Builder(myContext, "myChannel2")
                   .setSmallIcon(android.R.drawable.star_big_on) //Das Icon der Notifikation
                   .setContentTitle("Eine Neue Fahrt!")
                   .setContentText("Ihre Fahrt ging von "+ Start + " bis " + Ziel + " und ging " + strecke + " km :D")
                   .setContentIntent(resultPendInt)
                   ;

           builder.setContentIntent(resultPendInt);
           builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
           builder.setAutoCancel(true);
           notification = builder.build();

           notificationManagerCompat = NotificationManagerCompat.from(myContext);
           notificationManagerCompat.notify(1, notification);

       }
     }

