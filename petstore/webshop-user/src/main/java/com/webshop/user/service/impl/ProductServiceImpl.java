package com.webshop.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.webshop.common.domain.dos.ProductDO;
import com.webshop.common.domain.mapper.ProductMapper;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.products.ProductListReqVO;
import com.webshop.user.model.vo.products.ProductVO;
import com.webshop.user.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Response getProductsByGoodsId(ProductListReqVO reqVO) {
        try {
            // 类型转换验证
            Integer goodsId = Integer.valueOf(reqVO.getGoodsid());

            // 查询数据库
            List<ProductDO> products = productMapper.selectList(
                    new LambdaQueryWrapper<ProductDO>()
                            .eq(ProductDO::getGoodsId, goodsId)
            );

            // 转换为VO列表
            List<ProductVO> productVOs = products.stream().map(product ->
                    ProductVO.builder()
                            .productid(String.valueOf(product.getId()))
                            .productname(product.getProductName())
                            .productprice(product.getProductPrice())
                            .build()
            ).collect(Collectors.toList());

            return Response.success(productVOs);
        } catch (NumberFormatException e) {
            log.error("商品大类ID格式错误: {}", reqVO.getGoodsid());
            return Response.fail("INVALID_GOODS_ID");
        } catch (Exception e) {
            log.error("获取商品小类列表失败", e);
            return Response.fail("SYSTEM_ERROR");
        }
    }
}