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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.android.app.androidproject.R;

public class MapsView extends FragmentActivity implements OnMapReadyCallback {
//// TODO: 22/11/2017 maybe change design to align button search with spinner / add a textview saying what is shown on the map for clear frontend / button back to mainview
    private GoogleMap mMap;
    Spinner buildingchoice;
    TextView buildingtext;
    Button searchbtn;
    LatLng choicelatlng;
    String choiceview;
    String choice;
    Marker pos;
    String buildingfromevent;
    int indexeur = 0;
    LatLng ruc = new LatLng(55.652622, 12.139827);

    public static List<String> buildingList = Arrays.asList("ruc","0","1","2","3","4","5","6","7","8","9","10",
            "11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","36","37","38","40","41","42","43","44","45","46","korallen","kolibrien","rockwool");
    public static ArrayList<LatLng> positionList = new ArrayList<LatLng>() {{
        add(new LatLng(55.652622, 12.139827));// ruc default centered map
        add(new LatLng(55.653657, 12.138663)); // 0 to 28
        add(new LatLng(55.653562, 12.139533));
        add(new LatLng(55.652920, 12.140992));
        add(new LatLng(55.653610, 12.140928));
        add(new LatLng(55.653048, 12.137906));
        add(new LatLng(55.652726, 12.138222));
        add(new LatLng(55.653047, 12.138613));
        add(new LatLng(55.652732, 12.138935));
        add(new LatLng(55.653053, 12.139343));
        add(new LatLng(55.652750, 12.139729));
        add(new LatLng(55.653028, 12.140029));
        add(new LatLng(55.652743, 12.140447));
        add(new LatLng(55.652386, 12.140222));
        add(new LatLng(55.651998, 12.137101));
        add(new LatLng(55.651986, 12.137892));
        add(new LatLng(55.652361, 12.137828));
        add(new LatLng(55.652461, 12.136587));
        add(new LatLng(55.652446, 12.137172));
        add(new LatLng(55.652773, 12.137167));
        add(new LatLng(55.653091, 12.137183));
        add(new LatLng(55.653085, 12.136652));
        add(new LatLng(55.653097, 12.136078));
        add(new LatLng(55.653120, 12.135499));
        add(new LatLng(55.652787, 12.135489));
        add(new LatLng(55.652463, 12.135465));
        add(new LatLng(55.652182, 12.135009));
        add(new LatLng(55.651674, 12.136130));
        add(new LatLng(55.651501, 12.138780));
        add(new LatLng(55.652442, 12.138995));
        add(new LatLng(55.652441, 12.141396));
        add(new LatLng(55.652399, 12.142008));
        add(new LatLng(55.653005, 12.142091));
        add(new LatLng(55.6543721, 12.1386321));
        add(new LatLng(55.6548365, 12.1386762));
        add(new LatLng(55.6549765, 12.1389745));
        add(new LatLng(55.6549700, 12.1393248));
        add(new LatLng(55.654615, 12.139909));
        add(new LatLng(55.654594, 12.140632));
        add(new LatLng(55.654594, 12.141281));
        add(new LatLng(55.651404, 12.143217));
        add(new LatLng(55.650867, 12.135655));
        add(new LatLng(55.650197, 12.133114));



    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_view);
        /* we get data from events view if user click button view on map */
        Bundle data = getIntent().getExtras();
        if (data!=null) {
            buildingfromevent = data.getString("buildingFromEvent");
        }

        /*Obtain the SupportMapFragment and get notified when the map is ready to be used*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (isNetworkAvailable()) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Error occured. Check your internet connection", Toast.LENGTH_SHORT).show();
        }

        /*Building text and spinner*/
        buildingtext = (TextView) findViewById(R.id.buildingtext);
        buildingtext.setText("Choose a building :");
        buildingchoice = (Spinner) findViewById(R.id.building);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
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

        float zoom = 15.4f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ruc,zoom);
        mMap.animateCamera(cameraUpdate);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        /* handle if received a location from events view */
        if (buildingfromevent != null){
            choice = buildingfromevent;
            findBuildingByChoice(choice);
        }
        /*Search button*/
        searchbtn = (Button) findViewById(R.id.searchbutton);
        searchbtn.setText("search");
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                choice = buildingchoice.getSelectedItem().toString();
                findBuildingByChoice(choice);
            }
        });

    }

    /*Check if network is available to get location*/
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void newMarker(LatLng markerChoicelatlng, String markerchoiceview, GoogleMap nMap ){
        LatLng latlng = markerChoicelatlng;
        String view = markerchoiceview;
        GoogleMap myMap = nMap;

        if (pos != null){
            pos.remove();
        }
        pos = myMap.addMarker(new MarkerOptions().position(latlng).title(view));
        /* remove camera to center in case of */
        float zoom = 15.4f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ruc,zoom);
        mMap.animateCamera(cameraUpdate);


    }

    public void findBuildingByChoice(String choice){
        String mychoice = choice;
        int i = 0; // for transition position list

        for (final String building: buildingList ){

            if (mychoice.matches("^-?\\d+$") && building.matches("^-?\\d+$")){ // if choice is building number
                if (Integer.parseInt(mychoice) == Integer.parseInt(building)){
                    newMarker(positionList.get(i), mychoice ,mMap);
                }
            }else {// if choice is a building name
                if (building.equals(mychoice)){
                    if (mychoice.equals("ruc")){ // for initial position we dont add marker and just recenter map
                        if (pos != null){ // and we remove previous position if exist to prevent misunderstanding in frontend
                            pos.remove();
                        }
                        float zoom = 15.4f;
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ruc,zoom);
                        mMap.animateCamera(cameraUpdate);
                    }else if (mychoice.equals("rockwool")){
                        newMarker(positionList.get(i), mychoice, mMap); // we move a bit the camera because building are out of zoom
                        float zoom = 14.9f;
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ruc,zoom);
                        mMap.animateCamera(cameraUpdate);
                    }else{
                        newMarker(positionList.get(i), mychoice, mMap);
                        float zoom = 15.2f;
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ruc,zoom);
                        mMap.animateCamera(cameraUpdate);
                    }

                }
            }
            i++; // add to transition position list
        }
    }

}
