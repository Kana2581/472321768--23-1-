package com.study.controller;

import com.study.entity.Chat;
import com.study.entity.Exchange;
import com.study.entity.RestBean;
import com.study.service.ChatService;
import com.study.service.ExchangeService;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chat/api")
@RestController
public class ChatApiController {
    @Resource
    ChatService chatService;
    @PostMapping("/add-chat")
    public RestBean addChat(@RequestParam("uid2")int uid2,@RequestParam("message")String message,@AuthenticationPrincipal UserDetails userDetails) {
    try{
        int uid1=Integer.valueOf(userDetails.getUsername());
        if( chatService.addChat(new Chat(uid1,uid2,message)))
            return RestBean.success("添加成功");
        else
            return RestBean.failure(400,"添加失败");

        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }
    @GetMapping("/find-chat-by-cid")
    public RestBean findChatByGid(@RequestParam("cid")int cid)
    {
        try{

            return RestBean.success(chatService.returnChat(cid));
        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }

    @GetMapping("/find-chat-page")
    public RestBean findChatPage(@RequestParam("uid2")int uid2,@AuthenticationPrincipal UserDetails userDetails)
    {
        int uid1=Integer.valueOf(userDetails.getUsername());
        try{
            return RestBean.success(chatService.getChatList(uid1,uid2));
        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }

    }
}

