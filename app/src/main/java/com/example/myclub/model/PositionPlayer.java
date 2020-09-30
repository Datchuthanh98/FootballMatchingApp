package com.example.myclub.model;

public class PositionPlayer {
    private  String id ;
    private  boolean gk;
    private  boolean lb;
    private  boolean cb;
    private  boolean cdm;
    private  boolean lm;
    private  boolean rm;
    private  boolean cam;
    private  boolean cm ;
    private  boolean lw ;
    private  boolean rw ;
    private  boolean st ;

    public PositionPlayer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isGk() {
        return gk;
    }

    public void setGk(boolean gk) {
        this.gk = gk;
    }

    public boolean isLb() {
        return lb;
    }

    public void setLb(boolean lb) {
        this.lb = lb;
    }

    public boolean isCb() {
        return cb;
    }

    public void setCb(boolean cb) {
        this.cb = cb;
    }

    public boolean isCdm() {
        return cdm;
    }

    public void setCdm(boolean cdm) {
        this.cdm = cdm;
    }

    public boolean isLm() {
        return lm;
    }

    public void setLm(boolean lm) {
        this.lm = lm;
    }

    public boolean isRm() {
        return rm;
    }

    public void setRm(boolean rm) {
        this.rm = rm;
    }

    public boolean isCam() {
        return cam;
    }

    public void setCam(boolean cam) {
        this.cam = cam;
    }

    public boolean isCm() {
        return cm;
    }

    public void setCm(boolean cm) {
        this.cm = cm;
    }

    public boolean isLw() {
        return lw;
    }

    public void setLw(boolean lw) {
        this.lw = lw;
    }

    public boolean isRw() {
        return rw;
    }

    public void setRw(boolean rw) {
        this.rw = rw;
    }

    public boolean isSt() {
        return st;
    }

    public void setSt(boolean st) {
        this.st = st;
    }
}
