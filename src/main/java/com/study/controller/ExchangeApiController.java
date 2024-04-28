package com.study.controller;

import com.study.entity.Exchange;
import com.study.entity.RestBean;
import com.study.service.ExchangeService;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/exchange/api")
@RestController
public class ExchangeApiController {

    @Resource
    ExchangeService exchangeService;
    @PostMapping("/add-exchange")
    public RestBean addExchange(@RequestParam("gid")int gid,@AuthenticationPrincipal UserDetails userDetails)
    {
        int uid=Integer.valueOf(userDetails.getUsername());

        try {
            if(exchangeService.addExchange(new Exchange(gid,uid)))
                return RestBean.success("添加成功");
            else
                return RestBean.failure(400,"添加失败");

        }catch (DataAccessException e)
        {
            return RestBean.failure(400,e.getMessage());
        }
    }

    @GetMapping("/find-exchange-by-eid")
    public RestBean findExchangeByEid(@RequestParam("eid")int eid)
    {
        try {
            return RestBean.success(exchangeService.returnExchangeByEid(eid));
        }catch (DataAccessException e)
        {
            return RestBean.failure(400,e.getMessage());
        }
    }
    @GetMapping("/find-exchange-by-uid")
    public RestBean findExchangeByUid(@AuthenticationPrincipal UserDetails userDetails)
    {
        int uid=Integer.valueOf(userDetails.getUsername());
        try {
            return RestBean.success(exchangeService.returnExchangeByUid(uid));
        }catch (DataAccessException e)
        {
            return RestBean.failure(400,e.getMessage());
        }
    }
    @GetMapping("/find-exchange-by-gid")
    public RestBean findExchangeByGid(@RequestParam("gid")int gid)
    {
        try {
            return RestBean.success(exchangeService.returnExchangeByGid(gid));
        }catch (DataAccessException e)
        {
            return RestBean.failure(400,e.getMessage());
        }

    }
    @GetMapping("/find-exchange-date-count-by-gid")
    public RestBean findExchangeDateCountByGid(@RequestParam("gid")int gid,@RequestParam("day")int day)
    {
        try {
            return RestBean.success(exchangeService.returnExchangeCountByGid(gid,day));
        }catch (DataAccessException e)
        {
            return RestBean.failure(400,e.getMessage());
        }

    }
    @GetMapping("/find-exchange-page")
    public RestBean findExchangePage(@RequestParam("limit")int limit,@RequestParam("offset")int offset,@RequestParam(value = "eid",required = false,defaultValue = "%")String eid,@RequestParam(value = "gid",required = false,defaultValue = "%")String gid)
    {

        try {
            return RestBean.success(exchangeService.getExchangeList(limit,offset,"%"+eid+"%","%"+gid+"%"));
        }catch (DataAccessException e)
        {
            return RestBean.failure(400,e.getMessage());
        }


    }
}
