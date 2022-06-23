package com.example.fahrtenbuch.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import android.util.Log;

class Database extends SQLiteOpenHelper {

    private static final String TAG = Database.class.getSimpleName();

    // Name und Version der Datenbank
    private static final String DATABASE_NAME = "ride.db";
    private static final int DATABASE_VERSION = 1;

    // Name und Attribute der Tabelle "Ride"
    public static final String TABLE_NAME_RIDES = "Rides";
    private static final String RIDE_PK = "Ride_id";
    //private static final String RIDE_DISTANCE = "distance";
    public static final String RIDE_START = "start";
    public static final String RIDE_DESTINATION = "destination";
    public static final String RIDE_START_TIME = "timeMillis";
    public static final String RIDE_TYPE = "type";

    // Konstanten für die Art der Fahrt
    public static final int ARBEITSFAHRT = 1;
    public static final int EINKAUFSFAHRT = 2;
    public static final int SONSTIGE_Fahrt = 3;

    // Tabelle Ride anlegen
    private static final String TABLE_RIDE_CREATE = "CREATE TABLE "
            + TABLE_NAME_RIDES + " (" + RIDE_PK + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + RIDE_START + " TEXT, "
            + RIDE_DESTINATION + " TEXT, "
            + RIDE_START_TIME + " INTEGER, "
            + RIDE_TYPE + " INTEGER);";

    // Tabelle Ride löschen
    private static final String TABLE_RIDE_DROP =
            "DROP TABLE IF EXISTS " + TABLE_NAME_RIDES;

    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_RIDE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        Log.w(TAG, "Upgrade der Datenbank von Version "
                + oldVersion + " zu "
                + newVersion + "; alle Daten werden gelöscht");
        //  db.execSQL(TABLE_RIDE_DROP);

        //onCreate(db);
    }

    void insert(int time, int km) { //Fahrt einfügen nur mit Datum und KM
        long rowId = -1;
        try {
            // Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            // Log.d(TAG, "Pfad: " + db.getPath());
            // die zu speichernden Werte
          /*  ContentValues values = new ContentValues();
            values.put(RIDE_START, start);
            values.put(RIDE_DESTINATION, ziel);
            // in die Tabelle ride einfügen
            rowId =  db.insert(TABLE_NAME_RIDES, null, values);
            */
            db.execSQL("INSERT INTO 'Rides' (start, destination)" + "VALUES (" + time + ", " + km + ");");
            String [] columns = new String[] {"max(Ride_id)"};
            Cursor c = db.query( "Rides", columns, null, null, null, null, null);
            c.moveToFirst();
            rowId = c.getInt(0);


        } catch (SQLiteException e) {
            Log.e(TAG, "insert()", e);
        } finally {
            Log.d(TAG, "insert(): rowId=" + rowId);
        }
    }
    void insert(int type, String start, String ziel) {//Fahrt einfügen  mit Start, ziel und Typ
        long rowId = -1;
        try {
            // Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            // Log.d(TAG, "Pfad: " + db.getPath());
            // die zu speichernden Werte
            ContentValues values = new ContentValues();
            values.put(RIDE_TYPE, type);
            values.put(RIDE_START, start);
            values.put(RIDE_DESTINATION, ziel);
            // in die Tabelle ride einfügen
            rowId =  db.insert(TABLE_NAME_RIDES, null, values);
            /*
            db.execSQL("INSERT INTO 'ride' (timeMillis, ride)" + "VALUES (" + timeMillis + ", " + ride + ");");
            String [] columns = new String[] {"max(_ID)"};
            Cursor c = db.query( "ride", columns, null, null, null, null, null);
            c.moveToFirst();
            rowId = c.getInt(0);
            */

        } catch (SQLiteException e) {
            Log.e(TAG, "insert()", e);
        } finally {
            Log.d(TAG, "insert(): rowId=" + rowId);
        }
    }

    void insert(int type, String start, String ziel, long time) {//Fahrt einfügen  mit Start, ziel, Typ und Zeit
        long rowId = -1;
        try {
            // Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            // Log.d(TAG, "Pfad: " + db.getPath());
            // die zu speichernden Werte
            ContentValues values = new ContentValues();
            values.put(RIDE_TYPE, type);
            values.put(RIDE_START, start);
            values.put(RIDE_DESTINATION, ziel);
            values.put(RIDE_START_TIME, time);
            // in die Tabelle ride einfügen
            rowId =  db.insert(TABLE_NAME_RIDES, null, values);
            /*
            db.execSQL("INSERT INTO 'ride' (timeMillis, ride)" + "VALUES (" + timeMillis + ", " + ride + ");");
            String [] columns = new String[] {"max(_ID)"};
            Cursor c = db.query( "ride", columns, null, null, null, null, null);
            c.moveToFirst();
            rowId = c.getInt(0);
            */

        } catch (SQLiteException e) {
            Log.e(TAG, "insert()", e);
        } finally {
            Log.d(TAG, "insert(): rowId=" + rowId);
        }
    }



    Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_NAME_RIDES,
                null, null, null,
                null, null,
                RIDE_START_TIME + " DESC");
    }

    void update(long pk, int type) { // Art der Fahrt ändern
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RIDE_TYPE, type);
        int numUpdated = db.update(TABLE_NAME_RIDES,
                values, RIDE_PK + " = ?", new String[]{Long.toString(pk)});
        Log.d(TAG, "update(): pk=" + pk + " -> " + numUpdated);
    }

    void delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int numDeleted = db.delete(TABLE_NAME_RIDES, RIDE_PK + " = ?",
                new String[]{Long.toString(id)});
        Log.d(TAG, "delete(): id=" + id + " -> " + numDeleted);
    }
}


