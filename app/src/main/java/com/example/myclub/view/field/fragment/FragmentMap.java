package com.example.myclub.view.field.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myclub.R;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Field;
import com.example.myclub.model.PlacesConstant;
import com.example.myclub.view.field.fragment.FragmentProfileField;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentMap extends Fragment implements OnMapReadyCallback {
    private Map<Marker, Field> fieldMarkers = new HashMap<>();
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
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.clear();
        // add marker for myself
        MarkerOptions myMarkerOptions = new MarkerOptions();
        myMarkerOptions.position(new LatLng(PlacesConstant.latitude, PlacesConstant.longitude));
        myMarkerOptions.title("You");
        googleMap.addMarker(myMarkerOptions);

//        moveCamera to my location
        LatLng MylatLng = new LatLng(PlacesConstant.latitude, PlacesConstant.longitude);
        Log.d("MyLocation", MylatLng.latitude + " " + MylatLng.longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(MylatLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MylatLng, 15.0f));
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // lay du lieu tu firebase ve
        FirebaseFirestore.getInstance().collection("Field").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d("GetFields", "onSuccess");
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                    Field field = documentSnapshot.toObject(Field.class);
                    MarkerOptions markerOptions = new MarkerOptions();
                    double lat = Double.parseDouble(field.getLatitude());
                    double lng = Double.parseDouble(field.getLongitude());
                    Log.d("GetField", "field: " + lat + " " + lng);
                    String fieldName = field.getName();
                    // set latlng google map object
                    LatLng latLng = new LatLng(lat, lng);
                    markerOptions.position(latLng);
                    markerOptions.title(fieldName);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.field_24));
                    Marker marker = googleMap.addMarker(markerOptions);
                    marker.showInfoWindow();
                    fieldMarkers.put(marker, field);
                }
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return false;
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Field field = fieldMarkers.get(marker);
                if (field != null) {
                    FragmentProfileField fragmentProfileField = new FragmentProfileField(field);
                    ActivityHome activityHome = (ActivityHome) getContext();
                    activityHome.addFragment(fragmentProfileField);
                }
            }
        });
    }
}
