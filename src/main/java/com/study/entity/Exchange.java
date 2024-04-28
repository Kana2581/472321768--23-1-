package com.study.entity;

import lombok.Data;

import java.sql.Time;

@Data
public class Exchange {
    int eid;
    int uid1;
    int uid2;
    int gid;
    String time;
    double money;

    public Exchange(int eid, int gid, double money) {
        this.eid = eid;
        this.gid = gid;
        this.money = money;
    }

    public Exchange(int gid, int uid2) {
        this.uid2 = uid2;
        this.gid = gid;
    }

    public Exchange() {

    }
}
