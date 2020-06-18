package com.example.easygps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
        else{
            locationStart();

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,50,this);
        }
    }



    private void locationStart(){
        Log.d("debug", "locationStart()");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Log.d("debug", "location manager Enabled");
        }
        else{
            Intent settingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingIntent);
            Log.d("debug", "not gpsEnable, StartActivity");
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            Log.d("debug", "checkSelfPermission false");
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("debug", "chechSelfPermission true");
                locationStart();
            }
            else{
                Toast.makeText(this, "これ以上は何もできません", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView textView1 = findViewById(R.id.text_view1);
        String str1 = "Location:" + location.getLatitude();
        textView1.setText(str1);

        TextView textView2 = findViewById(R.id.text_view2);
        String str2 = "Longtude" + location.getLongitude();
        textView2.setText(str2);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
