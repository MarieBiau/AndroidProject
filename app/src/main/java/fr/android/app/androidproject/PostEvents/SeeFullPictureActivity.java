package fr.android.app.androidproject.PostEvents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import fr.android.app.androidproject.R;

public class SeeFullPictureActivity extends AppCompatActivity {

    int idpostevent;
    byte[] image;
    ImageView imgview;
    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_full_picture);

        /*Get data from post events view */
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idpostevent = Integer.parseInt(data.getString("postEventId"));
            image = (byte[]) data.get("img");
        }

        imgview = (ImageView) findViewById(R.id.imageView);
        backButton = (Button) findViewById(R.id.backbutton);

        Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);
        imgview.setImageBitmap(img);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);

         /* back button */
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SeeFullPictureActivity.this, NoteAndPictureActivity.class);
                if (Integer.toString(idpostevent) != null) {
                    intent.putExtra("idFromPostEvent",  String.valueOf(idpostevent));
                }
                startActivity(intent);
            }
        });

    }
}
