package com.study.controller;


import com.study.entity.Account;
import com.study.entity.RestBean;
import com.study.entity.UserInformation;
import com.study.service.UserInformationService;
import com.study.utils.AuthUtils;
import com.study.utils.Base64ToFile;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RequestMapping("user/api/")
@RestController
public class UserInformationApiController {
    @Resource
    BCryptPasswordEncoder passwordEncoder;
    @Resource
    UserInformationService userInformationService;
    @GetMapping("/find-userInformation-by-uid")
    public RestBean findUserInformationByGid(@RequestParam("uid")int uid)
    {
        try{
            return RestBean.success(userInformationService.returnUserInformation(uid));
        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }

    @GetMapping("/find-account-count")
    public RestBean findAccountCount()
    {
        try {
            return RestBean.success(userInformationService.getAccountCount("%","%"));
        }catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }
    @GetMapping("/find-account-page")
    public RestBean findAccountPage(@RequestParam("limit")int limit,@RequestParam("page")int page,@RequestParam(value = "uid",required = false,defaultValue = "%")String uid,@RequestParam(value = "account",required = false,defaultValue = "%")String account)
    {
        int offset=(page-1)*limit;
        try{
            return RestBean.success(userInformationService.getAccountCount("%"+uid+"%","%"+account+"%"),userInformationService.getAccountList(limit,offset,"%"+uid+"%","%"+account+"%"));
        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }
    @PostMapping("/add-account-and-userInformation")
    public RestBean addAccountAndUserInformation(@RequestParam("account")String account, @RequestParam("password")String password,@RequestParam("role")String role, @RequestParam("name")String name, @RequestParam("phone")String phone, @RequestParam("portrait")String portrait)
    {

        try{
            if(userInformationService.addAccountAndUserInformation(new Account(account,passwordEncoder.encode(password),role),new UserInformation(name,phone),portrait))
                return RestBean.success("添加成功");
            else
                return RestBean.failure(400,"添加失败");
        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }



    @PostMapping("/update-account-and-userInformation")
    public RestBean updateAccountAndUserInformation(@RequestParam("uid")int uid,@RequestParam("account")String account,@RequestParam("role")String role, @RequestParam("password")String password, @RequestParam("name")String name, @RequestParam("phone")String phone, @RequestParam("portrait")String portrait)
    {
        try{
            if (Base64ToFile.Base64Split(portrait)!=-1)
            {
                if(userInformationService.editAccountAndUserInformationAndSavePortrait(new Account(uid,account,passwordEncoder.encode(password),role),new UserInformation(uid,name,phone),portrait))
                    return RestBean.success("修改成功");
                else
                    return RestBean.failure(400,"修改失败");
            }else
            {
                if(userInformationService.editAccountAndUserInformation(new Account(uid,account,passwordEncoder.encode(password),role),new UserInformation(uid,name,phone,Base64ToFile.extractLastString(portrait))))
                    return RestBean.success("修改成功");
                else
                    return RestBean.failure(400,"修改失败");
            }

        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }

    @GetMapping("/me")
    public RestBean<UserInformation>me(@AuthenticationPrincipal UserDetails userDetails){
        return RestBean.success(userInformationService.returnUserInformation(Integer.valueOf(userDetails.getUsername())));
    }
    @GetMapping("/find-chat-information-page")
    public RestBean findChatInformationPage(@RequestParam("uid")int uid){
        return RestBean.success(userInformationService.returnChatUserInformation(uid));
    }

    @PostMapping("/update-userInformation")
    public RestBean updateUserInformationPage(@RequestParam(value = "uid",required = false)int uid, @RequestParam("name")String name, @RequestParam("phone")String phone, @RequestParam("portrait")String portrait,@AuthenticationPrincipal UserDetails userDetails)
    {
        if(!AuthUtils.isAdmin(userDetails.getAuthorities()))
        {
            uid=Integer.parseInt(userDetails.getUsername());
        }
        try{
            if (Base64ToFile.Base64Split(portrait)!=-1)
            {
                if(userInformationService.addUserInformationAndSavePortrait(new UserInformation(uid,name,phone, portrait),portrait))
                    return RestBean.success("修改成功");
                else
                    return RestBean.failure(400,"修改失败");
            }else
            {
                if(userInformationService.addUserInformation(new UserInformation(uid,name,phone, Base64ToFile.extractLastString(portrait))))
                    return RestBean.success("修改成功");
                else
                    return RestBean.failure(400,"修改失败");
            }

        } catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }


    }
}
