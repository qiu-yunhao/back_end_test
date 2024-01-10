package com.example.demo.Bean;

import jakarta.persistence.*;

@Entity(name = "userinfo")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private int uid;

    @Column(length = 25)
    private String email;

    @Column(length = 5)
    private String name;

    @Column(length = 15)
    private String phone;

    @Column(length = 15)
    private String address;

    @Column(name = "pid")
    private int pid;

    public UserInfo() {
        this.name = "";
        this.phone = "";
        this.email = "";
        this.address = "";
        this.pid = 0;
    }

    public void clone(UserInfo info) {
        if (!info.address.isEmpty()) {
            this.address = info.address;
        }
        if (!info.email.isEmpty()) {
            this.email = info.email;
        }
        if (!info.name.isEmpty()) {
            this.name = info.name;
        }
        if (!info.phone.isEmpty()) {
            this.phone = info.phone;
        }
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
