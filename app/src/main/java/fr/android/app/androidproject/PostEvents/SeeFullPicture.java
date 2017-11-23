package fr.android.app.androidproject.PostEvents;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import fr.android.app.androidproject.R;


public class SeeFullPicture extends AppCompatActivity {

    int idpostevent;
    byte[] image;
    ImageView imgview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_full_picture);


        /* get data from post events view */
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idpostevent = Integer.parseInt(data.getString("posteventid"));
            image = (byte[]) data.get("img");
        }

        imgview = (ImageView) findViewById(R.id.imageView);

        Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);
        imgview.setImageBitmap(img);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);

    }
}
