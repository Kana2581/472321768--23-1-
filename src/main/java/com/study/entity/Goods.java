package com.study.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Goods {
    int gid;
    int uid;
    double price;
    String img;
    String title;
    String details;
    int status;
    String time;


    public Goods(int gid, double price, String title, String details, int status, String time) {
        this.gid = gid;
        this.price = price;
        this.title = title;
        this.details = details;
        this.status = status;
        this.time = time;
    }

    public Goods() {
    }
}
