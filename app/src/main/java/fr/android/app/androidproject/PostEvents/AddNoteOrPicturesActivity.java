package fr.android.app.androidproject.PostEvents;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import fr.android.app.androidproject.R;

import static fr.android.app.androidproject.DatabaseHandler.EVENT_NAME;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_NOTE;

public class AddNoteOrPicturesActivity extends AppCompatActivity {

    TextView textBlocAddNote;
    TextView textBlocAddPictures;
    int idpostevent;
    Cursor myCursor;
    String note;

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

        /* ask database to retreive the associated event */
        PostEventDAO postsEventDAO = new PostEventDAO(getApplicationContext());
        postsEventDAO.open();

        if (Integer.toString(idpostevent) != null) {

            myCursor = postsEventDAO.getPostEventCursor(idpostevent);
            startManagingCursor(myCursor);
            Log.d("testazazazazazazaza", String.valueOf(myCursor.getColumnIndex(EVENT_NAME)));
            Log.d("testazazazazazazaza", String.valueOf(myCursor.getColumnIndex(EVENT_NOTE)));
            note = myCursor.getString(2);
        }

        if (note.equals(null)){
            textBlocAddNote.setText("Add note");
        }else{
            textBlocAddNote.setText(note);
        }
        //todo if nothing in database

        textBlocAddPictures.setText("Add pictures");

        textBlocAddNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteOrPicturesActivity.this, AddNoteActivity.class);
                if (Integer.toString(idpostevent) != null) {
                    intent.putExtra("posteventid", String.valueOf(idpostevent));
                }
                startActivity(intent);
            }
        });

        textBlocAddPictures.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteOrPicturesActivity.this, AddPicturesActivity.class);
                if (Integer.toString(idpostevent) != null) {
                    intent.putExtra("posteventid", String.valueOf(idpostevent));
                }
                startActivity(intent);
            }
        });
    }

}
