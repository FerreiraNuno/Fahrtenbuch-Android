package com.example.fahrtenbuch;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fahrtenbuch.background.BluetoothBroadcastReceiver;
import com.example.fahrtenbuch.background.RideTracker;
import com.example.fahrtenbuch.databinding.ActivityMainBinding;
import com.example.fahrtenbuch.db.Database;
import com.example.fahrtenbuch.db.ExpenseItem;
import com.example.fahrtenbuch.ui.settings.PushNotificationHandler;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init Database
        db = new Database(getApplicationContext());
        //db.restartDatabase();

        //Set View
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Deactivate Night Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Navigation
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_rides, R.id.navigation_expenses, R.id.navigation_statistics, R.id.navigation_settings).build();
        NavController navController = Navigation.findNavController(this, R.id.main_fragment_container);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //Test
        requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 10);
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Navigation.findNavController(this, R.id.main_fragment_container).navigateUp();
        return true;
    }
}