package com.example.myclub.googlemap.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.myclub.R;
import  com.example.myclub.googlemap.activity.ShowPlacesOnMapActivity;
import  com.example.myclub.googlemap.adapter.PlaceRecyclerViewAdapter;
import  com.example.myclub.googlemap.constants.PlacesConstant;
import  com.example.myclub.googlemap.models.MyPlaces;
import  com.example.myclub.googlemap.remotes.GoogleApiService;
import  com.example.myclub.googlemap.remotes.RetrofitBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearByFragment extends Fragment {

    private ImageView imageViewSearch;
    private Spinner spinner_nearby_choices;
    private RecyclerView recyclerViewPlaces;
    private LinearLayout linearLayoutShowOnMap;
    double latitude;
    double longitude;

    ProgressDialog progressDialog;

    private FusedLocationProviderClient mFusedLocationClient;
    LocationManager lm;
    LocationManager locationManager;

    double lat = 0;
    double lng = 0;
    private String placeType = "Sân bóng";
    private GoogleApiService googleApiService;
    private MyPlaces myPlaces;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_near_by, container, false);
        spinner_nearby_choices = view.findViewById(R.id.spinner_nearby_choices);
        imageViewSearch = view.findViewById(R.id.imageViewSearch);
        recyclerViewPlaces = view.findViewById(R.id.recyclerViewPlaces);
        linearLayoutShowOnMap = view.findViewById(R.id.linearLayoutShowOnMap);

        locationService();

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int position = spinner_nearby_choices.getSelectedItemPosition();
//                if (position == 0) {
//                    Toast.makeText(getContext(), "Please select valid type", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
//                    placeType = spinner_nearby_choices.getSelectedItem().toString();
//                    getNearbyPlaces();
//                }

                getNearbyPlaces();

            }


        });

        linearLayoutShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacesConstant.results = myPlaces.getResults();
                Intent intent = new Intent(getContext(), ShowPlacesOnMapActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void locationService() {

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait while fetching data from GPS .......");
            progressDialog.setCancelable(false);
            progressDialog.show();


            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            final LocationListener locationListener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                progressDialog.dismiss();

                return;
            }

            progressDialog.dismiss();

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {

                        lat = location.getLatitude();
                        lng = location.getLongitude();

                    } else {
                        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }

                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                        } else if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), "GPS off", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {

            longitude = loc.getLongitude();
            latitude = loc.getLatitude();

            lat = loc.getLatitude();
            lng = loc.getLongitude();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    private String buildUrl(double latitude, double longitude, String API_KEY) {
        StringBuilder urlString = new StringBuilder("api/place/nearbysearch/json?");

        urlString.append("&location=");
        urlString.append(Double.toString(latitude));
        urlString.append(",");
        urlString.append(Double.toString(longitude));
        urlString.append("&radius=5000"); // places between 5 kilometer
        urlString.append("&keyword=" + placeType.toLowerCase());
        urlString.append("&sensor=false&key=" + API_KEY);

        return urlString.toString();
    }

    private void getNearbyPlaces() {
        if (lat != 0 && lng != 0) {

            final ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();

            String apiKey = getContext().getResources().getString(R.string.google_map_api_key);
            String url = buildUrl(lat, lng, apiKey);
            Log.d("finalUrl", url);

            googleApiService = RetrofitBuilder.builder().create(GoogleApiService.class);

            Call<MyPlaces> call = googleApiService.getMyNearByPlaces(url);

            call.enqueue(new Callback<MyPlaces>() {
                @Override
                public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                    Log.d("MyPlaces", response.body().toString());
                    myPlaces = response.body();
//                    Log.d("MyPlaces", myPlaces.getResults().get(0).toString());

                    dialog.dismiss();
                    PlaceRecyclerViewAdapter adapter = new PlaceRecyclerViewAdapter(getContext(), myPlaces, lat, lng);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerViewPlaces.setLayoutManager(layoutManager);
                    recyclerViewPlaces.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewPlaces.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    linearLayoutShowOnMap.setVisibility(View.VISIBLE);
                    linearLayoutShowOnMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PlacesConstant.results = myPlaces.getResults();
                            Intent intent = new Intent(getContext(), ShowPlacesOnMapActivity.class);
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFailure(Call<MyPlaces> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
