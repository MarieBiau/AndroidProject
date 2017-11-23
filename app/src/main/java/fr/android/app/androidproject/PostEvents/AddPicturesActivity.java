package fr.android.app.androidproject.PostEvents;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import fr.android.app.androidproject.R;

public class AddPicturesActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    ImageView imgview;
    int idpostevent;
    Button okButton;
    Button backButton;
    byte[] byteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pictures);

        /* set variables */
        imgview = (ImageView)findViewById(R.id.imageView);
        okButton = (Button) findViewById(R.id.okbutton);
        backButton = (Button) findViewById(R.id.backbutton);

        /* get data from addnotorpicture*/
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idpostevent = Integer.parseInt(data.getString("posteventid"));
        }

        /* ask new photo*/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);

        /* ok button */
        okButton.setText("OK");
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //if ( editTextNote.getText().toString().trim().length() != 0 ) {
                    PostEventDAO postsEventDAO = new PostEventDAO(getApplicationContext());
                    postsEventDAO.open();
                    if (Integer.toString(idpostevent) != null) {
                        if (byteArray != null) {
                            postsEventDAO.updatePostEventPic(byteArray, idpostevent);
                        }else{
                            Toast.makeText(getBaseContext(),"No picture added - make sure to add one",Toast.LENGTH_LONG).show();
                        }
                    }
                    Intent intent = new Intent(AddPicturesActivity.this, AddNoteOrPicturesActivity.class);
                    if (Integer.toString(idpostevent) != null) {
                        intent.putExtra("idfrompostevent",  String.valueOf(idpostevent));
                    }
                    startActivity(intent);
                /*}else{
                    Toast.makeText(getBaseContext(),"Please check if all fields are completed",Toast.LENGTH_LONG).show();
                }*/
            }
        });




        /* backbutton */
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddPicturesActivity.this, AddNoteOrPicturesActivity.class);
                if (Integer.toString(idpostevent) != null) {
                    intent.putExtra("idfrompostevent",  String.valueOf(idpostevent));
                }
                startActivity(intent);
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Bitmap yourSelectedImage = BitmapFactory.decodeFile(selectedImagePath);

                imgview.setImageBitmap(yourSelectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
}
