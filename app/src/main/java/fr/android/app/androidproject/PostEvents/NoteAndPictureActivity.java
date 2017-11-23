package fr.android.app.androidproject.PostEvents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import fr.android.app.androidproject.R;

import static fr.android.app.androidproject.DatabaseHandler.EVENT_NAME;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_NOTE;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_PICTURES;

public class NoteAndPictureActivity extends AppCompatActivity {

    Button backButton;
    TextView textBlocAddNote;
    ImageView textBlocAddPictures;
    TextView textBlocTitle;
    PostEventDAO postsEventDAO;
    int idpostevent;
    Cursor myCursor;
    String name;
    String note;
    byte[] imgPtr;
    final boolean[] noteChanged = new boolean[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_and_picture);

        /*Get data from PostEventsView */
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idpostevent = Integer.parseInt(data.getString("idFromPostEvent"));
        }

        /*Back button */
        backButton = (Button) findViewById(R.id.backbutton);
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NoteAndPictureActivity.this, PostEventsView.class);
                startActivity(intent);
            }
        });

        /*TextView*/
        textBlocTitle = (TextView)  findViewById(R.id.title);
        textBlocAddNote = (TextView) findViewById(R.id.add_note);
        textBlocAddPictures = (ImageView) findViewById(R.id.add_pictures);

        /*Ask database to retrieve the associated event */
        if (!(noteChanged[0])) {    //If the user has not erased the note earlier
            postsEventDAO = new PostEventDAO(getApplicationContext());
            postsEventDAO.open();
            if (Integer.toString(idpostevent) != null) {
                myCursor = postsEventDAO.getPostEventCursor(idpostevent);
                startManagingCursor(myCursor);
                if (!myCursor.moveToFirst())
                    myCursor.moveToFirst();
                note = myCursor.getString(myCursor.getColumnIndex(EVENT_NOTE));
                name = myCursor.getString(myCursor.getColumnIndex(EVENT_NAME));
                imgPtr = myCursor.getBlob(myCursor.getColumnIndex(EVENT_PICTURES));
            }
        }
        textBlocTitle.setText("Event : " + name);
        if (note == null){        //If there is no note
            textBlocAddNote.setText("Add note");

            textBlocAddNote.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(NoteAndPictureActivity.this, AddNoteActivity.class);
                    if (Integer.toString(idpostevent) != null) {
                        intent.putExtra("posteventid", String.valueOf(idpostevent));
                    }
                    startActivity(intent);

                }
            });
        } else {    //If there is already a note
            textBlocAddNote.setText(note);
            textBlocAddNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NoteAndPictureActivity.this, AddNoteActivity.class);
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

        if (imgPtr == null || BitmapFactory.decodeByteArray(imgPtr, 0, imgPtr.length) == null){
            textBlocAddPictures.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(NoteAndPictureActivity.this, AddPictureActivity.class);
                    if (Integer.toString(idpostevent) != null) {
                        intent.putExtra("posteventid", String.valueOf(idpostevent));
                    }
                    startActivity(intent);
                }
            });

        } else {
            Bitmap img = BitmapFactory.decodeByteArray(imgPtr, 0, imgPtr.length);
            textBlocAddPictures.setImageBitmap(img);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 100, stream);
            textBlocAddPictures.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NoteAndPictureActivity.this, SeeFullPicture.class);
                    if (Integer.toString(idpostevent) != null) {
                        intent.putExtra("posteventid", String.valueOf(idpostevent));
                        intent.putExtra("img", imgPtr);
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
                        noteChanged[0]= true; // for refreshing page
                        finish();
                        startActivity(getIntent());
                        getIntent().putExtra("idFromPostEvent", idpostevent);
                        Toast.makeText(NoteAndPictureActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        noteChanged[0]= false;
                        dialog.dismiss();
                    }
                })
                .create();
        mDialogBox.show();
        return noteChanged[0];
    }

    private void deleteimgConfirmation(final int id) {
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
                        getIntent().putExtra("idFromPostEvent", idpostevent);
                        Toast.makeText(NoteAndPictureActivity.this, "Image Deleted", Toast.LENGTH_SHORT).show();

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