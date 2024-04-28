package com.study.entity;

import lombok.Data;

import java.sql.Date;
@Data
public class UserInformation {

        int uid;
        String name;
        String phone;
        String portrait;

    public UserInformation(int uid, String name, String phone) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
    }

    public UserInformation() {
    }

    public UserInformation(int uid, String name, String phone, String portrait) {
            this.uid = uid;
            this.name = name;
            this.phone = phone;
            this.portrait=portrait;

        }

    public UserInformation(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
