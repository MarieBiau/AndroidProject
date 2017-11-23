package fr.android.app.androidproject.PostEvents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import fr.android.app.androidproject.Events.EventDAO;
import fr.android.app.androidproject.R;

import static fr.android.app.androidproject.DatabaseHandler.EVENT_NAME;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_NOTE;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_PICTURES;

public class AddNoteOrPicturesActivity extends AppCompatActivity {

    TextView textBlocAddNote;
    ImageView textBlocAddPictures;
    TextView textBlocTitle;
    int idpostevent;
    Cursor myCursor;
    String note;
    String name;
    PostEventDAO postsEventDAO;
    final boolean[] notechanged = new boolean[1];
    EventDAO eventDAO;
    Button backButton;
    byte[] imgptr;

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
        textBlocAddPictures = (ImageView) findViewById(R.id.add_pictures);
        backButton = (Button) findViewById(R.id.backbutton);
        textBlocTitle = (TextView)  findViewById(R.id.title);

        /* back button */
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteOrPicturesActivity.this, PostEventsView.class);
                startActivity(intent);
            }
        });

        Log.d("WAIT WHAT111", String.valueOf(notechanged[0]));
        /* ask database to retreive the associated event */
        if (!(notechanged[0])) { // we dont do that if the user have erased the note earlier
            postsEventDAO = new PostEventDAO(getApplicationContext());
            postsEventDAO.open();
            if (Integer.toString(idpostevent) != null) {
                myCursor = postsEventDAO.getPostEventCursor(idpostevent);
                startManagingCursor(myCursor);
                if (!myCursor.moveToFirst())
                    myCursor.moveToFirst();
                note = myCursor.getString(myCursor.getColumnIndex(EVENT_NOTE));
                name = myCursor.getString(myCursor.getColumnIndex(EVENT_NAME));
                imgptr = myCursor.getBlob(myCursor.getColumnIndex(EVENT_PICTURES));
            }
        }
        /* set title of event */
        textBlocTitle.setText("Event : " + name);

        // if no note
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
        //if no image

        if (imgptr == null || BitmapFactory.decodeByteArray(imgptr, 0, imgptr.length) == null){
            // add "add picture " text to the imageview
            //// TODO: 23/11/2017  

            // clicklistener
            textBlocAddPictures.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(AddNoteOrPicturesActivity.this, AddPicturesActivity.class);
                    if (Integer.toString(idpostevent) != null) {
                        intent.putExtra("posteventid", String.valueOf(idpostevent));
                    }
                    startActivity(intent);
                }
            });
            /*textBlocAddNote.setText("Add note");

            textBlocAddNote.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(AddNoteOrPicturesActivity.this, AddNoteActivity.class);
                    if (Integer.toString(idpostevent) != null) {
                        intent.putExtra("posteventid", String.valueOf(idpostevent));
                    }
                    startActivity(intent);

                }
            });*/

        }else{
            Bitmap img = BitmapFactory.decodeByteArray(imgptr, 0, imgptr.length);
            textBlocAddPictures.setImageBitmap(img);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 100, stream);
            textBlocAddPictures.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //// TODO: 23/11/2017 new activity to show image full
                    Intent intent = new Intent(AddNoteOrPicturesActivity.this, SeeFullPicture.class);
                    if (Integer.toString(idpostevent) != null) {
                        intent.putExtra("posteventid", String.valueOf(idpostevent));
                        intent.putExtra("img",imgptr);
                    }
                    startActivity(intent);
                }
            });
            textBlocAddPictures.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteimgConfirmation(idpostevent);
                    return true;
                }
            });
        }
        //}


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
    private void deleteimgConfirmation(final int id)
    {
        AlertDialog mDialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to delete the image ? ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        postsEventDAO = new PostEventDAO(getApplicationContext());
                        postsEventDAO.open();
                        postsEventDAO.deletePostEventImg(id);
                        finish();
                        startActivity(getIntent());
                        getIntent().putExtra("idfrompostevent", idpostevent);
                        Toast.makeText(AddNoteOrPicturesActivity.this, "Image Deleted", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        mDialogBox.show();
    }
}
