package com.study.entity;

import lombok.Data;

@Data
public class Count {
    int count;

    public Count(int count) {
        this.count = count;
    }
}
