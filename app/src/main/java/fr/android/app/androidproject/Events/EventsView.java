package fr.android.app.androidproject.Events;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
    View myview;
    Button seeonmapbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_view);

        /*Plus button*/
        plusButton = (Button) findViewById(R.id.plusbutton);
        plusButton.setText("+");
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EventsView.this, EventsActivity.class);
                startActivity(intent);
            }
        });

        /*Back button*/
        backButton = (Button) findViewById(R.id.backbutton);
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent intent = new Intent(EventsView.this, MainActivity.class);
                startActivity(intent);
            }
        });


        /*seeonmapbtn = (Button) findViewById(R.id.see_on_map);
            seeonmapbtn.setTag("test");
            Toast.makeText(getBaseContext(), "nice", Toast.LENGTH_LONG).show();
            seeonmapbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(EventsView.this, MainActivity.class);
                    startActivity(intent);
                }
            });*/


        /*ViewList with all created events*/
        eventDAO = new EventDAO(getApplicationContext());
        eventDAO.open();
        eventsCursor = eventDAO.getAllEventsCursor();
        startManagingCursor(eventsCursor);
        mAdapter = new MyCursorAdapter
                (this, R.layout.activity_events_view_list, eventsCursor, new String[]{"_id", EVENT_NAME, EVENT_DATE}, new int[]{0, R.id.name, R.id.date});/*{
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                final View row = super.getView(position, convertView, parent);
                for (int i = 0;i < eventsCursor.getCount();i++){
                    if (position == i){
                        row.setBackgroundResource(android.R.color.darker_gray);
                        row.setTag("id" + i);
                        row.setClickable(true);

                        row.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getBaseContext(),"test",Toast.LENGTH_LONG);
                            }
                        });
                    }
                }
                return row;*/
               /* if (position % 2 == 0)
                    row.setBackgroundResource(android.R.color.darker_gray);
                else
                    row.setBackgroundResource(android.R.color.background_light);
                return row;*/
          //  }
        //};
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
            }
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

    /* try */
    public class MyCursorAdapter extends SimpleCursorAdapter {
        public MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int position = cursor.getPosition();
            final Cursor myCursor = cursor;
            int namecln = myCursor.getColumnIndex(EVENT_NAME);
            int datecln = myCursor.getColumnIndex(EVENT_DATE);
            final int buildingcln = myCursor.getColumnIndex(EVENT_BUILDING);

            TextView tv1 = (TextView) view.findViewById(R.id.name);
            TextView tv2 = (TextView) view.findViewById(R.id.date);
            Button btn = (Button) view.findViewById(R.id.see_on_map);
            String s1 = myCursor.getString(namecln);
            String s2 = myCursor.getString(datecln);
            final String building = myCursor.getString(buildingcln);
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
