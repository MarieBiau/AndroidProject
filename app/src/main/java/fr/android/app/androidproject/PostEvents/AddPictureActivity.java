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

public class AddPictureActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    Button okButton;
    Button backButton;
    int idPostEvent;
    byte[] byteArray;
    private String selectedImagePath;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);

        /*Get data from NoteAndPictureActivity*/
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            idPostEvent = Integer.parseInt(data.getString("postEventId"));
        }

        /*Ask for a new photo*/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);

        /*Ok button*/
        okButton = (Button) findViewById(R.id.okbutton);
        okButton.setText("OK");
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    PostEventDAO postsEventDAO = new PostEventDAO(getApplicationContext());
                    postsEventDAO.open();
                    if (Integer.toString(idPostEvent) != null) {
                        if (byteArray != null) {
                            postsEventDAO.updatePostEventPic(byteArray, idPostEvent);
                        }else{
                            Toast.makeText(getBaseContext(),"No picture added - make sure to add one",Toast.LENGTH_LONG).show();
                        }
                    }
                    Intent intent = new Intent(AddPictureActivity.this, NoteAndPictureActivity.class);
                    if (Integer.toString(idPostEvent) != null) {
                        intent.putExtra("idFromPostEvent",  String.valueOf(idPostEvent));
                    }
                    startActivity(intent);
            }
        });

        /*Back button*/
        backButton = (Button) findViewById(R.id.backbutton);
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddPictureActivity.this, NoteAndPictureActivity.class);
                if (Integer.toString(idPostEvent) != null) {
                    intent.putExtra("idFromPostEvent",  String.valueOf(idPostEvent));
                }
                startActivity(intent);
            }
        });

        /*ImageView*/
        imgView = (ImageView)findViewById(R.id.imageView);
    }

    /*Get image and insert it in the ImageView*/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Bitmap yourSelectedImage = BitmapFactory.decodeFile(selectedImagePath);
                imgView.setImageBitmap(yourSelectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
            }
        }
    }

    /*Get the path of the image*/
    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        //Try to retrieve the image from the media store, only works for images selected from gallery
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
        return uri.getPath();
    }

}
