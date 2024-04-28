package com.study.entity;

import lombok.Data;

@Data
public class ExchangeAndGoods extends Exchange{

    String img;
    String title;
    

    public ExchangeAndGoods(int eid, int gid, double money) {
        super(eid, gid, money);
    }

}
