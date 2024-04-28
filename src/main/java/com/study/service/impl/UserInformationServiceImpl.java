package com.study.service.impl;

import com.study.entity.Account;
import com.study.entity.UserInformation;
import com.study.mapper.UserInformationMapper;
import com.study.mapper.UserMapper;
import com.study.service.UserInformationService;
import com.study.utils.Base64ToFile;
import com.study.utils.FileUtils;
import com.study.utils.RandomNameUtils;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInformationServiceImpl implements UserInformationService {

    @Resource
    UserInformationMapper userInformationMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public boolean addUserInformationAndSavePortrait(UserInformation userInformation,String cover)throws DataAccessException {
        String fileName= RandomNameUtils.createRandomName() +".jpeg";
        FileUtils.addImage(cover, FileUtils.portraitsPath+fileName);
        userInformation.setPortrait(fileName);
        return userInformationMapper.modifyUserInformation(userInformation);
    }
    public boolean addUserInformation(UserInformation userInformation)throws DataAccessException {
        return userInformationMapper.modifyUserInformation(userInformation);
    }

    @Override
    public UserInformation returnUserInformation(int uid)throws DataAccessException {
        return userInformationMapper.findUserInformationByUid(uid);
    }

    @Override
    public UserInformation[] returnChatUserInformation(int uid) throws DataAccessException{
        return userInformationMapper.findChatUserInformationByUid(uid);
    }

    @Transactional
    @Override
    public boolean editAccountAndUserInformationAndSavePortrait(Account account, UserInformation userInformation,String portrait) throws DataAccessException{
        userMapper.modifyAccount(account);
        return addUserInformationAndSavePortrait(userInformation,portrait);
    }
    @Transactional
    @Override
    public boolean editAccountAndUserInformation(Account account, UserInformation userInformation) throws DataAccessException{

        userMapper.modifyAccount(account);
        return addUserInformation(userInformation);

    }
    @Transactional
    @Override
    public boolean addAccountAndUserInformation(Account account, UserInformation userInformation,String portrait) throws DataAccessException{
        int uid=userInformationMapper.insertAccountAndGetUid(account);
        userInformation.setUid(uid);
        return addUserInformationAndSavePortrait(userInformation,portrait);

    }
    @Override
    public int getAccountCount(String uid,String account) {
        return userInformationMapper.findAccountCount(uid,account);
    }
    @Override
    public Account[] getAccountList(int limit, int offset, String uid,String account) throws DataAccessException {
        return userInformationMapper.findAccountByPage(limit,offset,uid,account);
    }


}
