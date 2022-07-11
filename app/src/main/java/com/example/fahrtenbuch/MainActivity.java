package com.example.fahrtenbuch;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Deactivate Night Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Init Database
        Database db = new Database(getApplicationContext());
        db.restartDatabase();
        System.out.println("Km Für ein Jahr " + db.getKMPerYear(2022));
        System.out.println("Km im Juni gefahren " + db.getKMInTime("2022 06 01", "2022 07 30"));
        System.out.println("Ausgaben " + db.getAllExpensesPerType());
        System.out.println("Ausgaben für das Tanken " + db.getTypeExpenses(1));
        System.out.println("Ausgaben von April bis einde Juni 2022" + db.getAllExpensesPerTypeTimed("2022 04 01", "2023 06 30"));
        System.out.println("Preis für 1 km " + db.getPricePerKm("2021 06 01", "2022 07 30", 8));

        //Navigation
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_rides, R.id.navigation_expenses, R.id.navigation_statistics, R.id.navigation_settings).build();
        NavController navController = Navigation.findNavController(this, R.id.main_fragment_container);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //Push Notifications
        Bundle bundle = new Bundle();
        Cursor c = db.getNewestRide();
        c.moveToFirst();
        bundle.putInt("Strecke", c.getInt(3));
        PushNotificationHandler pushNotifier = new PushNotificationHandler(this);
        pushNotifier.pushNotifcation(bundle);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}