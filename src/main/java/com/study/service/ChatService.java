package com.study.service;

import com.study.entity.Chat;
import com.study.entity.Exchange;
import org.apache.ibatis.annotations.Param;

public interface ChatService {
    Chat[] getChatList(int uid1,int uid2);
    boolean addChat(Chat chat);
    Chat  returnChat(int cid);
    boolean deleteChat(int cid);
}
