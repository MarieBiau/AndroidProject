package fr.android.app.androidproject.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import fr.android.app.androidproject.Events.EventsView;
import fr.android.app.androidproject.Maps.MapsView;
import fr.android.app.androidproject.PostEvents.PostEventsView;
import fr.android.app.androidproject.R;

public class MainActivity extends AppCompatActivity {

    //define variables
    TextView textbloc1;
    TextView textbloc2;
    TextView textbloc3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        //rely textviews to variables
        textbloc1 = (TextView) findViewById(R.id.textView1);
        textbloc2 = (TextView) findViewById(R.id.textView2);
        textbloc3 = (TextView) findViewById(R.id.textView3);

        //add text to textviews
        textbloc1.setText("Go to Maps");
        textbloc2.setText("Go to Events");
        textbloc3.setText("Go to PostEvents");

        /////////////////////////////////
        //click listener for textviews///
        /////////////////////////////////

        //listener textview1
        textbloc1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsView.class);

                startActivity(intent);
            }
        });

        //listener textview2
        textbloc2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventsView.class);

                startActivity(intent);
            }
        });

        //listener textview3
        textbloc3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostEventsView.class);

                startActivity(intent);
            }
        });

    }

}
