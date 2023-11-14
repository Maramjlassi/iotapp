package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.Manifest;
public class Location extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    TextView country,city,address,longitude,latitude,logout;
    Button getLocation;
    private final static int REQUEST_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        getLocation = findViewById(R.id.get_location_btn);

        city=findViewById(R.id.City);
        country=findViewById(R.id.Country);
        address=findViewById(R.id.Address);
        longitude=findViewById(R.id.Longitude);
        latitude=findViewById(R.id.Lagitude);
        logout =findViewById(R.id.Logout);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });


    }

    private void getLastLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
                @Override
                public void onSuccess(android.location.Location location) {
                    if (location!=null){
                        Geocoder geocoder=new Geocoder( Location.this, Locale.getDefault());
                        List<Address> addresses= null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            latitude.setText("Lagitude: "+ addresses.get(0).getLatitude());
                            longitude.setText("Longitude: "+ addresses.get(0).getLatitude());
                            address.setText("Address: "+ addresses.get(0).getAddressLine(0));
                            city.setText("City: "+ addresses.get(0).getLocality());
                            country.setText("Country: "+ addresses.get(0).getCountryName());
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }

                }
            });
        }else {
            askPermission();

        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(Location.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else{
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}