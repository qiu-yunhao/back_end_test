package com.example.demo.Bean;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(name = "maintenanceinfo")
public class MaintenanceInfo {

    @Column(insertable = false, updatable = false)
    private int cid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mid;

    @Column(name = "date", length = 20)
    private String date;

    @Column(length = 20)
    private String position;
    @Column(length = 20)
    private String question;
    @Column(name = "issolve")
    private boolean issolve;

    @Column
    private int value;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    private Car car;


    public MaintenanceInfo(int cid, int mid, String date, String position, String question, boolean isSolve) {
        this.cid = cid;
        this.mid = mid;
        this.date = date;
        this.position = position;
        this.question = question;
        this.issolve = isSolve;
    }

    public MaintenanceInfo() {
        this.cid = 0;
        this.mid = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        this.date = format.format(new Date());
        this.position = "";
        this.question = "";
        this.issolve = false;
        this.value = 100;
    }

    public void clone(MaintenanceInfo info) {
        if (!issolve) {
            this.issolve = info.issolve;
        }
        if (!info.question.isEmpty()) {
            this.question = info.question;
        }
        if (!info.position.isEmpty()) {
            this.position = info.position;
        }
        if (!info.date.isEmpty()) {
            this.date = info.date;
        }
        if (info.getValue() != 0) {
            this.value = info.value;
        }
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getMid() {
        return mid;
    }


    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isIssolve() {
        return issolve;
    }

    public void setIssolve(boolean issolve) {
        this.issolve = issolve;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
