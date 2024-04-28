package com.study.mapper;

import com.study.entity.DateCount;
import com.study.entity.Exchange;
import com.study.entity.ExchangeAndGoods;
import com.study.entity.Goods;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

public interface ExchangeMapper {
    @Select("select * from exchange where eid =#{eid}")
    public Exchange findExchangeByEid(@Param("eid") int eid)throws DataAccessException;
    @Select("select eid,uid1,uid2,exchange.time,money,exchange.gid,price,img,title,details from exchange left join goods on exchange.gid=goods.gid where uid1 =#{uid} or uid2=#{uid}")
    public ExchangeAndGoods[] findExchangeByUid(@Param("uid") int uid)throws DataAccessException;
    @Select("select * from exchange where gid =#{gid}")
    public Exchange[] findExchangeByGid(@Param("gid") int gid)throws DataAccessException;
    @Select("SELECT DATE_FORMAT(`time`, '%c/%e') AS date, COUNT(*) AS count FROM exchange WHERE gid=#{gid} and `time` >= DATE_SUB(CURDATE(), INTERVAL #{day} DAY) AND `time` <= CURDATE() GROUP BY DATE_FORMAT(`time`, '%c/%e') ORDER BY date;")
    public DateCount[] findExchangeCountByGid(@Param("gid") int gid,@Param("day")int day)throws DataAccessException;

    @Select("select * from exchange where eid like #{eid} and gid like #{gid}  order by time desc limit #{limit} offset  #{offset};")
    public Exchange[] findExchangeByPage(@Param("limit") int limit,@Param("offset") int offset,@Param("eid") String eid,@Param("gid") String gid)throws DataAccessException;
    @Delete("delete from exchange where eid=#{eid}")
    public boolean dropExchangeByEid(@Param("eid") int eid)throws DataAccessException;
    @Update("update exchange set eid=#{eid},uid=#{uid},gid=#{gid},money=#{money} where eid=#{lastEid}")
    public boolean modifyExchange(Exchange exchange,@Param("lastEid")int eid)throws DataAccessException;
    @Insert("call insert_exchange_and_update_goods(#{uid2},#{gid})")
    public boolean insertExchange(Exchange exchange)throws DataAccessException;
}
