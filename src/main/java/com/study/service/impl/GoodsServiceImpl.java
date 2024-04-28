package com.study.service.impl;

import com.study.entity.Goods;
import com.study.mapper.GoodsMapper;
import com.study.service.GoodsService;
import com.study.utils.FileUtils;
import com.study.utils.RandomNameUtils;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    GoodsMapper goodsMapper;
    @Override
    public Goods[] getGoodsList(int limit, int offset, String uid,String gid,String title,String link) throws DataAccessException{
        return goodsMapper.findGoodsByPage(limit,offset,uid,gid,title,link);
    }
    public Goods[] getGoodsListIndex(int limit, int offset, String uid,String gid,String title,String link) throws DataAccessException{
        return goodsMapper.findGoodsByPageIndex(limit,offset,uid,gid,title,link);
    }

    @Override
    public boolean addGoods(Goods goods,String img) throws DataAccessException {
        String fileName= RandomNameUtils.createRandomName() +".jpeg";
        FileUtils.addImage(img, FileUtils.picturesPath+fileName);
        goods.setImg(fileName);
        return goodsMapper.insertGoods(goods);
    }

    @Override
    public Goods returnGoods(int gid)throws DataAccessException {

        return goodsMapper.findGoodsByGid(gid);
    }

    @Override
    public int getGoodsCount(String uid,String gid,String title,String link) {

        return goodsMapper.findGoodsCount(uid,gid,title,link);
    }

    @Override
    public boolean deleteGoods(int gid)throws DataAccessException {
        return goodsMapper.dropGoodsByGid(gid);
    }

    @Override
    public boolean editGoodsAndSaveImg(Goods goods,String cover)throws DataAccessException {
        String fileName= RandomNameUtils.createRandomName() +".jpeg";
        FileUtils.addImage(cover, FileUtils.picturesPath+fileName);
        goods.setImg(fileName);

        return goodsMapper.modifyGoods(goods);
    }

    @Override
    public boolean editGoods(Goods goods)throws DataAccessException {
        return goodsMapper.modifyGoods(goods);
    }

    @Override
    public int deleteGoodsBatch(List<Integer> dataToDelete) {
        return goodsMapper.dropGoodsBatch(dataToDelete);
    }

}
