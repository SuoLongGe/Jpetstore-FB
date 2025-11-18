package com.webshop.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.webshop.common.domain.dos.GoodsDO;
import com.webshop.common.domain.dos.ProductDO;
import com.webshop.common.domain.dos.ShoppingCartDO;
import com.webshop.common.domain.dos.UserDO;
import com.webshop.common.domain.mapper.GoodsMapper;
import com.webshop.common.domain.mapper.ProductMapper;
import com.webshop.common.domain.mapper.ShoppingCartMapper;
import com.webshop.common.domain.mapper.UserMapper;
import com.webshop.common.utils.PageResponse;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.shopping_cart.CartReqVO_20;
import com.webshop.user.model.vo.shopping_cart.CartRespVO_20;
import com.webshop.user.model.vo.shoppingcarts.AddShoppingcartsReqVO;
import com.webshop.user.model.vo.shoppingcarts.DeleteShoppingcartsReqVO;
import com.webshop.user.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ShoppingCartMapper shoppingcartMapper;

    @Qualifier("goodsmapper")
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Response addShoppingcart(AddShoppingcartsReqVO addShoppingcartsReqVO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserDO user = userMapper.findByUsername(username);
        Integer id = user.getId();

        ProductDO product = productMapper.findById(Integer.valueOf(addShoppingcartsReqVO.getProductid()));
        String goodsId = product.getGoodsId();

        ShoppingCartDO shoppingcartDO = ShoppingCartDO.builder()
                .goodId(goodsId)
                .userId(String.valueOf(id))
                .quantity(addShoppingcartsReqVO.getQuantity())
                .isInOrder("0")
                .productId(addShoppingcartsReqVO.getProductid())
                .build();

        shoppingcartMapper.insert(shoppingcartDO);

        return Response.success();
    }

    //更新商品数量
    @Override
    public Response <Void> updateShoppingCartQuantity(Long shoppingcartid, Integer quantity) {
        //通过jwt用户鉴权查看是否具有更新商品数量的权限，如果有则执行下方代码，如果没有则return Response.fail("没有权限，更新购物车数量失败");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Integer userId = userMapper.findByUsername(username).getId();

        int rowsAffected = 0;

            // 执行更新操作
        rowsAffected = shoppingcartMapper.updateShoppingCartQuantity(shoppingcartid, quantity,userId);



        // 判断是否更新成功
        if (rowsAffected > 0) {
            return Response.success(); // 更新成功，data 为 null
        } else {
            return Response.fail("500", "更新购物车数量失败");
        }
    }

    //获取购物车内的所有商品
    @Override
    public PageResponse<CartRespVO_20> queryCartPageByUserId(CartReqVO_20 reqVO) {
        Long current = reqVO.getCurrent();
        Long size = reqVO.getSize();
        //通过jwt用户鉴权获取UserId，然后reqVO.setUserId(UserId)**********************************************;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Integer userId = userMapper.findByUsername(username).getId();
        reqVO.setUserId(userId);

        //而且这个方法查询的购物车内商品是不在订单内的，即is_in_order==0才可被查询到**********************************
        Page<ShoppingCartDO> cartPage = shoppingcartMapper.pageShoppingCart(current, size, reqVO.getUserId());
        List<ShoppingCartDO> cartDOList = cartPage.getRecords();

        List<CartRespVO_20> vos = cartDOList.stream()
                .map(ShoppingCartDO -> {
                    ProductDO product = productMapper.getProductInfoById(Integer.valueOf(ShoppingCartDO.getProductId()));
                    System.out.println("product_id = " + ShoppingCartDO.getProductId());
                    if (product == null) {

                        System.out.println("product为null");
                        return null; // 或者可以抛出一个异常，取决于需求
                    }
                    GoodsDO goods = goodsMapper.selectById(product.getGoodsId());
                    if (goods == null) {
                        System.out.println("goods为null");
                        return null; // 或者可以抛出一个异常，取决于需求
                    }

                    return CartRespVO_20.builder()
                            .shoppingcartid(ShoppingCartDO.getId())
                            .productid(Integer.valueOf(ShoppingCartDO.getProductId()))
                            .quantity(Integer.valueOf(ShoppingCartDO.getQuantity()))
                            .productname(product.getProductName())
                            .productprice(product.getProductPrice())
                            .goodsid(goods.getId())
                            .goodspicture(goods.getGoodspicture())
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return PageResponse.success(cartPage, vos);
    }

    @Override
    public Response deleteShoppingcart(DeleteShoppingcartsReqVO deleteShoppingcartsReqVO) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserDO user = userMapper.findByUsername(username);

        try {
            // 转换购物车ID为Integer类型
            Integer shoppingcartId = Integer.valueOf(deleteShoppingcartsReqVO.getShoppingcartid());

            // 查询购物车记录
            ShoppingCartDO shoppingcartDO = shoppingcartMapper.selectById(shoppingcartId);

            // 验证记录存在性
            if (shoppingcartDO == null) {
                log.warn("购物车记录不存在，ID: {}", shoppingcartId);
                return Response.fail("SHOPPINGCART_NOT_FOUND");
            }

            // 验证用户权限（比较字符串类型的用户ID）
            if (!shoppingcartDO.getUserId().equals(String.valueOf(user.getId()))) {
                log.warn("用户[{}]尝试删除他人购物车记录[{}]", user.getId(), shoppingcartId);
                return Response.fail("PERMISSION_DENIED");
            }

            // 构建更新对象（保持与添加方法一致的builder风格）
            ShoppingCartDO updateDO = ShoppingCartDO.builder()
                    .id(shoppingcartId)
                    .isInOrder("2")  // 修改状态为已删除
                    .build();

            // 执行更新操作
            shoppingcartMapper.updateById(updateDO);

            log.info("用户[{}]成功删除购物车记录[{}]", user.getId(), shoppingcartId);
            return Response.success();

        } catch (NumberFormatException e) {
            log.error("购物车ID格式转换异常: {}", deleteShoppingcartsReqVO.getShoppingcartid());
            return Response.fail("INVALID_ID_FORMAT");
        } catch (Exception e) {
            log.error("删除购物车记录时发生系统异常", e);
            return Response.fail("SYSTEM_ERROR");
        }
    }

}
