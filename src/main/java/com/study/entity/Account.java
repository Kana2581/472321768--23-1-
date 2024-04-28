package com.study.entity;

import lombok.Data;

@Data
public class Account {
    int uid;
    String account;
    String password;
    String role;

    public Account(){

    }

    public Account(String account, String password) {
        this.account = account;
        this.password = password;
    }
    public Account(String account, String password,String role) {
        this.account = account;
        this.password = password;
        this.role=role;
    }

    public Account(int uid, String password) {
        this.uid = uid;
        this.password = password;
    }

    public Account(int uid, String account, String password, String role) {
        this.uid = uid;
        this.account = account;
        this.password = password;
        this.role = role;
    }
}
