package com.study.service.impl;

import com.study.entity.Account;
import com.study.mapper.UserMapper;
import com.study.service.UserService;
import jakarta.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper mapper;




    @Override
    public boolean addAccount(Account account)throws DataAccessException {
        return mapper.addAccount(account);
    }

    @Override
    public Account returnAccount(int uid)throws DataAccessException {
        return mapper.findAccountByUid(uid);
    }

    @Override
    public boolean deleteAccount(int uid)throws DataAccessException {
        return mapper.dropAccountByUid(uid);
    }
    @Override
    public boolean editAccount(Account account)throws DataAccessException {
        return mapper.modifyAccount(account);
    }

    @Override
    public boolean editPassword(Account account) {
        return mapper.modifyPassword(account);
    }

    @Override
    public int deleteAccountBatch(List<Integer> dataToDelete) {
        return mapper.dropAccountBatch(dataToDelete);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null)
            throw new UsernameNotFoundException("用户名不能为空");
        Account account = mapper.findAccountByAccount(username);
        if(account == null)
            throw new UsernameNotFoundException("用户名或密码错误");
        return User

                .withUsername(String.valueOf(account.getUid()))
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }
}
