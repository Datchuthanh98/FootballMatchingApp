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
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myclub.R;
import com.example.myclub.googlemap.adapter.PlaceRecyclerViewAdapter;
import com.example.myclub.googlemap.constants.PlacesConstant;
import com.example.myclub.googlemap.models.MyPlaces;
import com.example.myclub.googlemap.remotes.GoogleApiService;
import com.example.myclub.googlemap.remotes.RetrofitBuilder;
import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearByFragment extends Fragment {

    private ImageView imageViewSearch;
    private Spinner spinner_nearby_choices;
    private RecyclerView recyclerViewPlaces;


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


        PlacesConstant.locationChange.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null) return;
                getNearbyPlaces();

            }
        });


        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String distance = spinner_nearby_choices.getSelectedItem().toString();
               int intDistance = Integer.parseInt(distance);
                PlacesConstant.radius = intDistance;
                PlacesConstant.locationChange.setValue(intDistance);
                getNearbyPlaces();
            }


        });



        return view;
    }



    private String buildUrl(double latitude, double longitude, int radius, String API_KEY) {
        StringBuilder urlString = new StringBuilder("api/place/nearbysearch/json?");

        urlString.append("&location=");
        urlString.append(Double.toString(latitude));
        urlString.append(",");
        urlString.append(Double.toString(longitude));
        urlString.append("&radius=" + radius); // places between 5 kilometer
        urlString.append("&keyword=" + placeType.toLowerCase());
        urlString.append("&sensor=false&key=" + API_KEY);

        return urlString.toString();
    }

    private void getNearbyPlaces() {
        if (PlacesConstant.latitude != 0 && PlacesConstant.longitude != 0) {

            final ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();

            String apiKey = getContext().getResources().getString(R.string.google_map_api_key);
            String url = buildUrl(PlacesConstant.latitude, PlacesConstant.longitude, PlacesConstant.radius, apiKey);
            Log.d("finalUrl", url);

            googleApiService = RetrofitBuilder.builder().create(GoogleApiService.class);

            Call<MyPlaces> call = googleApiService.getMyNearByPlaces(url);

            call.enqueue(new Callback<MyPlaces>() {
                @Override
                public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                    Log.d("MyPlaces", response.body().toString());
                    myPlaces = response.body();


                    dialog.dismiss();
                    PlaceRecyclerViewAdapter adapter = new PlaceRecyclerViewAdapter(getContext(), myPlaces, PlacesConstant.latitude, PlacesConstant.longitude);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerViewPlaces.setLayoutManager(layoutManager);
                    recyclerViewPlaces.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewPlaces.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    PlacesConstant.results = myPlaces.getResults();
                    PlacesConstant.resultUpdate.setValue(myPlaces.getResults().size());
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
