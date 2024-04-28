package com.study.controller;

import com.study.entity.Account;
import com.study.entity.RestBean;
import com.study.service.UserService;
import com.study.utils.AuthUtils;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("/auth/api")
@RestController
public class AuthApiController {
    @Resource
    UserService userService;
    @Resource
    BCryptPasswordEncoder passwordEncoder;
    @PostMapping("/add-account")
    public RestBean addAccount(@RequestParam("account")String account, @RequestParam("password")String password)
    {

        try{
            if(userService.addAccount(new Account(account, passwordEncoder.encode(password))))
                return RestBean.success("注册成功");
            else
                return RestBean.failure(400,"注册失败");
        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }
    @GetMapping("/find-account-by-uid")
    public RestBean findAccountByUid(@RequestParam("uid")int uid)
    {
        try{
            return RestBean.success(userService.returnAccount(uid));
        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }

    @DeleteMapping("/delete-account")
    public RestBean deleteAccount(@RequestParam(value = "uid",required = false)int uid,@AuthenticationPrincipal UserDetails userDetails)
    {
        if(!AuthUtils.isAdmin(userDetails.getAuthorities()))
        {
            uid=Integer.parseInt(userDetails.getUsername());
        }
        try{

            return RestBean.success(userService.deleteAccount(uid));
        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }

    }
    @DeleteMapping("/delete-account-batch")
    public RestBean deleteGoods(@RequestParam("dataToDelete") List<Integer> dataToDelete)
    {

        return RestBean.success(userService.deleteAccountBatch(dataToDelete));
    }

    @PostMapping("/update-account")
    public RestBean updateAccountPage(@RequestParam("uid")int uid,@RequestParam("account")String account,@RequestParam("password") String password,@RequestParam("role")String role,@AuthenticationPrincipal UserDetails userDetails)
    {
        if(!AuthUtils.isAdmin(userDetails.getAuthorities()))
        {
            uid=Integer.parseInt(userDetails.getUsername());
        }
        try{
            return RestBean.success(userService.editAccount(new Account(uid,account,password,role)));
        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }

    }
    @PostMapping("/update-password")
    public RestBean updateAccountPage(@RequestParam("password") String password,@AuthenticationPrincipal UserDetails userDetails)
    {

        int uid=Integer.parseInt(userDetails.getUsername());

        try{
            return RestBean.success(userService.editPassword(new Account(uid,passwordEncoder.encode(password))));
        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }

    }
}
