package com.study.service;

import com.study.entity.DateCount;
import com.study.entity.Exchange;
import com.study.entity.Goods;
import org.springframework.dao.DataAccessException;

public interface ExchangeService {
    Exchange[] getExchangeList(int limit, int offset, String eid,String uid);

    boolean addExchange(Exchange exchange);
    Exchange  returnExchangeByEid(int eid);
    Exchange[]  returnExchangeByGid(int gid);

    Exchange[]  returnExchangeByUid(int uid);

    DateCount[] returnExchangeCountByGid(int gid, int day);

    boolean deleteExchange(int eid);
}

