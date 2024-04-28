package com.study.controller;

import com.study.entity.*;
import com.study.service.GoodsService;
import com.study.utils.AuthUtils;
import com.study.utils.Base64ToFile;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/goods/api")
@RestController
public class GoodsApiController {

    @Resource
    GoodsService goodService;
    @PostMapping("/add-goods")
    public RestBean addGoods(@RequestParam("price")Double price, @RequestParam("img")String img,@RequestParam("title")String title,@RequestParam("details")String details,@RequestParam("status")int status,@RequestParam(value = "time",required = false)String time,@AuthenticationPrincipal UserDetails userDetails)
    {

        if(time==null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time = dateFormat.format(new Date());
        }

        int uid=Integer.parseInt(userDetails.getUsername());

        try {
            Goods goods=new Goods(uid,price,title,details,status,time);
            goods.setUid(uid);
            goodService.addGoods(goods,img);
                return RestBean.success("添加成功");

        }catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }

    }
    @PostMapping("/update-goods")
    public RestBean editGoods(@RequestParam(value = "uid",required = false)int uid,@RequestParam("gid")int gid,@RequestParam("price")Double price, @RequestParam("img")String img,@RequestParam("title")String title,@RequestParam("details")String details,@RequestParam("status")int status,@RequestParam(value = "time",required = false)String time)
    {

        if(time==null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time = dateFormat.format(new Date());
        }

        try {
            if (Base64ToFile.Base64Split(img)!=-1)
            {
                if(goodService.editGoodsAndSaveImg(new Goods(gid,price,title,details,status,time),img))
                    return RestBean.success("修改成功");
                else
                    return RestBean.success("修改失败");
            }else
            {
                Goods goods=new Goods(gid,price,title,details,status,time);
                goods.setImg(Base64ToFile.extractLastString(img));
                if(goodService.editGoods(goods))
                    return RestBean.success("修改成功");
                else
                    return RestBean.success("修改失败");

            }
        }catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }

    }
    @GetMapping("/find-goods-by-gid")
    public RestBean findGoodsByGid(@RequestParam("gid")int gid)
    {
        try {
            return RestBean.success(goodService.returnGoods(gid));

        }catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }

    }
    @DeleteMapping("/delete-goods")
    public RestBean deleteGoods(@RequestParam("gid")int gid,@AuthenticationPrincipal UserDetails userDetails)
    {
        if(!AuthUtils.isAdmin(userDetails.getAuthorities()))
        {
            if(goodService.returnGoods(gid).getUid()!=Integer.parseInt(userDetails.getUsername()))
                return RestBean.failure(400,"没有权限");

        }
        int uid=Integer.valueOf(userDetails.getUsername());
        try {
            return RestBean.success(goodService.deleteGoods(gid));

        }catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }

    }
    @DeleteMapping("/delete-goods-batch")
    public RestBean deleteGoods(@RequestParam("dataToDelete") List<Integer> dataToDelete)
    {

        return RestBean.success(goodService.deleteGoodsBatch(dataToDelete));
    }
    @GetMapping("/find-goods-page")
    public RestBean findGoodsPage(@RequestParam("limit")int limit,@RequestParam("page")int page,@RequestParam(value = "uid",required = false,defaultValue = "%")String uid,@RequestParam(value = "title",required = false,defaultValue = "%")String title,@RequestParam(value = "gid",required = false,defaultValue = "%")String gid,@AuthenticationPrincipal UserDetails userDetails)
    {
        String link;
        if(AuthUtils.isAdmin(userDetails.getAuthorities()))
        {
            uid="%"+uid+"%";
            link="like";
        }else {
            uid = userDetails.getUsername();
            link="=";
        }
        int offset=(page-1)*limit;
        try {
            return RestBean.success(goodService.getGoodsCount(uid,"%"+gid+"%","%"+title+"%",link),goodService.getGoodsList(limit,offset,uid,"%"+gid+"%","%"+title+"%",link));
        }catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }
    @GetMapping("/find-goods-count")
    public RestBean findGoodsCounts()
    {
        String link="like";

        try {
            return RestBean.success(goodService.getGoodsCount("%","%","%",link));
        }catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }
    @GetMapping("/find-goods-page-index")
    public RestBean findGoodsPage()
    {
        String link="like";
        int limit=1000;
        int offset=0;
        try {
            return RestBean.success(goodService.getGoodsCount("%","%","%",link),goodService.getGoodsListIndex(limit,offset,"%","%","%",link));
        }catch (DataAccessException e) {
            return RestBean.failure(400,e.getMessage());
        }
    }

//    @GetMapping("/find-page-mum")
//    public RestBean findGoodsPage()
//    {
//        try {
//            return RestBean.success(new Count(goodService.getGoodsCount()));
//        }catch (DataAccessException e) {
//            return RestBean.failure(400,e.getMessage());
//        }
//    }



}
