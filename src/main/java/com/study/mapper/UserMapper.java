package com.study.mapper;

import com.study.entity.Account;
import com.study.entity.AccountAndUserInformation;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface UserMapper {
    @Select("select * from account where uid=#{uid}")
    public Account findAccountByUid(@Param("uid") int uid)throws DataAccessException;
    @Select("select * from account where account=#{account}")
    public Account findAccountByAccount(@Param("account") String account)throws DataAccessException;
    @Insert("insert into account(uid,account,password) value(#{uid},#{account},#{password})")
    public Boolean addAccount(Account account)throws DataAccessException;
    @Delete("delete from account where uid = #{uid}")
    boolean dropAccountByUid(int uid)throws DataAccessException;

    @Update("update account set uid=#{uid},account=#{account},password=#{password},role=#{role} where uid=#{uid}")
    Boolean modifyAccount(Account account)throws DataAccessException;
    @Update("update account set password=#{password} where uid=#{uid}")
    Boolean modifyPassword(Account account)throws DataAccessException;
    @Delete("<script>" +
            "DELETE FROM account WHERE uid IN " +
            "<foreach item='item' collection='dataToDelete' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    public int dropAccountBatch(@Param("dataToDelete") List<Integer> dataToDelete);

}
