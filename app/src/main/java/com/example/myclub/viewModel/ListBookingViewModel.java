package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.repository.MatchRepository;
import com.example.myclub.model.Booking;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListBookingVertical;

import java.util.List;

public class ListBookingViewModel extends ViewModel {

    private MatchRepository matchRepository = MatchRepository.getInstance();
    private RecycleViewAdapterListBookingVertical adapterListBooking = new RecycleViewAdapterListBookingVertical();
    private MutableLiveData<List<Booking>> listBookingFieldLiveData = new MutableLiveData<>();


    public ListBookingViewModel() {
        matchRepository.getListBooking(new CallBack<List<Booking>, String>() {
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
    }



    public RecycleViewAdapterListBookingVertical getAdapterListBooking() {
        return adapterListBooking;
    }

}
