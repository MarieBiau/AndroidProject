package fr.android.app.androidproject.PostEvents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.android.app.androidproject.Events.EventDAO;
import fr.android.app.androidproject.R;

import static fr.android.app.androidproject.DatabaseHandler.EVENT_NOTE;

public class AddNoteOrPicturesActivity extends AppCompatActivity {

    TextView textBlocAddNote;
    TextView textBlocAddPictures;
    int idpostevent;
    Cursor myCursor;
    String note;
    PostEventDAO postsEventDAO;
    final boolean[] notechanged = new boolean[1];
    EventDAO eventDAO;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_or_pictures);

        /* get data from post events view */
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idpostevent = Integer.parseInt(data.getString("idfrompostevent"));
            Log.d("did receive id :", String.valueOf(idpostevent));
        }
        /*TextView*/
        textBlocAddNote = (TextView) findViewById(R.id.add_note);
        textBlocAddPictures = (TextView) findViewById(R.id.add_pictures);
        backButton = (Button) findViewById(R.id.backbutton);

        /* back button */
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteOrPicturesActivity.this, PostEventsView.class);
                startActivity(intent);
            }
        });

        /* ask database to retreive the associated event */
        if (!(notechanged[0])) { // we dont do that if the user have erased the note earlier
            postsEventDAO = new PostEventDAO(getApplicationContext());
            postsEventDAO.open();
            if (Integer.toString(idpostevent) != null) {
                myCursor = postsEventDAO.getPostEventCursor(idpostevent);
                Log.d("number of return", String.valueOf(myCursor.getColumnCount()));
                startManagingCursor(myCursor);
                if (!myCursor.moveToFirst())
                    myCursor.moveToFirst();
                note = myCursor.getString(myCursor.getColumnIndex(EVENT_NOTE));
            }
        }

        if (note == null){
            textBlocAddNote.setText("Add note");

            textBlocAddNote.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(AddNoteOrPicturesActivity.this, AddNoteActivity.class);
                    if (Integer.toString(idpostevent) != null) {
                        intent.putExtra("posteventid", String.valueOf(idpostevent));
                    }
                    startActivity(intent);

                }
            });

        }else{
            textBlocAddNote.setText(note);
            textBlocAddNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddNoteOrPicturesActivity.this, AddNoteActivity.class);
                    if (Integer.toString(idpostevent) != null) {
                        intent.putExtra("posteventid", String.valueOf(idpostevent));
                        intent.putExtra("notevalue",note);
                    }
                    startActivity(intent);
                }
            });
            textBlocAddNote.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    boolean test = deleteConfirmation(idpostevent);
                    return true;
                }
            });
        }
        //todo picture

        textBlocAddPictures.setText("Add pictures");



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

    /*Delete notes confirmation*/
    private boolean deleteConfirmation(final int id)
    {
        AlertDialog mDialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to delete the note ? ( short click to modify it )")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        postsEventDAO = new PostEventDAO(getApplicationContext());
                        postsEventDAO.open();
                        postsEventDAO.deletePostEventNote(id);
                        textBlocAddNote.setText("Add note");
                        notechanged[0]= true; // for refreshing page
                        finish();
                        startActivity(getIntent());
                        getIntent().putExtra("idfrompostevent", idpostevent);
                        Toast.makeText(AddNoteOrPicturesActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        notechanged[0]= false;
                        dialog.dismiss();
                    }
                })
                .create();
      mDialogBox.show();
    return notechanged[0];
    }
}
