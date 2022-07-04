package com.example.fahrtenbuch;

import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Database db = new Database(getApplicationContext());

       db.restartDatabase();
        System.out.println("Km Für ein Jahr " + db.getKMPerYear(2022));
       System.out.println("Km im Juni gefahren " + db.getKMInTime("2022 06 01", "2022 07 30"));
        System.out.println("Ausgaben " + db.getAllExpensesPerType());
        System.out.println("Ausgaben für das Tanken " + db.getTypeExpenses(3));
        System.out.println("Ausgaben von April bis einde Juni 2022" + db.getAllExpensesPerTypeTimed("2022 04 01", "2023 06 30"));
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
        pushNotifier.pushNotifcation(db.getRide(1).getRideLocationStart(), db.getRide(1).getRideDestination(), db.getRide(1).getRideDistance(), db.getRide(1).getRideType());
    }
}