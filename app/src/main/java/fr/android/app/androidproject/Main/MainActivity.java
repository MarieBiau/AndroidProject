package fr.android.app.androidproject.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import fr.android.app.androidproject.Events.EventsView;
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
        textbloc1 = (TextView) findViewById(R.id.textView);
        textbloc2 = (TextView) findViewById(R.id.textView2);
        textbloc3 = (TextView) findViewById(R.id.textView3);

        //add text to txtview
        textbloc1.setText("go to part A (Maps)");
        textbloc2.setText("go to part B (Events");
        textbloc3.setText("go to part C (PostEvents)");

        /////////////////////////////////
        //click listener for textviews///
        /////////////////////////////////

        //listener txtview1
        textbloc1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsView.class);

                startActivity(intent);
            }
        });

        //listener txtview2
        textbloc2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventsView.class);

                startActivity(intent);
            }
        });

        //listener txtview3
        textbloc3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostEventsView.class);

                startActivity(intent);
            }
        });


    }
}
