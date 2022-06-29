package com.example.fahrtenbuch;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

        System.out.println("main activity started");
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_rides, R.id.navigation_outgoings, R.id.navigation_statistics, R.id.navigation_settings).build();
        NavController navController = Navigation.findNavController(this, R.id.main_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        PushNotificationHandler pushNotifier = new PushNotificationHandler(this);
        String start = db.getRide(0).getRideLocationStart();
        pushNotifier.pushNotifcation(start, "Frankfurt", db.getRide(0).getRideDistance(), 2);

    }

}