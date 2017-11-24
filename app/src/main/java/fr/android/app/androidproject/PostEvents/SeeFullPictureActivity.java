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

    Button backButton;
    ImageView imgView;
    int idPostEvent;
    byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_full_picture);

        /*Get data from PostEventsView */
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idPostEvent = Integer.parseInt(data.getString("postEventId"));
            image = (byte[]) data.get("img");
        }

        /*ImageView*/
        imgView = (ImageView) findViewById(R.id.imageView);
        Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);
        imgView.setImageBitmap(img);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);

        /*Back button*/
        backButton = (Button) findViewById(R.id.backbutton);
        backButton.setText(R.string.back_string);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SeeFullPictureActivity.this, NoteAndPictureActivity.class);
                if (Integer.toString(idPostEvent) != null) {
                    intent.putExtra("idFromPostEvent",  String.valueOf(idPostEvent));
                }
                startActivity(intent);
            }
        });

    }
}
