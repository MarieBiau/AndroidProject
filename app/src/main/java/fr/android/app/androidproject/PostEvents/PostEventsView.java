package fr.android.app.androidproject.PostEvents;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import fr.android.app.androidproject.Main.MainActivity;
import fr.android.app.androidproject.R;

import static fr.android.app.androidproject.DatabaseHandler.EVENT_DATE;
import static fr.android.app.androidproject.DatabaseHandler.EVENT_NAME;

public class PostEventsView extends ListActivity {

    Button backButton;
    Button noteAndPictureButton;
    ListView mListView;
    PostEventDAO postEventDAO;
    Cursor postEventsCursor;
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postevents_view);

        /*Back button*/
        backButton = (Button) findViewById(R.id.backbutton);
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PostEventsView.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*Note & picture button*/
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_postevents_view_list, null);
        noteAndPictureButton = (Button) rowView.findViewById(R.id.note_and_picture_button);
        noteAndPictureButton.setText("Note & picture");
        noteAndPictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PostEventsView.this, NoteAndPictureActivity.class);
                startActivity(intent);
            }
        });

        /*ViewList with all created postevents*/
        postEventDAO = new PostEventDAO(getApplicationContext());
        postEventDAO.open();
        postEventsCursor = postEventDAO.getAllPostEventsCursor();
        startManagingCursor(postEventsCursor);
        mAdapter = new MyCursorAdapter
                (this, R.layout.activity_postevents_view_list, postEventsCursor,
                        new String[]{"_id", EVENT_NAME, EVENT_DATE},
                        new int[]{0, R.id.name, R.id.date}, 0);
        this.setListAdapter(mAdapter);

        /*Delete postevents*/
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                postEventsCursor.moveToPosition(position);
                String name = postEventsCursor.getString(postEventsCursor.getColumnIndex(EVENT_NAME));
                deleteConfirmation((int)id, name);
                return true;
            }
        });
    }

    /*Delete events confirmation*/
    private void deleteConfirmation(final int id, String name)
    {
        AlertDialog mDialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to Delete the event " + name + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        postEventDAO.deletePostEvent(id);
                        postEventsCursor.requery();
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(PostEventsView.this, "Event Deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        mDialogBox.show();
    }

    private class MyCursorAdapter extends SimpleCursorAdapter {

        MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int idCln = cursor.getColumnIndex("_id");
            int nameCln = cursor.getColumnIndex(EVENT_NAME);
            int dateCln = cursor.getColumnIndex(EVENT_DATE);

            TextView tv1 = (TextView) view.findViewById(R.id.name);
            TextView tv2 = (TextView) view.findViewById(R.id.date);
            Button btn = (Button) view.findViewById(R.id.note_and_picture_button);

            final int s3 = cursor.getInt(idCln);
            String s1 = cursor.getString(nameCln);
            String s2 = cursor.getString(dateCln);

            tv1.setText(s1);
            tv2.setText(s2);

            btn.setClickable(true);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PostEventsView.this, NoteAndPictureActivity.class);
                    Log.d("test1", String.valueOf(s3));
                    intent.putExtra("idFromPostEvent",String.valueOf(s3));
                    startActivity(intent);
                }
            });
        }
    }

}
