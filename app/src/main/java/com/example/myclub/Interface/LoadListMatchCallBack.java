package com.example.myclub.Interface;


import com.example.myclub.model.Match;

import java.util.List;

public interface LoadListMatchCallBack {
    void onSuccess(List<Match> mathList);

    void onFailure(String message);
}
