package com.study.service;

import com.study.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {


    boolean addAccount(Account account);
    Account returnAccount(int uid);

    boolean deleteAccount(int bid);

    boolean editAccount(Account account);
    boolean editPassword(Account account);
    int deleteAccountBatch(List<Integer> dataToDelete);
}
