package fr.android.app.androidproject.Events;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.app.ListActivity;

import fr.android.app.androidproject.Main.MainActivity;
import fr.android.app.androidproject.R;

import static fr.android.app.androidproject.DatabaseHandler.EVENT_NAME;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_DATE;

public class EventsView extends ListActivity {

    Button plusButton;
    Button backButton;

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

        /*ViewList with all created events*/
        EventDAO eventDAO = new EventDAO(getApplicationContext());
        eventDAO.open();
        Cursor eventsCursor = eventDAO.getAllEventsCursor();
        startManagingCursor(eventsCursor);
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter
                (this, R.layout.activity_events_view_list, eventsCursor, new String[]{"_id", EVENT_NAME, EVENT_DATE}, new int[]{0, R.id.name, R.id.date}, 0);
        this.setListAdapter(mAdapter);
    }

}
