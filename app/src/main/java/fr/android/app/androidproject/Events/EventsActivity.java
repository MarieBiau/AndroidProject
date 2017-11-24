package fr.android.app.androidproject.Events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fr.android.app.androidproject.Maps.MapsView;
import fr.android.app.androidproject.R;

public class EventsActivity extends AppCompatActivity {

    Button okButton;
    Button backButton;
    EditText editTextName;
    EditText editTextDate;
    Spinner buildingChoice;
    Calendar myCalendar;
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_add);

        /*Ok button*/
        okButton = (Button) findViewById(R.id.okbutton);
        okButton.setText(R.string.ok_string);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ( editTextName.getText().toString().trim().length() != 0 && editTextDate.getText().toString().trim().length() != 0) {
                EventDAO eventDAO = new EventDAO(getApplicationContext());
                eventDAO.open();
                    Event a = new Event(editTextName.getText().toString(), editTextDate.getText().toString(), buildingChoice.getSelectedItem().toString());
                    eventDAO.createEvent(a);
                    Intent intent = new Intent(EventsActivity.this, EventsView.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(),R.string.checkfield,Toast.LENGTH_LONG).show();
                }
            }
        });

        /*Back button*/
        backButton = (Button) findViewById(R.id.backbutton);
        backButton.setText(R.string.back_string);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EventsActivity.this, EventsView.class);
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
                editTextDate.setText(sdfDate.format(myCalendar.getTime()));
                timePicker();
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

    /*TimePicker*/
    private void timePicker() {
        time = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                editTextDate.setText(sdfDate.format(myCalendar.getTime()));
            }
        };
        new TimePickerDialog(EventsActivity.this, time,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),false).show();
    }
}



