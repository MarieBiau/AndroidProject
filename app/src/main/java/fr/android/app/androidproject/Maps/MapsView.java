package fr.android.app.androidproject.Maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import fr.android.app.androidproject.R;

public class MapsView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Spinner buildingchoice;
    TextView buildingtext;
    Button searchbtn;
    LatLng choicelatlng;
    String choiceview;
    Marker pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (isNetworkAvailable()) {
            mapFragment.getMapAsync(this);
        }else{
            Toast.makeText(this, "Error occured. Check your internet connection", Toast.LENGTH_SHORT).show();
        }

        // text for building:
        buildingtext = (TextView) findViewById(R.id.buildingtext);
        buildingtext.setText("Choose a building :");
        //spinner configuration
        buildingchoice = (Spinner) findViewById(R.id.building);
        List buildingList = new ArrayList();
        for (int i=0;i<28;i++){
            buildingList.add(i);
        }
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                buildingList
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingchoice.setAdapter(adapter);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ruc = new LatLng(55.652622, 12.139827);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ruc));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 15.4f ) );
        // easy way to show position
        // TODO: 30/10/2017 use API from google to get latlng 
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        // search button
        searchbtn = (Button) findViewById(R.id.searchbutton);
        searchbtn.setText("search");
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String choice = buildingchoice.getSelectedItem().toString();

                if (Integer.parseInt(choice) == 1){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.653562, 12.139533);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 2){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652920, 12.140992);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 3){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.653610, 12.140928);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 4){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.653048, 12.137906);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 5){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652726, 12.138222);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 6){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.653047, 12.138613);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 7){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652732, 12.138935);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 8){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.653053, 12.139343);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 9){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652750, 12.139729);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 10){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.653028, 12.140029);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 11){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652743, 12.140447);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 12){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652386, 12.140222);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 13){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.651998, 12.137101);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 14){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.651986, 12.137892);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 15){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652361, 12.137828);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 16){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652461, 12.136587);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 17){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652446, 12.137172);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 18){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652773, 12.137167);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 19){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.653091, 12.137183);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 20){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.653085, 12.136652);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 21){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.653097, 12.136078);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 22){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.653120, 12.135499);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 23){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652787, 12.135489);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 24){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652463, 12.135465);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 25){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652182, 12.135009);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 26){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.651674, 12.136130);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 27){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.651501, 12.138780);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                if (Integer.parseInt(choice) == 28){
                    if (pos != null){
                        pos.remove();
                    }
                    choicelatlng = new LatLng(55.652442, 12.138995);
                    choiceview = "1";
                    pos = mMap.addMarker(new MarkerOptions().position(choicelatlng).title(choiceview));

                }
                // TODO: 10/11/2017 add 9 other building (also in the list) // make a generic function
            }
        });
    }

    //check if network is available to have location
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
