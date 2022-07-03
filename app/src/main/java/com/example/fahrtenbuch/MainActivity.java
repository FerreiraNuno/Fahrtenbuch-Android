package com.example.fahrtenbuch;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fahrtenbuch.databinding.ActivityMainBinding;
import com.example.fahrtenbuch.db.Database;
import com.example.fahrtenbuch.ui.settings.PushNotificationHandler;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database db = new Database(getApplicationContext());
        db.restartDatabase();

        System.out.println("Verbrauch in 2022 " + db.getKMPerYear(2022));
        System.out.println("Verbrauch in 2022, Juni "+db.getKMPerMonth(2022)[5]);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_rides, R.id.navigation_expenses, R.id.navigation_statistics, R.id.navigation_settings).build();
        NavController navController = Navigation.findNavController(this, R.id.main_fragment_container);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        PushNotificationHandler pushNotifier = new PushNotificationHandler(this);
        pushNotifier.pushNotifcation("München", "Frankfurt", db.getRide(1).getRideDistance(), 2);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {

            String data = intent.getStringExtra("pushRide");

            if (data != null) {

                //Fragment fragment = new NotificationActivity() ;
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

            }


        }


    }
}