package com.example.myclub.googlemap.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.myclub.R;
import com.example.myclub.googlemap.constants.PlacesConstant;
import com.example.myclub.googlemap.models.Results;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ShowPlaceOnMapFragment extends Fragment implements OnMapReadyCallback {
    List<Results> results = new ArrayList<Results>();
    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_place_on_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // tạo ra map trống
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        // observe cái livedata list, khi
        PlacesConstant.resultUpdate.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (PlacesConstant.locationChange.getValue() == null) return;
                results = PlacesConstant.results;
                mapView.getMapAsync(ShowPlaceOnMapFragment.this);
            }
        });

        PlacesConstant.locationChange.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Toast.makeText(getContext(),"get change location in Map",Toast.LENGTH_SHORT).show();
                if (PlacesConstant.locationChange.getValue() == null) return;
                results = PlacesConstant.results;
                mapView.getMapAsync(ShowPlaceOnMapFragment.this);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // add marker for myself
        MarkerOptions myMarkerOptions = new MarkerOptions();
        myMarkerOptions.position(new LatLng(PlacesConstant.latitude, PlacesConstant.longitude));
        myMarkerOptions.title("You");
        googleMap.addMarker(myMarkerOptions);

        //moveCametra
        LatLng MylatLng =new LatLng(PlacesConstant.latitude,PlacesConstant.longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(MylatLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MylatLng, 15.0f));
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // add markers for stadiums
        for (int i = 0; i < results.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            Results googlePlace = results.get(i);
            double lat = Double.parseDouble(googlePlace.getGeometry().getLocation().getLat());
            double lng = Double.parseDouble(googlePlace.getGeometry().getLocation().getLng());
            String placeName = googlePlace.getName();
            String vicinity = googlePlace.getVicinity();
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.field_24));
            // add marker to map
            googleMap.addMarker(markerOptions).showInfoWindow();;


        }
    }
}
