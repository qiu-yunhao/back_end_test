package com.example.demo.Bean;

import com.example.demo.Util.JsonUtil;

import java.io.Serializable;

public class Token implements Serializable {
    private String token;

    public Token(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
