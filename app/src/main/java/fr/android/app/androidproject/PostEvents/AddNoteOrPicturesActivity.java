package fr.android.app.androidproject.PostEvents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import fr.android.app.androidproject.R;

public class AddNoteOrPicturesActivity extends AppCompatActivity {

    TextView textBlocAddNote;
    TextView textBlocAddPictures;
    int idpostevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_or_pictures);

        /* get data from post events view */
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idpostevent = Integer.parseInt(data.getString("idfrompostevent"));
        }
        /*TextView*/
        textBlocAddNote = (TextView) findViewById(R.id.add_note);
        textBlocAddPictures = (TextView) findViewById(R.id.add_pictures);

        //todo if nothing in database
        textBlocAddNote.setText("Add note");
        textBlocAddPictures.setText("Add pictures");

        textBlocAddNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteOrPicturesActivity.this, AddNoteActivity.class);
                if (Integer.toString(idpostevent) != null) {
                    intent.putExtra("posteventid", idpostevent);
                }
                startActivity(intent);
            }
        });

        textBlocAddPictures.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteOrPicturesActivity.this, AddPicturesActivity.class);
                Log.d("ahahahha", Integer.toString(idpostevent));
                if (Integer.toString(idpostevent) != null) {
                    intent.putExtra("posteventid", Integer.toString(idpostevent));
                }
                startActivity(intent);
            }
        });
    }

}
