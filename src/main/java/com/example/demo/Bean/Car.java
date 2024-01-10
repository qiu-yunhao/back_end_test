package com.example.demo.Bean;

import com.example.demo.Util.JsonUtil;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "car")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    @Column(length = 10)
    private String carname;

    @Column(length = 6)
    private String brand;

    @Column(length = 5)
    private String color;

    @Column(name = "cartype", length = 5)
    private String cartype;

    @Column
    private int value;

    @Column
    private int uid;

    public Car(int cid, String carname, String brand, String color, String cartype, int value, int uid) {
        this.cid = cid;
        this.carname = carname;
        this.brand = brand;
        this.color = color;
        this.cartype = cartype;
        this.value = value;
        this.uid = uid;
    }

    public Car() {
        this.cid = 0;
        this.carname = "";
        this.brand = "";
        this.color = "";
        this.cartype = "";
        this.value = 0;
        this.uid = 0;
    }

    public void getStoreMessage(Car car) {
        if (this.cartype.isEmpty()) {
            this.cartype = car.cartype;
        }

        if (this.brand.isEmpty()) {
            this.brand = car.brand;
        }

        if (this.value == 0) {
            this.value = car.value;
        }

        if (this.carname.isEmpty()) {
            this.carname = car.carname;
        }

        if (this.color.isEmpty()) {
            this.color = car.color;
        }

    }


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
