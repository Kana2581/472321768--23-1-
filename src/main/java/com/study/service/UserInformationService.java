package com.study.service;

import com.study.entity.Account;
import com.study.entity.UserInformation;

public interface UserInformationService {
    Account[] getAccountList(int limit, int offset, String uid,String account);
    boolean addUserInformation(UserInformation goods);
    boolean addUserInformationAndSavePortrait(UserInformation userInformation,String cover);
    UserInformation returnUserInformation(int uid);
    UserInformation[] returnChatUserInformation(int uid);
    boolean addAccountAndUserInformation(Account account,UserInformation userInformation,String portrait);
    boolean editAccountAndUserInformation(Account account,UserInformation userInformation);
    boolean editAccountAndUserInformationAndSavePortrait(Account account, UserInformation userInformation,String portrait);
    int getAccountCount(String uid,String account);
}
