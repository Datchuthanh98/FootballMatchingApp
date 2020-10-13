package com.example.myclub.Interface;

import com.example.myclub.model.RequestJoinTeam;

public interface GetStateJoinTeam {
    void onSuccess(RequestJoinTeam requestJoinTeam);
    void onFailure (String e);
}
