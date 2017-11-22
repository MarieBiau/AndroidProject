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
    int idpostevent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        /* get data from addnotorpicture*/
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idpostevent = Integer.parseInt(data.getString("posteventid"));
        }

        /* set variables */
        okButton = (Button) findViewById(R.id.okbutton);
        backButton = (Button) findViewById(R.id.backbutton);
        editTextNote = (EditText) findViewById(R.id.note);


        /*okbutton*/
        //// TODO: 22/11/2017 FOR NOW 
        okButton.setText("OK");
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ( editTextNote.getText().toString().trim().length() != 0 ) {
                    PostEventDAO postsEventDAO = new PostEventDAO(getApplicationContext());
                    postsEventDAO.open();
                    if (Integer.toString(idpostevent) != null) {
                        postsEventDAO.updatePostEvent(editTextNote.getText().toString(), idpostevent);
                    }
                    Intent intent = new Intent(AddNoteActivity.this, AddNoteOrPicturesActivity.class);
                    if (Integer.toString(idpostevent) != null) {
                        intent.putExtra("idfrompostevent",  String.valueOf(idpostevent));
                    }
                    startActivity(intent);
                    /*Event a = new Event(editTextNote.getText().toString(), editTextDate.getText().toString(), buildingChoice.getSelectedItem().toString());
                    postsEventDAO.createEvent(a);
                    Intent intent = new Intent(EventsActivity.this, EventsView.class);
                    startActivity(intent);*/
                }else{
                    Toast.makeText(getBaseContext(),"Please check if all fields are completed",Toast.LENGTH_LONG).show();
                }
            }
        });

        /* backbutton */
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteActivity.this, AddNoteOrPicturesActivity.class);
                startActivity(intent);
            }
        });



    }

}
