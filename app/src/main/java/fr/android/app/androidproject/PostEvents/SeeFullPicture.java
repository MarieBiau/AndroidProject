package fr.android.app.androidproject.PostEvents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import fr.android.app.androidproject.R;


public class SeeFullPicture extends AppCompatActivity {

    int idpostevent;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_full_picture);


        /* get data from post events view */
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idpostevent = Integer.parseInt(data.getString("posteventid"));
            image = data.getString("img");
            Log.d("did receive id :", String.valueOf(image));
        }

    }
}
