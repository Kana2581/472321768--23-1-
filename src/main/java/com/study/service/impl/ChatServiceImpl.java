package com.study.service.impl;

import com.study.entity.Chat;
import com.study.mapper.ChatMapper;
import com.study.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    ChatMapper chatMapper;
    @Override
    public Chat[] getChatList(int uid1, int uid2) throws DataAccessException {
        return chatMapper.findChatByUid1AndUid2(uid1,uid2);
    }

    @Override
    public boolean addChat(Chat chat) throws DataAccessException{
        return chatMapper.insertChat(chat);
    }

    @Override
    public Chat returnChat(int cid)throws DataAccessException {
        return chatMapper.findChatByCid(cid);
    }

    @Override
    public boolean deleteChat(int cid)throws DataAccessException {
        return chatMapper.dropChatByCid(cid);
    }
}
