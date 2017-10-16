package fr.android.app.androidproject.Maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fr.android.app.androidproject.Main.MainActivity;
import fr.android.app.androidproject.R;

public class MapsView extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_view);

        backButton = (Button) findViewById(R.id.backbutton);


        backButton.setText("Back");


        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MapsView.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }


}
