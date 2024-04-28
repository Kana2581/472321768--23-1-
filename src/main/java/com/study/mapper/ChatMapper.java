package com.study.mapper;

import com.study.entity.Chat;

import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

public interface ChatMapper{
    @Select("select * from chat where cid =#{cid}")
    public Chat findChatByCid(@Param("cid") int cid)throws DataAccessException;
    @Select("select * from chat  order by time desc limit #{limit} offset  #{offset};")
    public Chat[] findChatByPage(@Param("limit") int limit,@Param("offset") int offset)throws DataAccessException;

    @Select("select * from chat where (uid1=#{uid1} and uid2=#{uid2}) or (uid1=#{uid2} and uid2=#{uid1}) order by time")
    public Chat[] findChatByUid1AndUid2(@Param("uid1") int uid1,@Param("uid2") int uid2) throws DataAccessException;
    @Delete("delete from chat where cid=#{cid}")
    public boolean dropChatByCid(@Param("cid") int cid) throws DataAccessException;
    @Insert("insert into chat(uid1,uid2,message) values(#{uid1},#{uid2},#{message})")
    public boolean insertChat(Chat chat) throws DataAccessException;

}
