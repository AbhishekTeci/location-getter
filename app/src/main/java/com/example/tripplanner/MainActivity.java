package com.example.tripplanner;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    Button loc;
    TextView vLat, vLon, memory, memory2;
    FusedLocationProviderClient fusedLocationProviderClient;
    // Object for Firebase date base to save our data to fire base.
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("location");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loc = findViewById(R.id.button);
        vLat = findViewById(R.id.txtLatitude);
        vLon = findViewById(R.id.txtLongitude);
        memory = findViewById(R.id.txtCatch);
        memory2 = findViewById(R.id.txtCatch_Lati);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        loc.setOnClickListener(v -> {
            // check permission
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // when permission granted
                getLocation();
            } else {
                //When permission denied
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        44);
            }
            try {
                throw new RuntimeException("Test Crash");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        try {
            addContentView(loc, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // To add data to shared preference
        addDataToSharedPreference();


    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {

            // Initialize location
            Location location = task.getResult();
            if (location != null) {
                try {
                    // Initialize geoCoder
                    Geocoder geocoder = new Geocoder(MainActivity.this,
                            Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                            location.getLongitude(), 1);

                    vLat.setText("Latitude: " + addresses.get(0).getLatitude());
                    vLon.setText("Longitude: " + addresses.get(0).getLongitude());
                    // To set our data to firebase data base.
                    setValue();
                    // To save data in catch memory.
                    saveData();
                } catch (Exception handle) {
                    handle.printStackTrace();
                }

                compareLocation();


            }
        });
    }


    // Add data to our RealTime database
    public void setValue() {
        String latitude = vLat.getText().toString().trim();
        String longitude = vLon.getText().toString().trim();
        int locationId = 0;

       dataHolder dh = new dataHolder(locationId,latitude,longitude);
        myRef.child(String.valueOf(locationId)).setValue(dh);
    }

    // Add data to Shared Preference
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("locationData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("longitude", vLon.getText().toString());
        editor.putString("latitude", vLat.getText().toString());
        editor.apply();

    }


    // Get our data from shared preference
    private void addDataToSharedPreference() {
        SharedPreferences getShared = getSharedPreferences("locationData", MODE_PRIVATE);
        String valueLatitude = getShared.getString("latitude", "");
        String valueLongitude = getShared.getString("longitude", "");
        memory.setText(valueLatitude);
        memory2.setText(valueLongitude);
    }

    private void compareLocation() {

        double lat1 = 0;
        double lon1 = 0;
        double lat2 = 0;
        double lon2 = 0;
        try {
            lat1 = Double.parseDouble(vLat.getText().toString());
            lon1 = Double.parseDouble(vLon.getText().toString());
            lat2 = Double.parseDouble(memory.getText().toString());
            lon2 = Double.parseDouble(memory2.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Location startPoint = new Location("location1");
        startPoint.setLatitude(lat1);
        startPoint.setLongitude(lon1);
        Location endPoint = new Location("location1");
        endPoint.setLatitude(lat2);
        endPoint.setLongitude(lon2);
        double distance = startPoint.distanceTo(endPoint);


        if (distance < 1000.00) {
            addDataToSharedPreference();
            addDataToRoomDatabase();


        }

    }

    private void addDataToRoomDatabase() {
        String latitude = vLat.getText().toString().trim();
        String longitude = vLon.getText().toString().trim();
        int locationId = 0;

        DatabseHelper databseHelper = DatabseHelper.getDB(this);
        databseHelper.rider_dao().addData(new dataHolder(locationId,latitude,longitude));
        databseHelper.rider_dao().updateData(new dataHolder(locationId,latitude,longitude));

    }

}

















