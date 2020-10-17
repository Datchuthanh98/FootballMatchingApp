package com.example.myclub.googlemap2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;



import java.util.ArrayList;
import java.util.List;

public class PlacesConstant {
    public static double longitude = 0;
    public static double latitude = 0;
    public static MutableLiveData<Integer> locationChange = new MutableLiveData<>(null);
    public static int radius = 500;
}
