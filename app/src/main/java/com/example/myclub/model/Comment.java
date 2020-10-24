package com.example.myclub.model;

import com.google.firebase.Timestamp;

public class Comment {
    private String id ;
    private Player idPlayer;
    private String idMatch ;
    private String comment ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Player idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
