package com.example.fahrtenbuch.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import android.util.Log;

import com.example.fahrtenbuch.ui.rides.FahrtItem;
import com.example.fahrtenbuch.ui.rides.ListObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Database extends SQLiteOpenHelper {

    private static final String TAG = Database.class.getSimpleName();

    // Name und Version der Datenbank
    private static final String DATABASE_NAME = "ride.db";
    private static final int DATABASE_VERSION = 5;
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



    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void restartDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(TABLE_RIDE_DROP);
        db.execSQL(TABLE_RIDE_CREATE);
        Random random = new Random();
        for (int i=0; i<25; i++){
            Date startDate = new Date("03/01/2022 15:05:24");
            Date endDate = new Date("06/22/2022 23:25:12");
            long randTime = startDate.getTime()+((long)(random.nextDouble()*(endDate.getTime()-startDate.getTime())));;
            Date date = new Date(randTime);
            System.out.println(date);
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_RIDE_START_TIME, date.getTime());
            contentValues.put(COLLUMN_RIDE_DISTANCE, random.nextInt(80)+5);
            contentValues.put(COLLUMN_RIDE_TYPE, random.nextInt(5));
            db.insert(TABLE_NAME_RIDES,null, contentValues);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(TABLE_RIDE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion,
                          int newVersion) {
        Log.w(TAG, "Upgrade der Datenbank von Version "
                + oldVersion + " zu "
                + newVersion + "; alle Daten werden gelöscht");
          DB.execSQL(TABLE_RIDE_DROP);
          DB.execSQL(TABLE_RIDE_CREATE);
    }

    public long insert(long time, int km, int rideType) {
        try {
            // Datenbank öffnen
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLLUMN_RIDE_START_TIME, time);
            contentValues.put(COLLUMN_RIDE_DISTANCE, km);
            contentValues.put(COLLUMN_RIDE_TYPE, rideType);
            long rowID = db.insert("Rides",null, contentValues);
            return rowID;
        } catch (SQLiteException e) {
            System.out.println("insert error");
        }
        return -1;
    }

    public FahrtItem getRide(int id){
        Cursor cursor = query();
        cursor.moveToFirst();
        cursor.move(id);
        int rideDistance = cursor.getInt(3);
        Date rideStartTime = new Date(Long.parseLong(cursor.getString(4)));
        int rideType = cursor.getInt(5);
        FahrtItem fahrtItem = new FahrtItem(rideStartTime, rideDistance, rideType);
        return fahrtItem;
    }

    public ArrayList<FahrtItem> getAllRides(){
        ArrayList<FahrtItem> eintraege_liste = new ArrayList<>();
        Cursor cursor = query();
        while (cursor.moveToNext()){
            int rideDistance = cursor.getInt(3);
            Date rideStartTime = new Date(cursor.getLong(4));
            int rideType = cursor.getInt(5);
            eintraege_liste.add(new FahrtItem(rideStartTime, rideDistance, rideType));
        }

        return eintraege_liste;
    }

    public Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_NAME_RIDES, null, null, null, null, null, COLLUMN_RIDE_START_TIME + " DESC");
    }

    public void update(long pk, int distance) { // Art der Fahrt ändern
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLLUMN_RIDE_DISTANCE, distance);
        int numUpdated = db.update(TABLE_NAME_RIDES,
                values, COLLUMN_RIDE_ID + " = ?", new String[]{Long.toString(pk)});
        Log.d(TAG, "update(): pk=" + pk + " -> " + numUpdated);
    }

    public void delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int numDeleted = db.delete(TABLE_NAME_RIDES, COLLUMN_RIDE_ID + " = ?", new String[]{Long.toString(id)});
        Log.d(TAG, "delete(): id=" + id + " -> " + numDeleted);
    }
}


