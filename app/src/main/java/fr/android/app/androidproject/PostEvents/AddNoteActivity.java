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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        /* set variables */
        okButton = (Button) findViewById(R.id.okbutton);
        backButton = (Button) findViewById(R.id.backbutton);
        editTextNote = (EditText) findViewById(R.id.note);


        /*okbutton*/

        okButton.setText("OK");
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ( editTextNote.getText().toString().trim().length() != 0 ) {
                    PostEventDAO postsEventDAO = new PostEventDAO(getApplicationContext());
                    postsEventDAO.open();
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
