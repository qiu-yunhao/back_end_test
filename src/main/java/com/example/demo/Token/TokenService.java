package com.example.demo.Token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.Util.JsonUtil;

import java.util.Date;

public class TokenService {
    private final static String crypto = "DemoTest";

    public static String getToken(int id, int type) {
        Date start = new Date();
        Date end = new Date(System.currentTimeMillis() + 30L * 24 * 3600 * 1000);
        return JWT.create().withAudience(new Temp(id, type).toString()).withIssuedAt(start).withExpiresAt(end).sign(Algorithm.HMAC256(crypto));
    }

    public static int getIdFromToken(String token) {
        String json = JWT.decode(token).getAudience().get(0);
        Temp temp = Temp.decode(json);
        return temp.id;
    }

    public static int getUserTypeFromToken(String token) {
        String json = JWT.decode(token).getAudience().get(0);
        Temp temp = Temp.decode(json);
        return temp.type;
    }


    public static boolean verityToken(String token) {
        if (token != null) {
            JWTVerifier result = JWT.require(Algorithm.HMAC256(crypto)).build();
            result.verify(token);
            return true;
        }
        return false;
    }

    static class Temp {
        private final int id;
        private final int type;

        public Temp(int id, int type) {
            this.id = id;
            this.type = type;
        }

        @Override
        public String toString() {
            return JsonUtil.toJson(this);
        }

        public static Temp decode(String s) {
            return (Temp) JsonUtil.toObject(s, Temp.class);
        }
    }
}
