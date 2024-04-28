package com.study.service;

import com.study.entity.Account;
import com.study.entity.Goods;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsService {
    Goods[] getGoodsList(int limit, int offset, String uid,String gid,String title,String link);
    Goods[] getGoodsListIndex(int limit, int offset, String uid,String gid,String title,String link);

    boolean addGoods(Goods goods,String cover);
    Goods returnGoods(int gid);
    int getGoodsCount(String uid,String gid,String title,String link);
    boolean deleteGoods(int gid);
    boolean editGoodsAndSaveImg(Goods goods,String cover);
    boolean editGoods(Goods goods);

    int deleteGoodsBatch(List<Integer> dataToDelete);
}
