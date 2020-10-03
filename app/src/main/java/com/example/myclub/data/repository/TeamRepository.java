package com.example.myclub.data.repository;

import com.example.myclub.Interface.RegisterTeamCallBack;
import com.example.myclub.data.datasource.TeamDataSource;

public class TeamRepository {
    private static TeamRepository instance;
    private TeamDataSource teamDataSource = TeamDataSource.getInstance();



    private TeamRepository() {
    }

    public static TeamRepository getInstance() {
        if (instance == null) {
            instance = new TeamRepository();
        }
        return instance;
    }

    public void creatTeam(String name , String phone , String email , RegisterTeamCallBack callBack){
        teamDataSource.createTeam(name,phone,email,callBack);
    }







}
