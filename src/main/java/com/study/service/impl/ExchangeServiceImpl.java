package com.study.service.impl;

import com.study.entity.DateCount;
import com.study.entity.Exchange;
import com.study.mapper.ExchangeMapper;
import com.study.service.ExchangeService;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    @Resource
    ExchangeMapper exchangeMapper;
    @Override
    public Exchange[] getExchangeList(int limit, int offset, String eid,String gid)throws DataAccessException {
        return exchangeMapper.findExchangeByPage(limit,offset,eid,gid);
    }

    @Override
    public boolean addExchange(Exchange exchange)throws DataAccessException {

        return exchangeMapper.insertExchange(exchange);
    }

    @Override
    public Exchange returnExchangeByEid(int eid)throws DataAccessException {
        return exchangeMapper.findExchangeByEid(eid);
    }
    @Override
    public Exchange[] returnExchangeByGid(int gid)throws DataAccessException {
        return exchangeMapper.findExchangeByGid(gid);
    }
    @Override
    public Exchange[] returnExchangeByUid(int uid)throws DataAccessException {
        return exchangeMapper.findExchangeByUid(uid);
    }
    @Override
    public DateCount[] returnExchangeCountByGid(int gid, int day)throws DataAccessException {
        return exchangeMapper.findExchangeCountByGid(gid,day);
    }
    @Override
    public boolean deleteExchange(int eid)throws DataAccessException {
        return exchangeMapper.dropExchangeByEid(eid);
    }
}
