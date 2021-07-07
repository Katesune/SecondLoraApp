package com.example.loraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.klim.tcharts.TChart;
import com.klim.tcharts.entities.ChartData;
import com.klim.tcharts.entities.ChartItem;

import java.util.ArrayList;
import java.util.Random;

public class SensorActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TChart t_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        t_chart = findViewById(R.id.tchart);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        showGraph();
    }

    public void showGraph(){
        ArrayList<String> keys = new ArrayList<String>(); //keys for each chart
        ArrayList<String> names = new ArrayList<String>(); //names for chart
        ArrayList<Integer> colors = new ArrayList<Integer>(); //colors for lines
        ArrayList<ChartItem> items = new ArrayList<ChartItem>(); //charts value for some time

        //ChartItem
        // time - time point (on x line)
        // values - list values for this moment of time in order from keys

        keys.add("y0");
        keys.add("y1");
        names.add("Red Line");
        names.add("Green Line");
        colors.add(Color.RED);
        colors.add(Color.GREEN);

        long startTime = 1614542230000L;
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            //time moment
            startTime += 86_400_000;

            //all values for this time moment
            ArrayList<Integer> values = new ArrayList<Integer>();
            for (int j = 0; j < keys.size(); j++) {
                values.add(random.nextInt(1000));
            }

            ChartItem chartItem = new ChartItem(startTime, values);
            items.add(chartItem);
        }
        ChartData chartData = new ChartData(keys, names, colors, items);

        t_chart.setData(chartData, true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void gotoBack(View v) {
        // добавить роль

        String role = checkRole();
        Intent intent = new Intent();

        if (role.equals("MECHANIC"))  intent = new Intent(SensorActivity.this, ChooseActivity.class);
        else  intent = new Intent(SensorActivity.this, EngineerActivity.class);
    }

    protected String checkRole() {
        Role r = ((Role)getApplicationContext());
        return r.getRole();
    }
}