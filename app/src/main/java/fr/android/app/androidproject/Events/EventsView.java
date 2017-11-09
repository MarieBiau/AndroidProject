package fr.android.app.androidproject.Events;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.app.ListActivity;

import java.util.Date;

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

        plusButton = (Button) findViewById(R.id.plusbutton);
        backButton = (Button) findViewById(R.id.backbutton);

        plusButton.setText("+");
        backButton.setText("Back");

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EventsView.this, MainActivity.class);

                startActivity(intent);
            }
        });

        EventDAO eventDAO = new EventDAO(getApplicationContext());
        eventDAO.open();

        Event a = new Event("a",new Date(),0,0);
        Event b = new Event("b",new Date(),0,0);
        eventDAO.createEvent(a);
        eventDAO.createEvent(b);

        Cursor eventsCursor = eventDAO.getAllEventsCursor();
        startManagingCursor(eventsCursor);
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter
                (this, R.layout.activity_events_view_list, eventsCursor, new String[]{"_id", EVENT_NAME, EVENT_DATE}, new int[]{R.id.id, R.id.name, R.id.date}, 0);
        this.setListAdapter(mAdapter);

    }

}
