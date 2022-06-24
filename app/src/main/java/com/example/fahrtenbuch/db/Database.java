package com.example.fahrtenbuch.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = Database.class.getSimpleName();

    // Name und Version der Datenbank
    private static final String DATABASE_NAME = "ride.db";
    private static final int DATABASE_VERSION = 2;

    // Name und Attribute der Tabelle "Ride"
    public static final String TABLE_NAME_RIDES = "Rides";
    private static final String RIDE_ID = "rideId";
    public static final String RIDE_START = "rideStart";
    public static final String RIDE_DISTANCE = "rideDistance";
    public static final String RIDE_DESTINATION = "rideDestination";
    public static final String RIDE_START_TIME = "rideStartTime";
    public static final String RIDE_TYPE = "type";
    //private static final String RIDE_DISTANCE = "distance";

    // Konstanten für die Art der Fahrt
    public static final int ARBEITSFAHRT = 1;
    public static final int EINKAUFSFAHRT = 2;
    public static final int SONSTIGE_Fahrt = 3;

    // Tabelle Ride anlegen
    private static final String TABLE_RIDE_CREATE = "CREATE TABLE "
            + TABLE_NAME_RIDES + " (" + RIDE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + RIDE_START + " TEXT, "
            + RIDE_DISTANCE + " TEXT, "
            + RIDE_DESTINATION + " TEXT, "
            + RIDE_START_TIME + " INTEGER, "
            + RIDE_TYPE + " INTEGER);";

    // Tabelle Ride löschen
    private static final String TABLE_RIDE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME_RIDES;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase DB) {
    DB.execSQL(TABLE_RIDE_CREATE);
    System.out.println("moin");
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

    public void insert(int time, int km) {
        System.out.println("moin2");
        try {
            // Datenbank öffnen
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues valuesInsert = new ContentValues();
            valuesInsert.put("rideDistance", time);
            valuesInsert.put("rideStartTime", km);
            db.insert("Rides",null, valuesInsert);
           // rowId = cursor.getInt(0);
        } catch (SQLiteException e) {
            Log.e(TAG, "insert()", e);
        } finally {
           // Log.d(TAG, "insert(): rowId=" + rowId);
        }
    }



    public Cursor query() {
       SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_NAME_RIDES, null, null, null, null, null, RIDE_START_TIME + " DESC");
    }

    public void update(long pk, int type) { // Art der Fahrt ändern
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RIDE_TYPE, type);
        int numUpdated = db.update(TABLE_NAME_RIDES,
                values, RIDE_ID + " = ?", new String[]{Long.toString(pk)});
        Log.d(TAG, "update(): pk=" + pk + " -> " + numUpdated);
    }

    public void delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int numDeleted = db.delete(TABLE_NAME_RIDES, RIDE_ID + " = ?", new String[]{Long.toString(id)});
        Log.d(TAG, "delete(): id=" + id + " -> " + numDeleted);
    }
}


