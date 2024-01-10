package com.example.demo.Util;

import com.google.gson.Gson;

public class JsonUtil {
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static Object toObject(String json, Class<?> type) {
        return new Gson().fromJson(json,type);
    }


}
