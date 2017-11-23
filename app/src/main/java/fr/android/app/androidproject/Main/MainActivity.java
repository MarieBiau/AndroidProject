package fr.android.app.androidproject.Main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import fr.android.app.androidproject.Events.EventDAO;
import fr.android.app.androidproject.Events.EventsView;
import fr.android.app.androidproject.Maps.MapsView;
import fr.android.app.androidproject.PostEvents.PostEvent;
import fr.android.app.androidproject.PostEvents.PostEventDAO;
import fr.android.app.androidproject.PostEvents.PostEventsView;
import fr.android.app.androidproject.R;

public class MainActivity extends AppCompatActivity {

    TextView textBloc1;
    TextView textBloc2;
    TextView textBloc3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        /*TextView*/
        textBloc1 = (TextView) findViewById(R.id.textView1);
        textBloc2 = (TextView) findViewById(R.id.textView2);
        textBloc3 = (TextView) findViewById(R.id.textView3);

        textBloc1.setText("Go to Maps");
        textBloc2.setText("Go to Events");
        textBloc3.setText("Go to PostEvents");

        textBloc1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsView.class);
                startActivity(intent);
            }
        });

        textBloc2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventsView.class);
                startActivity(intent);
            }
        });

        textBloc3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostEventsView.class);
                startActivity(intent);
            }
        });

        //Check if they are passed events in the Event table
        checkEventsDate();
    }

    /*Moves the passed events from the Event table to the PostEvent table*/
    public void checkEventsDate() {
        EventDAO eventDAO;
        PostEventDAO postEventDAO;
        Cursor eventsCursor;

        eventDAO = new EventDAO(getApplicationContext());
        eventDAO.open();
        postEventDAO = new PostEventDAO(getApplicationContext());
        postEventDAO.open();

        eventsCursor = eventDAO.getPassedEventsId();
        if ((eventsCursor != null) && (eventsCursor.getCount() > 0)) {
            for (eventsCursor.moveToFirst(); !eventsCursor.isAfterLast(); eventsCursor.moveToNext()) {
                eventDAO.deleteEvent(eventsCursor.getInt(eventsCursor.getColumnIndex("_id")));
                PostEvent postevent = new PostEvent(
                        eventsCursor.getInt(eventsCursor.getColumnIndex("_id")),
                        eventsCursor.getString(eventsCursor.getColumnIndex("name")),
                        eventsCursor.getString(eventsCursor.getColumnIndex("date")),
                        null,null);
                postEventDAO.createPostEvent(postevent);
            }
        }

    }

}
