package fr.android.app.androidproject.Events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.android.app.androidproject.DAOBase;

import static fr.android.app.androidproject.DatabaseHandler.EVENT_ID;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_NAME;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_DATE;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_BUILDING;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_TABLE_NAME;

public class EventDAO extends DAOBase {

    public EventDAO(Context pContext) {
        super(pContext);
    }

    public boolean createEvent(Event event) {
        ContentValues values = new ContentValues();
        values.put(EVENT_NAME, event.getName());
        values.put(EVENT_DATE, event.getDate());
        values.put(EVENT_BUILDING, event.getBuilding());
        return mDb.insert(EVENT_TABLE_NAME, null, values) > 0;
    }

    public boolean deleteEvent(Event event) {
        return mDb.delete(EVENT_TABLE_NAME, EVENT_ID + " = ?", new String[] {String.valueOf(event.getId())}) > 0;
    }

    public Cursor getAllEventsCursor() {
        return mDb.rawQuery("select " + "id as _id, name, date" + " from " + EVENT_TABLE_NAME + " order by date", null);
    }

}



