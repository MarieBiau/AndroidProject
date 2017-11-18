package fr.android.app.androidproject.PostEvents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import fr.android.app.androidproject.Main.MainActivity;
import fr.android.app.androidproject.R;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_or_pictures);
    }

}
