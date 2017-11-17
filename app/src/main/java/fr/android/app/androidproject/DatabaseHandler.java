package fr.android.app.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Table Event
    public static final String EVENT_TABLE_NAME = "Event";

    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "name";
    public static final String EVENT_DATE = "date";
    public static final String EVENT_BUILDING = "building";

    public static final String EVENT_TABLE_CREATE =
            "CREATE TABLE " + EVENT_TABLE_NAME + " (" +
                    EVENT_ID + " INTEGER PRIMARY KEY, " +
                    EVENT_NAME + " VARCHAR(50) NOT NULL, " +
                    EVENT_DATE + " VARCHAR(50) NOT NULL, " +
                    EVENT_BUILDING + " VARCHAR(50) NOT NULL);";
    public static final String EVENT_TABLE_DROP = "DROP TABLE IF EXISTS " + EVENT_TABLE_NAME;

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EVENT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EVENT_TABLE_DROP);
        onCreate(db);
    }

}