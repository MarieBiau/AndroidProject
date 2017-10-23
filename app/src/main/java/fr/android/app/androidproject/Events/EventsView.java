package fr.android.app.androidproject.Events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fr.android.app.androidproject.Main.MainActivity;
import fr.android.app.androidproject.R;

public class EventsView extends AppCompatActivity {

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

    }

}
