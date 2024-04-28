package com.study.entity;

import lombok.Data;

@Data
public class Chat {
    int uid1;
    int uid2;
    String message;
    String time;

    public Chat(int uid1,int uid2, String message) {
        this.uid1 = uid1;
        this.uid2 = uid2;
        this.message = message;
    }
}
