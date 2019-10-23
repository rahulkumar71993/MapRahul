package com.rentalmachine.maprahul;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    LatLngBounds.Builder builder;
    CameraUpdate cu;

    List<LatitudeLon> latitudeLonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LatitudeLon latitudeLon = new LatitudeLon();
        latitudeLon.setLat(28.61);
        latitudeLon.setLon(77.2099);
        latitudeLonList.add(latitudeLon);

        latitudeLon = new LatitudeLon();
        latitudeLon.setLat(30.75);
        latitudeLon.setLon(76.78);
        latitudeLonList.add(latitudeLon);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        mSetUpMap();
    }

    public void mSetUpMap() {
        map.clear();
        final List<Marker> markersList = new ArrayList<Marker>();

        for (int i = 0; i < latitudeLonList.size(); i++) {
            Marker aa = map.addMarker(new MarkerOptions().position(new LatLng(latitudeLonList.get(i).getLat(), latitudeLonList.get(i).getLon())).title("Arab"));
            markersList.add(aa);
        }

        builder = new LatLngBounds.Builder();
        for (Marker m : markersList) {
            builder.include(m.getPosition());
        }
        int padding = 100;
        LatLngBounds bounds = builder.build();
        cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                map.animateCamera(cu);
            }
        });
        map.setIndoorEnabled(true);
        map.setMyLocationEnabled(true);
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(false);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setZoomControlsEnabled(true);
    }
}
