package fr.android.app.androidproject.Events;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.android.app.androidproject.Main.MainActivity;
import fr.android.app.androidproject.Maps.MapsView;
import fr.android.app.androidproject.R;

public class EventsActivity extends AppCompatActivity {

    Button okButton;
    Button backButton;
    EditText editTextName;
    EditText editTextDate;
    Spinner buildingChoice;
    Calendar myCalendar;
    DateFormat shortDateFormatEN = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale("EN","en"));
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_add);

        /*Ok button*/
        okButton = (Button) findViewById(R.id.okbutton);
        okButton.setText("OK");
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EventDAO eventDAO = new EventDAO(getApplicationContext());
                eventDAO.open();
                //Ne fonctionne pas
                Event a = new Event(editTextName.getText().toString(),shortDateFormatEN.format(editTextDate.getText()),0,0);
                eventDAO.createEvent(a);
                Intent intent = new Intent(EventsActivity.this, EventsView.class);
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

        /*Name editText*/
        editTextName = (EditText) findViewById(R.id.name);

        /*DatePicker*/
        myCalendar = Calendar.getInstance();
        editTextDate = (EditText) findViewById(R.id.date);
        date = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editTextDate.setText(shortDateFormatEN.format(myCalendar.getTime()));
            }
        };
        editTextDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(EventsActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*Location spinner*/
        buildingChoice = (Spinner) findViewById(R.id.buildingName);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                MapsView.buildingList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingChoice.setAdapter(adapter);

    }
}



