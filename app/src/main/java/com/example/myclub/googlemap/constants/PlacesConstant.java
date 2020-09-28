package com.example.myclub.googlemap.constants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myclub.googlemap.models.Results;


import java.util.ArrayList;
import java.util.List;

public class PlacesConstant {

    public static List<Results> results = new ArrayList<Results>();
    public static MutableLiveData<Integer> resultUpdate = new MutableLiveData<>(0);
    public static double longitude = 0;
    public static double latitude = 0;
    public static MutableLiveData<Integer> locationChange = new MutableLiveData<>(null);
    public static int radius = 5000;
}
