package com.study.mapper;

import com.study.entity.Account;
import com.study.entity.Goods;
import com.study.entity.GoodsWithUserInformation;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface GoodsMapper {
    @Select("select goods.uid,title,name,portrait,img,time,price,time,details,status from goods left join user_information on user_information.uid=goods.uid where gid=#{gid}")
    public GoodsWithUserInformation findGoodsByGid(@Param("gid") int gid)throws DataAccessException;


    @Select("select * from goods where uid ${link} #{uid} and title like #{title} and gid like #{gid}  order by time desc limit #{limit} offset  #{offset};")
    public Goods[] findGoodsByPage(@Param("limit") int limit,@Param("offset") int offset,@Param("uid") String uid,@Param("gid") String gid,@Param("title") String title,@Param("link") String link)throws DataAccessException;

    @Select("select * from goods where uid ${link} #{uid} and title like #{title} and gid like #{gid} and status=0  order by time desc limit #{limit} offset  #{offset};")
    public Goods[] findGoodsByPageIndex(@Param("limit") int limit,@Param("offset") int offset,@Param("uid") String uid,@Param("gid") String gid,@Param("title") String title,@Param("link") String link)throws DataAccessException;

    @Select("select count(*)  from goods where uid ${link} #{uid} and gid like #{gid} and title like #{title}")
    public int findGoodsCount(@Param("uid") String uid,@Param("gid") String gid,@Param("title") String title,@Param("link") String link) throws DataAccessException;
    @Delete("delete from goods where gid=#{gid}")
    public boolean dropGoodsByGid(@Param("gid") int gid)throws DataAccessException;

    @Update("update goods set price=#{price},img=#{img},title=#{title},details=#{details},time=#{time},status=#{status} where gid=#{gid}")
    public boolean modifyGoods(Goods goods)throws DataAccessException;
    @Insert("insert into goods(price,img,title,details,uid,status,time) values(#{price},#{img},#{title},#{details},#{uid},#{status},#{time})")
    public boolean insertGoods(Goods goods) throws DataAccessException;
    @Delete("<script>" +
            "DELETE FROM goods WHERE gid IN " +
            "<foreach item='item' collection='dataToDelete' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    public int dropGoodsBatch(@Param("dataToDelete") List<Integer> dataToDelete);
}
