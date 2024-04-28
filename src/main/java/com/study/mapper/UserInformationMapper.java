package com.study.mapper;

import com.study.entity.Account;
import com.study.entity.AccountAndUserInformation;
import com.study.entity.UserInformation;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DataAccessException;

public interface UserInformationMapper {

    @Select("select * from user_information where uid =#{uid}")
    public UserInformation findUserInformationByUid(@Param("uid") int uid)throws DataAccessException;

    @Update("INSERT INTO user_information (uid, name,phone,portrait) VALUES (#{uid}, #{name},#{phone},#{portrait}) ON DUPLICATE KEY UPDATE name=#{name},phone=#{phone},portrait=#{portrait}")
    public boolean modifyUserInformation(UserInformation userInformation)throws DataAccessException;
    @Select("select * from Account left join user_information on Account.uid=user_information.uid where account.uid like #{uid}  and account like #{account}  limit #{limit} offset  #{offset};")
    AccountAndUserInformation[] findAccountByPage(@Param("limit") int limit, @Param("offset") int offset, @Param("uid") String uid,@Param("account") String account)throws DataAccessException;
    @Select("CALL Insert_Account_And_Get_Uid(#{account}, #{password});")
    int insertAccountAndGetUid(Account account)throws DataAccessException;
    @Select("select count(*)  from account where account.uid like #{uid}  and account like #{account} ")
    public int findAccountCount( @Param("uid") String uid,@Param("account") String account) throws DataAccessException;
    @Select("select t1.uid,name,portrait from (SELECT uid1 AS uid FROM chat WHERE uid2 = #{uid} UNION SELECT uid2 AS uid FROM chat WHERE uid1 = #{uid}) as t1 left join user_information on user_information.uid =t1.uid;")
    public UserInformation[] findChatUserInformationByUid(@Param("uid") int uid)throws DataAccessException;
}
