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
    TextView textPicture;
    TextView textBlocTitle;
    PostEventDAO postsEventDAO;
    int idPostEvent;
    Cursor myCursor;
    String name;
    String note;
    byte[] image;
    final boolean[] noteChanged = new boolean[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_and_picture);

        /*Get data from PostEventsView */
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idPostEvent = Integer.parseInt(data.getString("idFromPostEvent"));
        }

        /*Back button*/
        backButton = (Button) findViewById(R.id.backbutton);
        backButton.setText(R.string.back_string);
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
        textPicture = (TextView) findViewById(R.id.text_pictures);

        /*Ask database to retrieve the associated event */
        if (!(noteChanged[0])) {    //If the user has not erased the note earlier
            postsEventDAO = new PostEventDAO(getApplicationContext());
            postsEventDAO.open();
            if (Integer.toString(idPostEvent) != null) {
                myCursor = postsEventDAO.getPostEventCursor(idPostEvent);
                startManagingCursor(myCursor);
                if (!myCursor.moveToFirst())
                    myCursor.moveToFirst();
                note = myCursor.getString(myCursor.getColumnIndex(EVENT_NOTE));
                name = myCursor.getString(myCursor.getColumnIndex(EVENT_NAME));
                image = myCursor.getBlob(myCursor.getColumnIndex(EVENT_PICTURES));
            }
        }
        textBlocTitle.setText(R.string.eventcln + name);

        if (note == null){        //If there is no note
            textBlocAddNote.setText(R.string.addnote);

            textBlocAddNote.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(NoteAndPictureActivity.this, AddNoteActivity.class);
                    if (Integer.toString(idPostEvent) != null) {
                        intent.putExtra("postEventId", String.valueOf(idPostEvent));
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
                    if (Integer.toString(idPostEvent) != null) {
                        intent.putExtra("postEventId", String.valueOf(idPostEvent));
                        intent.putExtra("noteValue",note);
                    }
                    startActivity(intent);
                }
            });
            textBlocAddNote.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return deleteConfirmation(idPostEvent);
                }
            });
        }

        /*If there is no picture*/
        if (image == null || BitmapFactory.decodeByteArray(image, 0, image.length) == null){
            textPicture.setText(R.string.addpicture);
            textBlocAddPictures.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(NoteAndPictureActivity.this, AddPictureActivity.class);
                    if (Integer.toString(idPostEvent) != null) {
                        intent.putExtra("postEventId", String.valueOf(idPostEvent));
                    }
                    startActivity(intent);
                }
            });

        } else {    /*If there  is already a picture*/
            textPicture.setText("");
            Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);
            textBlocAddPictures.setImageBitmap(img);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 100, stream);
            textBlocAddPictures.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NoteAndPictureActivity.this, SeeFullPictureActivity.class);
                    if (Integer.toString(idPostEvent) != null) {
                        intent.putExtra("postEventId", String.valueOf(idPostEvent));
                        intent.putExtra("img", image);
                    }
                    startActivity(intent);
                }
            });
            textBlocAddPictures.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteImageConfirmation(idPostEvent);
                    return true;
                }
            });
        }

    }

    /*Delete note confirmation*/
    private boolean deleteConfirmation(final int id)
    {
        AlertDialog mDialogBox = new AlertDialog.Builder(this)
                .setTitle(R.string.delete)
                .setMessage(R.string.deletenote)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        postsEventDAO = new PostEventDAO(getApplicationContext());
                        postsEventDAO.open();
                        postsEventDAO.deletePostEventNote(id);
                        textBlocAddNote.setText(R.string.addnote);
                        noteChanged[0]= true;   //On refreshing the page
                        finish();
                        startActivity(getIntent());
                        getIntent().putExtra("idFromPostEvent", idPostEvent);
                        Toast.makeText(NoteAndPictureActivity.this, R.string.notedelete, Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        noteChanged[0]= false;
                        dialog.dismiss();
                    }
                })
                .create();
        mDialogBox.show();
        return noteChanged[0];
    }

    /*Delete image confirmation*/
    private void deleteImageConfirmation(final int id) {
        AlertDialog mDialogBox = new AlertDialog.Builder(this)
                .setTitle(R.string.delete)
                .setMessage(R.string.deleteimg)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        postsEventDAO = new PostEventDAO(getApplicationContext());
                        postsEventDAO.open();
                        postsEventDAO.deletePostEventImg(id);
                        finish();
                        startActivity(getIntent());
                        getIntent().putExtra("idFromPostEvent", idPostEvent);
                        Toast.makeText(NoteAndPictureActivity.this, R.string.imgdelete, Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        mDialogBox.show();
    }

}