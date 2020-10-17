package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.LoadCallBack;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Booking;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListBookingVertical;

import java.util.List;

public class ListBookingViewModel extends ViewModel {

    private MatchRepository matchRepository = MatchRepository.getInstance();
    private RecycleViewAdapterListBookingVertical adapterListBooking = new RecycleViewAdapterListBookingVertical();
    private MutableLiveData<List<Booking>> listBookingFieldLiveData = new MutableLiveData<>();


    public ListBookingViewModel() {
        matchRepository.getListBooking(new LoadCallBack<List<Booking>, String>() {
            @Override
            public void onSuccess(List<Booking> bookingList) {
                listBookingFieldLiveData.setValue(bookingList);
                adapterListBooking.setListBooking(bookingList);
                adapterListBooking.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String s) {

            }
        });
//        matchRepository.getListBooking(new LoadListBookingCallBack() {
//            @Override
//            public void onSuccess(List<Booking> bookingList) {
//                listBookingFieldLiveData.setValue(bookingList);
//                adapterListBooking.setListBooking(bookingList);
//                adapterListBooking.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//            }
//
//
//        });
    }



    public RecycleViewAdapterListBookingVertical getAdapterListBooking() {
        return adapterListBooking;
    }

}
