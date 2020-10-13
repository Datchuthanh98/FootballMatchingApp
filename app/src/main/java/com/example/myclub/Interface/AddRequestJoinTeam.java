package com.example.myclub.Interface;

import com.example.myclub.model.RequestJoinTeam;
import com.example.myclub.model.Team;
import com.example.myclub.model.Todo;

import java.util.List;

public interface AddRequestJoinTeam {
    void onSuccess(String key);
    void onFailure();
}
