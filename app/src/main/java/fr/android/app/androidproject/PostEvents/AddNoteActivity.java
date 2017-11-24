package fr.android.app.androidproject.PostEvents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.android.app.androidproject.R;

public class AddNoteActivity extends AppCompatActivity {

    Button okButton;
    Button backButton;
    EditText editTextNote;
    int idPostEvent;
    String noteValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        /*Get data from NoteAndPictureActivity*/
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idPostEvent = Integer.parseInt(data.getString("postEventId"));
            noteValue = data.getString("noteValue");
        }

        /*Ok button*/
        okButton = (Button) findViewById(R.id.okbutton);
        okButton.setText("OK");
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ( editTextNote.getText().toString().trim().length() != 0 ) {
                    PostEventDAO postsEventDAO = new PostEventDAO(getApplicationContext());
                    postsEventDAO.open();
                    if (Integer.toString(idPostEvent) != null) {
                        postsEventDAO.updatePostEvent(editTextNote.getText().toString(), idPostEvent);
                    }
                    Intent intent = new Intent(AddNoteActivity.this, NoteAndPictureActivity.class);
                    if (Integer.toString(idPostEvent) != null) {
                        intent.putExtra("idFromPostEvent",  String.valueOf(idPostEvent));
                    }
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(),"Please check if all fields are completed",Toast.LENGTH_LONG).show();
                }
            }
        });

        /*Back button*/
        backButton = (Button) findViewById(R.id.backbutton);
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteActivity.this, NoteAndPictureActivity.class);
                if (Integer.toString(idPostEvent) != null) {
                    intent.putExtra("idFromPostEvent",  String.valueOf(idPostEvent));
                }
                startActivity(intent);
            }
        });

        /*Note editText*/
        editTextNote = (EditText) findViewById(R.id.note);
        if (noteValue != null){     //If there is already a note we show the note for modification
            editTextNote.setText(noteValue);
        }

    }

}
