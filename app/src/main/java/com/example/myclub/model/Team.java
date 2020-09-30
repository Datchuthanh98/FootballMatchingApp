package com.example.myclub.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private String id;
    private String name;
    private String urlAvatar;
    private String urlCover;
    private String email;
    private String phone;
    private Location location;
    private String introduction;
    private Player caption;
    private List<Player> listPlayers = new ArrayList<>();
    private List<MatchHistory> listMatchSchedule = new ArrayList<>();
    private List<MatchSchedule> listMatchHisotry  = new ArrayList<>();
    private Timestamp lastUpdateNotificationTimestamp;


}
