package fr.android.app.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database";

    //Table Event
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_DATE = "date";
    public static final String EVENT_LOCATION_LATITUDE = "location_latitude";
    public static final String EVENT_LOCATION_LONGITUDE = "location_longitude";
    public static final String EVENT_TABLE_NAME = "Event";
    public static final String EVENT_TABLE_CREATE =
            "CREATE TABLE " + EVENT_TABLE_NAME + " (" +
                    EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EVENT_NAME + " TEXT NOT NULL, " +
                    EVENT_DATE + " DATE NOT NULL, " +
                    EVENT_LOCATION_LATITUDE + " FLOAT NOT NULL, " +
                    EVENT_LOCATION_LONGITUDE + " FLOAT NOT NULL);";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    private static DatabaseHandler instance;

    public static synchronized DatabaseHandler getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHandler(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EVENT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
        onCreate(db);
    }

}