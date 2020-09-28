package com.example.myclub.googlemap.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.myclub.R;
import com.example.myclub.googlemap.constants.PlacesConstant;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationFragment extends Fragment {

    private TextView textViewAddress;
    private TextView textViewCity;
    private TextView textViewPostalCode;
    private TextView textViewLongitude;
    private TextView textViewLatitude;
    private TextView textViewCountryCode;
    private TextView textViewCountry;
    private TextView textViewDivision;
    private TextView textViewShowMe;

    public LocationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        textViewAddress = view.findViewById(R.id.address);
        textViewCity = view.findViewById(R.id.city);
        textViewLongitude = view.findViewById(R.id.textViewLongitude);
        textViewLatitude = view.findViewById(R.id.textViewLatitude);
        textViewCountryCode = view.findViewById(R.id.textViewCountryCode);
        textViewCountry = view.findViewById(R.id.textViewCountry);
        textViewDivision = view.findViewById(R.id.textViewDivision);
        textViewPostalCode = view.findViewById(R.id.postalCode);
        textViewShowMe = view.findViewById(R.id.show_me);

//        locationService();

        PlacesConstant.locationChange.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null) return;
                textViewLatitude.setText(Double.toString(PlacesConstant.latitude));
                textViewLongitude.setText(Double.toString(PlacesConstant.longitude));
                getAddress();
            }
        });

        textViewShowMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    private void getAddress() {

        String city = null;
        String address = null;
        String subCity = null;
        String postalCode = null;
        String division = null;
        String country = null;
        String countryCode = null;

        Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = gcd.getFromLocation(PlacesConstant.latitude, PlacesConstant.longitude, 1);
            if (addresses.size() > 0) {

                city = addresses.get(0).getLocality();
                address = addresses.get(0).getFeatureName();
                subCity = addresses.get(0).getSubLocality();
                postalCode = addresses.get(0).getPostalCode();
                division = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                countryCode = addresses.get(0).getCountryCode();

                textViewAddress.setText(addresses.get(0).getAddressLine(0));
                textViewCountryCode.setText(countryCode);
                textViewCountry.setText(country);
                textViewDivision.setText(division);
                textViewPostalCode.setText(postalCode);
                textViewCity.setText(city);

                //Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
                Log.d("LocationFragment", addresses.get(0).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
