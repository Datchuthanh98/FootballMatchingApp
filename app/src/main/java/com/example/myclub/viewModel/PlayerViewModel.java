package com.example.myclub.viewModel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.UpdateProfileCallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.PlayerRepository;

import java.util.Map;

public class PlayerViewModel extends ViewModel {
    private PlayerRepository playerRepository= PlayerRepository.getInstance();
    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private String resultMessage = null;

    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void updateProfile(Map<String, Object> updateBasic) {
        playerRepository.updateProfile(updateBasic, new UpdateProfileCallBack() {
            @Override
            public void onSuccess() {
                resultLiveData.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public  void updateImage(Uri uri, String path , final UpdateProfileCallBack callBack){
        playerRepository.updateImage(uri, path, new UpdateProfileCallBack() {
            @Override
            public void onSuccess() {
                resultLiveData.setValue(Result.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }

}
