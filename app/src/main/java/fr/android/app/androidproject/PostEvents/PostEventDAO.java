package fr.android.app.androidproject.PostEvents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.android.app.androidproject.DAOBase;

import static fr.android.app.androidproject.DatabaseHandler.EVENT_DATE;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_ID;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_NAME;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_NOTE;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_PICTURES;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_TABLE_NAME;
import static fr.android.app.androidproject.DatabaseHandler.POSTEVENT_TABLE_NAME;

public class PostEventDAO extends DAOBase {

    public PostEventDAO(Context pContext) {
        super(pContext);
    }

    public boolean createPostEvent(PostEvent postevent) {
        ContentValues values = new ContentValues();
        values.put(EVENT_NAME, postevent.getName());
        values.put(EVENT_DATE, postevent.getDate());
        values.put(EVENT_NOTE, postevent.getNote());
        values.put(EVENT_PICTURES, String.valueOf(postevent.getPictures()));
        return mDb.insert(POSTEVENT_TABLE_NAME, null, values) > 0;
    }

    public boolean deletePostEvent(int id) {
        return mDb.delete(POSTEVENT_TABLE_NAME, EVENT_ID + " = ?", new String[] {String.valueOf(id)}) > 0;
    }

    public Cursor getAllPostEventsCursor() {
        return mDb.rawQuery("select " + "id as _id, name, date" + " from " + POSTEVENT_TABLE_NAME + " order by date", null);
    }

}



