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
import java.util.Arrays;
import java.util.List;

import fr.android.app.androidproject.R;

public class MapsView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Spinner buildingchoice;
    TextView buildingtext;
    Button searchbtn;
    LatLng choicelatlng;
    String choiceview;
    String choice;
    Marker pos;
    String buildingfromevent;
    public static List<String> buildingList = Arrays.asList("ruc","1","2","3","4","5","6","7","8","9","10",
            "11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28");
    public static ArrayList<LatLng> positionList = new ArrayList<LatLng>() {{
        add(new LatLng(55.652622, 12.139827));
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
        LatLng ruc = new LatLng(55.652622, 12.139827);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ruc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.4f));
        // TODO: 30/10/2017 use API from google to get latlng 
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
                // TODO: 10/11/2017 add 9 other building in the list/ code should adapt to new entries automatically
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
        myMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(55.652622, 12.139827)));
        myMap.animateCamera(CameraUpdateFactory.zoomTo(15.4f));

    }

    public void findBuildingByChoice(String choice){
        String mychoice = choice;
        for (int i = 0; i < buildingList.size();i++){
            if (mychoice.matches("^-?\\d+$")){ // if choice is building number
                if (Integer.parseInt(mychoice)-1 == i){
                    newMarker(positionList.get(i), String.valueOf(i+1),mMap);
                 }
            }else {// if choice is a building name
                //// TODO: 21/11/2017 do the list with building name and finish this part
                if (mychoice == "aBuildingName"){
                    newMarker(positionList.get(i), "buildingName ",mMap);
                }
            }
        }

    }

}
