package com.example.myclub.Interface;


import com.example.myclub.model.Booking;
import com.example.myclub.model.Player;

import java.util.List;

public interface LoadListBookingCallBack {
    void onSuccess(List<Booking> bookingList);

    void onFailure(String message);
}
