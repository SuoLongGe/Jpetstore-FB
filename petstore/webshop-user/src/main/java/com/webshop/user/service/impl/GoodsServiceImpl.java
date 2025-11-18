package com.webshop.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.webshop.common.domain.dos.GoodsDO;
import com.webshop.common.domain.mapper.GoodsMapper;
import com.webshop.common.utils.PageResponse;
import com.webshop.user.model.vo.goods.*;
import com.webshop.user.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    //分页查询所有商品
    @Override
    public PageResponse queryAllGoodsPage(GoodsReqVO_14 reqVO) {
        // 获取当前页、以及每页需要展示的数据数量
        Long current = reqVO.getCurrent();
        Long size = reqVO.getSize();

        // 调用 GoodsMapper 的分页查询方法，传入当前页、每页数量
        Page<GoodsDO> goodsDOPage = goodsMapper.searchPageGoods(current, size);

        // 获取查询到的商品列表
        List<GoodsDO> goodsDOS = goodsDOPage.getRecords();

        // 用于存放转换后的响应数据
        List<GoodsRespVO_14> vos = null;

        // 如果商品列表不为空，进行数据转换
        if (!CollectionUtils.isEmpty(goodsDOS)) {
            vos = goodsDOS.stream()
                    .map(goodsDO -> GoodsRespVO_14.builder()
                            .goodsid(goodsDO.getId())
                            .goodsname(goodsDO.getGoodsname())
                            .goodsprice(goodsDO.getGoodsprice())
                            .goodspicture(goodsDO.getGoodspicture())
                            .categoryid(goodsDO.getCategoryid())
                            .build())
                    .collect(Collectors.toList());
        }

        // 返回分页响应，包含原始分页数据和转换后的响应数据
        return PageResponse.success(goodsDOPage, vos);
    }

    //根据当前页、每页大小和搜索关键字进行模糊查询
    @Override
    public PageResponse queryGoodsPageByKey(GoodsReqVO_15 reqVO) {
        // 获取当前页、每页大小和搜索关键字
        Long current = reqVO.getCurrent();
        Long size = reqVO.getSize();
        String searchKey = reqVO.getSearchkey();

        // 调用 GoodsMapper 的分页查询方法，传入当前页、每页数量、和搜索关键字
        Page<GoodsDO> goodsDOPage = goodsMapper.searchPageGoodsBySearchKey(current, size, searchKey);

        // 获取查询到的商品列表
        List<GoodsDO> goodsDOS = goodsDOPage.getRecords();

        // 用于存放转换后的响应数据
        List<GoodsRespVO_14> vos = null;

        // 如果商品列表不为空，进行数据转换
        if (!CollectionUtils.isEmpty(goodsDOS)) {
            vos = goodsDOS.stream()
                    .map(goodsDO -> GoodsRespVO_14.builder()
                            .goodsid(goodsDO.getId())  // 使用 id 字段
                            .goodsname(goodsDO.getGoodsname())
                            .goodsprice(goodsDO.getGoodsprice())
                            .goodspicture(goodsDO.getGoodspicture())
                            .categoryid(goodsDO.getCategoryid())
                            .build())
                    .collect(Collectors.toList());
        }

        // 返回分页响应，包含原始分页数据和转换后的响应数据
        return PageResponse.success(goodsDOPage, vos);
    }

    //根据当前页、每页大小和搜索categoryid进行模糊查询
    @Override
    public PageResponse queryGoodsByCategory(GoodsReqVO_16 reqVO) {
        // 获取当前页和每页需要展示的数据数量
        Long current = reqVO.getCurrent();
        Long size = reqVO.getSize();
        Integer categoryId = reqVO.getCategoryid();

        // 调用 GoodsMapper 的分页查询方法，传入分类ID、当前页和每页数量
        Page<GoodsDO> goodsDOPage = goodsMapper.searchPageGoodsByCategory(current, size, categoryId);

        // 获取查询到的商品列表
        List<GoodsDO> goodsDOS = goodsDOPage.getRecords();

        // 用于存放转换后的响应数据
        List<GoodsRespVO_14> vos = null;

        // 如果商品列表不为空，进行数据转换
        if (!CollectionUtils.isEmpty(goodsDOS)) {
            vos = goodsDOS.stream()
                    .map(goodsDO -> GoodsRespVO_14.builder()
                            .goodsid(goodsDO.getId())
                            .goodsname(goodsDO.getGoodsname())
                            .goodsprice(goodsDO.getGoodsprice())
                            .goodspicture(goodsDO.getGoodspicture())
                            .categoryid(goodsDO.getCategoryid())
                            .build())
                    .collect(Collectors.toList());
        }

        // 返回分页响应，包含原始分页数据和转换后的响应数据
        return PageResponse.success(goodsDOPage, vos);
    }

    //根据当前页、每页大小和搜索关键字和categoryid进行模糊查询
    @Override
    public PageResponse queryGoodsPageByCategoryAndSearchKey(GoodsReqVO_17 reqVO) {
        // 获取请求参数
        Long current = reqVO.getCurrent();
        Long size = reqVO.getSize();
        Integer categoryid = reqVO.getCategoryid();
        String searchkey = reqVO.getSearchkey();

        // 调用 GoodsMapper 的分页查询方法，传入当前页、每页数量、分类ID和搜索关键字
        Page<GoodsDO> goodsDOPage = goodsMapper.searchPageGoodsByCategoryAndSearchKey(current, size, categoryid, searchkey);

        // 获取查询到的商品列表
        List<GoodsDO> goodsDOS = goodsDOPage.getRecords();

        // 用于存放转换后的响应数据
        List<GoodsRespVO_14> vos = null;

        // 如果商品列表不为空，进行数据转换
        if (!CollectionUtils.isEmpty(goodsDOS)) {
            vos = goodsDOS.stream()
                    .map(goodsDO -> GoodsRespVO_14.builder()
                            .goodsid(goodsDO.getId())
                            .goodsname(goodsDO.getGoodsname())
                            .goodsprice(goodsDO.getGoodsprice())
                            .goodspicture(goodsDO.getGoodspicture())
                            .categoryid(goodsDO.getCategoryid())
                            .build())
                    .collect(Collectors.toList());
        }

        // 返回分页响应，包含原始分页数据和转换后的响应数据
        return PageResponse.success(goodsDOPage, vos);
    }
}
