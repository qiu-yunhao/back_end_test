package com.example.demo.Bean;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "Users")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", length = 15)
    private String username;

    @Column(name = "password", length = 15)
    private String password;

    @Column(name = "type")
    private int type;

    @Column(name = "occupation", length = 6)
    private String occupation;


    public Users(int id, String username, String password, int type, String occupation) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.occupation = occupation;
    }

    public Users() {
        this.id = 0;
        this.username = "";
        this.password = "";
        this.type = 0;
        this.occupation = "";
    }

    public void clone(Users u) {
        if (!u.getPassword().isEmpty()) {
            this.password = u.password;
        }
        if (!u.getOccupation().isEmpty()) {
            this.occupation = u.occupation;
        }
        this.type = u.type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}