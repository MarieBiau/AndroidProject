package fr.android.app.androidproject.Events;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import fr.android.app.androidproject.Main.MainActivity;
import fr.android.app.androidproject.Maps.MapsView;
import fr.android.app.androidproject.R;

import static fr.android.app.androidproject.DatabaseHandler.EVENT_BUILDING;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_DATE;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_NAME;

public class EventsView extends ListActivity {

    Button plusButton;
    Button backButton;
    ListView mListView;
    EventDAO eventDAO;
    Cursor eventsCursor;
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_view);

        /*Plus button*/
        plusButton = (Button) findViewById(R.id.plusbutton);
        plusButton.setText(R.string.plus_string);
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EventsView.this, EventsActivity.class);
                startActivity(intent);
            }
        });

        /*Back button*/
        backButton = (Button) findViewById(R.id.backbutton);
        backButton.setText(R.string.back_string);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent intent = new Intent(EventsView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*ViewList with all created events*/
        eventDAO = new EventDAO(getApplicationContext());
        eventDAO.open();
        eventsCursor = eventDAO.getAllEventsCursor();
        startManagingCursor(eventsCursor);
        mAdapter = new MyCursorAdapter
                (this, R.layout.activity_events_view_list, eventsCursor,
                        new String[]{"_id", EVENT_NAME, EVENT_DATE},
                        new int[]{0, R.id.name, R.id.date}, 0);
        this.setListAdapter(mAdapter);

        /*Delete events*/
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setItemsCanFocus(false);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eventsCursor.moveToPosition(position);
                String name = eventsCursor.getString(eventsCursor.getColumnIndex(EVENT_NAME));
                deleteConfirmation((int)id, name);
                return true;
            }c
        });
    }

    /*Delete events confirmation*/
    private void deleteConfirmation(final int id, String name)
    {
        AlertDialog mDialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to Delete the event " + name + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        eventDAO.deleteEvent(id);
                        eventsCursor.requery();
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(EventsView.this, "Event Deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        mDialogBox.show();
    }

    /* Cursor Adapter */
    private class MyCursorAdapter extends SimpleCursorAdapter {

        MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int nameCln = cursor.getColumnIndex(EVENT_NAME);
            int dateCln = cursor.getColumnIndex(EVENT_DATE);
            final int buildingCln = cursor.getColumnIndex(EVENT_BUILDING);

            TextView tv1 = (TextView) view.findViewById(R.id.name);
            TextView tv2 = (TextView) view.findViewById(R.id.date);
            Button btn = (Button) view.findViewById(R.id.see_on_map);

            String s1 = cursor.getString(nameCln);
            String s2 = cursor.getString(dateCln);
            final String building = cursor.getString(buildingCln);

            tv1.setText(s1);
            tv2.setText(s2);

            btn.setClickable(true);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventsView.this, MapsView.class);
                    intent.putExtra("buildingFromEvent",building);
                    startActivity(intent);
                }
            });
        }

    }

}
