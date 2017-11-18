package fr.android.app.androidproject.PostEvents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import fr.android.app.androidproject.Main.MainActivity;
import fr.android.app.androidproject.R;

public class AddNoteOrPicturesActivity extends AppCompatActivity {

    TextView textBlocAddNote;
    TextView textBlocAddPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_or_pictures);

        /*TextView*/
        textBlocAddNote = (TextView) findViewById(R.id.add_note);
        textBlocAddPictures = (TextView) findViewById(R.id.add_pictures);

        textBlocAddNote.setText("Add note");
        textBlocAddPictures.setText("Add pictures");

        textBlocAddNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteOrPicturesActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

        textBlocAddPictures.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteOrPicturesActivity.this, AddPicturesActivity.class);
                startActivity(intent);
            }
        });
    }

}
