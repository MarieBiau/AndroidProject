package fr.android.app.androidproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainView extends AppCompatActivity {

    //define variables
    TextView textbloc1;
    TextView textbloc2;
    TextView textbloc3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // rely txtview to variables
        textbloc1 = (TextView) findViewById(R.id.textView);
        textbloc2 = (TextView) findViewById(R.id.textView2);
        textbloc3 = (TextView) findViewById(R.id.textView3);

        //add text to txtview
        textbloc1.setText("go to part A");
        textbloc1.setText("go to part B");
        textbloc1.setText("go to part C");

        /////////////////////////////////
        //click listener for textviews///
        /////////////////////////////////
        //listener txtview1
        textbloc1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(MainView.this, .class);

                //startActivity(intent);
            }
        });


    }
}
