package com.example.myclub.googlemap;


import com.example.myclub.googlemap.remotes.GoogleApiService;
import com.example.myclub.googlemap.remotes.RetrofitBuilder;

public class Common {
    private static final String BASE_URL = "https://maps.googleapis.com/";

    public static GoogleApiService getGoogleApiService() {
        return RetrofitBuilder.builder().create(GoogleApiService.class);
    }
}
