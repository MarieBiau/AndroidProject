package fr.android.app.androidproject.Events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fr.android.app.androidproject.Main.MainActivity;
import fr.android.app.androidproject.R;

public class EventsActivity extends AppCompatActivity {

    Button okButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_add);

        /*Ok button*/
        okButton = (Button) findViewById(R.id.okbutton);
        okButton.setText("OK");
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EventsActivity.this, EventsActivity.class);
                startActivity(intent);
            }
        });

        /*Back button*/
        backButton = (Button) findViewById(R.id.backbutton);
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EventsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*ViewList with all created events*/
        //EventDAO eventDAO = new EventDAO(getApplicationContext());
        //eventDAO.open();

        //DateFormat shortDateFormatEN = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale("EN","en"));

        //Event a = new Event("a",shortDateFormatEN.format(new Date()),0,0);
        //eventDAO.createEvent(a);
    }
}



