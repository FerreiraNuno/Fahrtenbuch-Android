package com.example.fahrtenbuch.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class Database extends SQLiteOpenHelper {

    private static final String TAG = Database.class.getSimpleName();

    // Name und Version der Datenbank
    private static final String DATABASE_NAME = "ride.db";

    private static final int DATABASE_VERSION = 5;
    DateFormat dfY = new SimpleDateFormat("yyyy");

    private float literDiesel = 1.98f;
    private float literBenzin = 1.72f;
    private float verbrauchDiesel100km = 6.6f;
    private float verbrauchBenzin100km = 7.2f;


    // Name und Attribute der Tabelle "Ride"
    public static final String TABLE_NAME_RIDES = "Rides";
    private static final String COLLUMN_RIDE_ID = "rideId";
    public static final String  COLLUMN_RIDE_LOCATION_START = "rideLocationStart";
    public static final String  COLLUMN_RIDE_LOCATION_DESTINATION = "rideDestination";
    public static final String  COLLUMN_RIDE_DISTANCE = "rideDistance";
    public static final String  COLLUMN_RIDE_START_TIME = "rideStartTime";
    public static final String  COLLUMN_RIDE_TYPE = "type";
    // Konstanten für die Art der Fahrt
    public static final int ARBEITSFAHRT = 1;
    public static final int UNIFAHRT = 2;
    public static final int SPORTFAHRT = 3;
    public static final int EINKAUFSFAHRT = 4;
    public static final int SONSTIGE_Fahrt = 5;
    // SQL Befehle
    private static final String TABLE_RIDE_CREATE = "CREATE TABLE "
            + TABLE_NAME_RIDES + " (" + COLLUMN_RIDE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLLUMN_RIDE_LOCATION_START + " TEXT, "
            + COLLUMN_RIDE_LOCATION_DESTINATION + " TEXT, "
            + COLLUMN_RIDE_DISTANCE + " INTEGER, "
            + COLLUMN_RIDE_START_TIME + " INTEGER, "
            + COLLUMN_RIDE_TYPE + " INTEGER);";
    private static final String TABLE_RIDE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME_RIDES;



    // Name und Attribute der Tabelle "Expenses"
    public static final String TABLE_NAME_EXPENSES = "Expenses";
    private static final String COLLUMN_EXPENSE_ID = "expenseId";
    private static final String COLLUMN_EXPENSE_AMMOUNT = "expenseAmmount";
    public static final String  COLLUMN_EXPENSE_TIME = "expenseTime";
    public static final String  COLLUMN_EXPENSE_TYPE = "expenseType";
    public static final String  COLLUMN_EXPENSE_INTERVAL = "expenseInterval";
    // Konstanten für die Art der Ausgabe
    public static final int TANKEN = 1;
    public static final int VERSICHERUNG = 2;
    public static final int KFZ_STEUER = 3;
    public static final int WERKSTATT = 4;
    public static final int SONSTIGE_AUSGABE = 5;
    // SQL Befehle
    private static final String TABLE_EXPENSES_CREATE = "CREATE TABLE "
            + TABLE_NAME_EXPENSES + " (" + COLLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLLUMN_EXPENSE_AMMOUNT + " INTEGER, "
            + COLLUMN_EXPENSE_TIME + " TEXT, "
            + COLLUMN_EXPENSE_TYPE + " INTEGER, "
            + COLLUMN_EXPENSE_INTERVAL + " INTEGER);";
    private static final String TABLE_EXPENSES_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME_EXPENSES;


    // Name und Attribute der Tabelle "Orte"
    public static final String TABLE_NAME_ORTE = "Orte";
    private static final String COLLUMN_ORT_ID = "ortId";
    private static final String COLLUMN_ORT_NAME = "ortName";
    private static final String COLLUMN_ORT_LONGITUDE = "ortLongitude";
    private static final String COLLUMN_ORT_LATITUDE = "ortLatitude";

    // SQL Befehle
    private static final String TABLE_ORTE_CREATE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_ORTE + " (" + COLLUMN_ORT_ID + " ,"
            + COLLUMN_ORT_NAME + " TEXT, "
            + COLLUMN_ORT_LONGITUDE + " TEXT, "
            + COLLUMN_ORT_LATITUDE + " TEXT);";

    private static final String TABLE_BLUETOOTH_CREATE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS 'BluetoothGerät' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'MacAdresse' TEXT);";


    SQLiteDatabase db;


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();

        db.execSQL(TABLE_BLUETOOTH_CREATE_IF_NOT_EXISTS);
        if (db.rawQuery("select * from " + "BluetoothGerät", null).getCount() == 0) {
            ContentValues macValues = new ContentValues();
            macValues.put("MacAdresse", "leer");
            db.insert("BluetoothGerät", null, macValues);
        }
        db.execSQL(TABLE_ORTE_CREATE_IF_NOT_EXISTS);
    }

    public void restartDatabase() {
        db.execSQL(TABLE_ORTE_CREATE_IF_NOT_EXISTS);

        db.execSQL(TABLE_RIDE_DROP);
        db.execSQL(TABLE_RIDE_CREATE);
        Random random = new Random();
        for (int i=0; i<700; i++){
            Date startDate = new Date("07/10/2018 15:05:24");
            Date endDate = new Date("07/11/2022 18:45:12");
            long randTime = startDate.getTime()+((long)(random.nextDouble()*(endDate.getTime()-startDate.getTime())));;
            Date date = new Date(randTime);
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_RIDE_START_TIME, date.getTime());
            contentValues.put(COLLUMN_RIDE_DISTANCE, random.nextInt(80)+5);
            contentValues.put(COLLUMN_RIDE_TYPE, random.nextInt(4)+1);
            db.insert(TABLE_NAME_RIDES,null, contentValues);
        }

        db.execSQL(TABLE_EXPENSES_DROP);
        db.execSQL(TABLE_EXPENSES_CREATE);
        for (int i=0; i<100; i++){
            Date startDate = new Date("08/10/2018 15:05:24");
            Date endDate = new Date("07/02/2022 09:18:12");
            long randTime = startDate.getTime()+((long)(random.nextDouble()*(endDate.getTime()-startDate.getTime())));;
            Date date = new Date(randTime);
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_EXPENSE_TIME, date.getTime());
            contentValues.put(COLLUMN_EXPENSE_AMMOUNT, random.nextInt(45)+25);
            contentValues.put(COLLUMN_EXPENSE_TYPE, 1);
            db.insert(TABLE_NAME_EXPENSES,null, contentValues);
        }
        for (int i=0; i<10; i++){
            Date startDate = new Date("08/10/2021 15:05:24");
            Date endDate = new Date("07/02/2022 09:18:12");
            long randTime = startDate.getTime()+((long)(random.nextDouble()*(endDate.getTime()-startDate.getTime())));;
            Date date = new Date(randTime);
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_EXPENSE_TIME, date.getTime());
            contentValues.put(COLLUMN_EXPENSE_AMMOUNT, random.nextInt(250)+100);
            contentValues.put(COLLUMN_EXPENSE_TYPE, 2);
            db.insert(TABLE_NAME_EXPENSES,null, contentValues);
        }
        for (int i=0; i<25; i++){
            Date startDate = new Date("07/03/2018 15:05:24");
            Date endDate = new Date("07/02/2022 09:18:12");
            long randTime = startDate.getTime()+((long)(random.nextDouble()*(endDate.getTime()-startDate.getTime())));
            Date date = new Date(randTime);
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_EXPENSE_TIME, date.getTime());
            contentValues.put(COLLUMN_EXPENSE_AMMOUNT, random.nextInt(500)+250);
            contentValues.put(COLLUMN_EXPENSE_TYPE, 3);
            db.insert(TABLE_NAME_EXPENSES,null, contentValues);
        }

        for (int i=0; i<5; i++){
            Date startDate = new Date("07/03/2018 15:05:24");
            Date endDate = new Date("07/02/2022 09:18:12");
            long randTime = startDate.getTime()+((long)(random.nextDouble()*(endDate.getTime()-startDate.getTime())));
            Date date = new Date(randTime);
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_EXPENSE_TIME, date.getTime());
            contentValues.put(COLLUMN_EXPENSE_AMMOUNT, random.nextInt(70)+15);
            contentValues.put(COLLUMN_EXPENSE_TYPE, 4);
            db.insert(TABLE_NAME_EXPENSES,null, contentValues);
        }
        for (int i=0; i<30; i++){
            Date startDate = new Date("07/03/2018 15:05:24");
            Date endDate = new Date("07/02/2022 09:18:12");
            long randTime = startDate.getTime()+((long)(random.nextDouble()*(endDate.getTime()-startDate.getTime())));;
            Date date = new Date(randTime);
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_EXPENSE_TIME, date.getTime());
            contentValues.put(COLLUMN_EXPENSE_AMMOUNT, random.nextInt(70)+15);
            contentValues.put(COLLUMN_EXPENSE_TYPE, 5);
            db.insert(TABLE_NAME_EXPENSES,null, contentValues);
        }
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(TABLE_RIDE_CREATE);
        DB.execSQL(TABLE_EXPENSES_CREATE);
        DB.execSQL(TABLE_ORTE_CREATE_IF_NOT_EXISTS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrade der Datenbank von Version "
                + oldVersion + " zu "
                + newVersion + "; alle Daten werden gelöscht");
        DB.execSQL(TABLE_RIDE_DROP);
        DB.execSQL(TABLE_RIDE_CREATE);
    }

    ///
    // SETTINGS DB
    ///
    public void insertBluetoothDevice(String macAddress) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MacAdresse", macAddress);
        db.update("BluetoothGerät", contentValues,  "id = ?", new String[]{"1"});
    }
    public String getBluetoothDevice() {
        Cursor cursor = db.rawQuery("select * from " + "BluetoothGerät" + " where " + "id" + "=" + 1 , null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }


    ///
    // RIDES DB
    ///
    public ArrayList<FahrtItem> getAllRides(){
        ArrayList<FahrtItem> fahrtItems = new ArrayList<>();
        Cursor cursor = queryAllRides();
        while (cursor.moveToNext()){
            int rideId = cursor.getInt(0);
            int rideDistance = cursor.getInt(3);
            Date rideStartTime = new Date(cursor.getLong(4));
            int rideType = cursor.getInt(5);
            fahrtItems.add(new FahrtItem(rideStartTime, rideDistance, rideType, rideId));
        }
        return fahrtItems;
    }
    public FahrtItem getRide(int inputId){
        Cursor cursor;
        if (inputId == 0) {
            cursor = getNewestRide();
        } else {
            cursor = queryRideById(inputId);
        }
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        int rideDistance = cursor.getInt(3);
        Date rideStartTime = new Date(Long.parseLong(cursor.getString(4)));
        int rideType = cursor.getInt(5);
        return new FahrtItem(rideStartTime, rideDistance, rideType, id);
    }
    public void insertRide(long time, int km, int rideType) {
        try {
            // Datenbank öffnen
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_RIDE_START_TIME, time);
            contentValues.put(COLLUMN_RIDE_DISTANCE, km);
            contentValues.put(COLLUMN_RIDE_TYPE, rideType);
            db.insert("Rides", null, contentValues);
        } catch (SQLiteException e) {
            System.out.println("insert error");
        }
    }
    public void updateRide(int id, long time, int km, int rideType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLLUMN_RIDE_START_TIME, time);
        contentValues.put(COLLUMN_RIDE_DISTANCE, km);
        contentValues.put(COLLUMN_RIDE_TYPE, rideType);
        int numChanged = db.update(TABLE_NAME_RIDES, contentValues, COLLUMN_RIDE_ID + " = ?", new String[]{Long.toString(id)});
        Log.d(TAG, "delete(): id=" + id + " -> " + numChanged);
    }
    public void deleteRide(long id) {
        int numDeleted = db.delete(TABLE_NAME_RIDES, COLLUMN_RIDE_ID + " = ?", new String[]{Long.toString(id)});
        Log.d(TAG, "delete(): id=" + id + " -> " + numDeleted);
    }
    public Cursor queryRideById(int id) {
        return db.rawQuery("select * from " + TABLE_NAME_RIDES + " where " + COLLUMN_RIDE_ID + "=" + id , null);
    }
    public Cursor queryAllRides() {
        return db.query(TABLE_NAME_RIDES, null, null, null, null, null, COLLUMN_RIDE_START_TIME + " DESC");
    }
    public Cursor getNewestRide() {
        return db.rawQuery("select * from " + TABLE_NAME_RIDES + " order by  " + COLLUMN_RIDE_START_TIME + " desc limit 1"  , null);
    }

    ///
    // EXPENSES DB
    ///
    public ArrayList<ExpenseItem> getAllExpenses(){
        ArrayList<ExpenseItem> expenseItems = new ArrayList<>();
        Cursor cursor = queryAllExpenses();
        while (cursor.moveToNext()){
            int expenseId = cursor.getInt(0);
            int expenseAmmount = cursor.getInt(1);
            Date expenseTime = new Date(cursor.getLong(2));
            int expenseType = cursor.getInt(3);
            int expenseInterval = cursor.getInt(4);
            expenseItems.add(new ExpenseItem(expenseId, expenseAmmount, expenseTime, expenseType, expenseInterval));
        }
        return expenseItems;
    }
    public ExpenseItem getExpense(int id){
        Cursor cursor = queryExpenseById(id);
        cursor.moveToFirst();
        int expenseAmmount = cursor.getInt(1);
        Date expenseTime = new Date(Long.parseLong(cursor.getString(2)));
        int expenseType = cursor.getInt(3);
        int expenseInterval = cursor.getInt(4);
        return new ExpenseItem(id, expenseAmmount, expenseTime, expenseType, expenseInterval);
    }
    public long insertExpense(int ammount, long time, int expenseType, int expenseInterval) {
        try {
            // Datenbank öffnen
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_EXPENSE_AMMOUNT, ammount);
            contentValues.put(COLLUMN_EXPENSE_TIME, time);
            contentValues.put(COLLUMN_EXPENSE_TYPE, expenseType);
            contentValues.put(COLLUMN_EXPENSE_INTERVAL, expenseType);
            return db.insert(TABLE_NAME_EXPENSES,null, contentValues);
        } catch (SQLiteException e) {
            System.out.println("insert error");
        }
        return -1;
    }
    public void updateExpense(int id, int ammount, long time, int expenseType, int expenseInterval) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLLUMN_EXPENSE_AMMOUNT, ammount);
        contentValues.put(COLLUMN_EXPENSE_TIME, time);
        contentValues.put(COLLUMN_EXPENSE_TYPE, expenseType);
        contentValues.put(COLLUMN_EXPENSE_INTERVAL, expenseType);
        int numChanged = db.update(TABLE_NAME_EXPENSES, contentValues, COLLUMN_EXPENSE_ID + " = ?", new String[]{Long.toString(id)});
        Log.d(TAG, "delete(): id=" + id + " -> " + numChanged);
    }
    public void deleteExpense(long id) {
        int numDeleted = db.delete(TABLE_NAME_EXPENSES, COLLUMN_EXPENSE_ID + " = ?", new String[]{Long.toString(id)});
        Log.d(TAG, "delete(): id=" + id + " -> " + numDeleted);
    }
    public Cursor queryExpenseById(int id) {
        return db.rawQuery("select * from " + TABLE_NAME_EXPENSES + " where " + COLLUMN_EXPENSE_ID + "=" + id , null);
    }
    public Cursor queryAllExpenses() {
        return db.query(TABLE_NAME_EXPENSES, null, null, null, null, null, COLLUMN_EXPENSE_TIME + " DESC");
    }

    ///
    // ORTE
    ///
    public long insertLocation(String latitude, String longitude, String categorie) {
        try {
            // Datenbank öffnen
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_ORT_LATITUDE, latitude);
            contentValues.put(COLLUMN_ORT_LONGITUDE, longitude);
            contentValues.put(COLLUMN_ORT_NAME, categorie);
            return db.insert(TABLE_NAME_ORTE,null, contentValues);
        } catch (SQLiteException e) {
            System.out.println("insert error");
        }
        return -1;
    }

    //
    //Statistik DB Abfragen
    //
    public int getKMPerYear(int year){ // Alle gefahrenen Km von einen gewähleten jahre
            int sumOfYear = 0;
          ArrayList<FahrtItem> fahrten = getAllRides();
           for (FahrtItem fahrt:fahrten) {
            if(Integer.parseInt(dfY.format(fahrt.getDatum().getTime())) == year)
                sumOfYear += fahrt.getRideDistance();
           }
        return  sumOfYear;

    }
    public ArrayList<Integer> getKMInTime(String von, String bis){ // Alle gefahrenen km pro Typ im Zeitraum von - bis, sortiert nach typ
        // ARBEITSFAHRT = 1; UNIFAHRT = 2; SPORTFAHRT = 3; EINKAUFSFAHRT = 4; SONSTIGE_Fahrt = 5;
        Cursor c = db.rawQuery( "Select sum (rideDistance), type, strftime('%Y %m %d', rideStartTime/1000 ,'unixepoch'), type from Rides " +
                "where '" +  von + "' <= strftime('%Y %m %d', rideStartTime/1000 ,'unixepoch') " +
                "and strftime('%Y %m %d', rideStartTime/1000 ,'unixepoch') <= '" +  bis +
                "' Group by type order by type asc ",null);
        ArrayList<Integer> rideDistance = new ArrayList<Integer>();
        int arbeit    = 0;
        int uni       = 0;
        int sport     = 0;
        int einkauf   = 0;
        int sonstige  = 0;
        if(c.moveToFirst()){
            arbeit = c.getInt(0);
        while(c.moveToNext()){
            switch (c.getInt(1)){
                case 2 : uni      = c.getInt(0); break;
                case 3 : sport    = c.getInt(0); break;
                case 4 : einkauf  = c.getInt(0); break;
                case 5 : sonstige = c.getInt(0); break;
            }
          }
        }
        rideDistance.add(arbeit);
        rideDistance.add(uni);
        rideDistance.add(sport);
        rideDistance.add(einkauf);
        rideDistance.add(sonstige);

        return rideDistance;

    }
    public int getExpensesInTime(String von, String bis){ // Alle Ausgaben  pro Typ im Zeitraum von - bis

        Cursor c = db.rawQuery( "Select sum (expenseAmmount), expenseType from Expenses " +
                "where '" +  von + "' <= strftime('%Y %m %d', expenseTime/1000 ,'unixepoch') " +
                "and strftime('%Y %m %d', expenseTime/1000 ,'unixepoch') <= '" +  bis +
                "'",null);
        int allExpenses = 0;
        if(c.moveToFirst()){
            allExpenses = c.getInt(0);
            }else allExpenses = -1;
        return allExpenses;
    }
    public ArrayList<Integer> getAllExpensesPerType () { //Gibt alle Ausgaben per Kategorie als Arrayliste zurück(
        ArrayList<Integer> expenses = new ArrayList<Integer>();
        Cursor c = db.rawQuery("Select sum (expenseAmmount), expenseType from Expenses " +
                "Group by expenseType order by expenseType asc ", null);

        int tanken       = 0;
        int versicherung = 0;
        int kfz          = 0;
        int werkstatt    = 0;
        int sonstige     = 0;

        if (c.moveToFirst()) {
            if (c.getInt(1) == 1) tanken = c.getInt(0);
            while (c.moveToNext()) {
                switch (c.getInt(1)){
                    case 2:  versicherung = c.getInt(0);break;
                    case 3 : kfz = c.getInt(0);break;
                    case 4 : werkstatt = c.getInt(0);break;
                    case 5 : sonstige = c.getInt(0);break;
                }
            }
        }
        expenses.add(tanken);
        expenses.add(versicherung);
        expenses.add(kfz);
        expenses.add(werkstatt);
        expenses.add(sonstige);
        return expenses;

    }
    public ArrayList<Integer> getAllExpensesPerTypeTimed (String von, String bis){ //Gibt alle Ausgaben per Kategorie als Arrayliste zurück(
            ArrayList<Integer> expenses = new ArrayList<Integer>();
            Cursor c = db.rawQuery( "Select sum (expenseAmmount), expenseType from Expenses " +
                    "where '" +  von + "' <= strftime('%Y %m %d', expenseTime/1000 ,'unixepoch') " +
                    "and strftime('%Y %m %d', expenseTime/1000 ,'unixepoch') <= '" +  bis +
                    "' Group by expenseType order by expenseType asc ",null);
            int tanken       = 0;
            int versicherung = 0;
            int kfz          = 0;
            int werkstatt    = 0;
            int sonstige     = 0;


            if(c.moveToFirst()) {
                if (c.getInt(1) == 1)tanken = c.getInt(0);
                while (c.moveToNext()){
                   switch (c.getInt(1)){
                        case 2: versicherung = c.getInt(0); break;
                        case 3: kfz = c.getInt(0); break;
                        case 4: werkstatt = c.getInt(0); break;
                        case 5: sonstige = c.getInt(0); break;
                    }
                }
            }
            expenses.add(tanken);
            expenses.add(versicherung);
            expenses.add(kfz);
            expenses.add(werkstatt);
            expenses.add(sonstige);
            return expenses;
    }
    public int getTypeExpenses (int typ){
        // 0 = tanken, 1 = Versicherung, 2 = Kfz-Steuer, 3 = Werkstatt, 4 = sonstige
        Cursor c = db.rawQuery( "Select sum (expenseAmmount) from Expenses " +
                "where expenseType = '"+ typ +"'  order by expenseType asc ",null);
      if(c.moveToFirst()) {
          return c.getInt(0);
      }
      return  0;
    }
    public ArrayList<Float> getPricePerKm(String von, String bis, int aufMonate) {
       ArrayList<Float>  preisProKm = new ArrayList<>();
       float kmPreisDiesel;
       float kmPreisBenzin;
        int kmInTime = 0;
        int ausgabenOhneTanken = 0;
       ArrayList<Integer> ausgaben = getAllExpensesPerTypeTimed(von, bis);
        ArrayList<Integer> kmgefahren = getKMInTime(von, bis);
        for (int km: kmgefahren) {
            kmInTime += km;
        }
        for (int i = 0; i < ausgaben.size() - 1; i++){
            ausgabenOhneTanken += ausgaben.get(i);
        }
       // System.out.println("Test " + getKMInTime(von , bis));
        kmPreisDiesel = (literDiesel * verbrauchDiesel100km / 100);
        kmPreisBenzin = (literBenzin * verbrauchBenzin100km / 100);
        preisProKm.add(kmPreisDiesel);// Preis für 1 km für den Sprit
        preisProKm.add(kmPreisBenzin);
        preisProKm.add(kmPreisDiesel * kmInTime);//Geld fürs Tanken alleine
        preisProKm.add(kmPreisBenzin * kmInTime);
        float absolutKostenDiesel = kmPreisDiesel + ausgabenOhneTanken / kmInTime;
        float absolutkostenBenzin = kmPreisBenzin + ausgabenOhneTanken / kmInTime;
        preisProKm.add(absolutKostenDiesel);// Km preis mit ausgaben
        preisProKm.add(absolutkostenBenzin);
        preisProKm.add(absolutKostenDiesel / aufMonate);//Auf Monate
        preisProKm.add(absolutkostenBenzin / aufMonate);
        return preisProKm;
    }

}


