package com.webshop.user.controller.openapi;

import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.shopping_cart.CartReqVO_20;
import com.webshop.user.model.vo.shopping_cart.CartRespVO_20;
import com.webshop.user.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 更推荐直接用 @RestController，等于 @Controller + @ResponseBody
@Slf4j
@Tag(name = "userApi")
@RequestMapping("/c/shoppingcarts")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    //18.更新购物车货物数量
    @PatchMapping("/shoppingcartid/{shoppingcartid}/quantity/{quantity}")
    public Response<Void> updateShoppingCartQuantity(@PathVariable Long shoppingcartid,
                                                     @PathVariable Integer quantity) {
        return shoppingCartService.updateShoppingCartQuantity(shoppingcartid, quantity);
    }

    //20.获取购物车内的所有商品
    @GetMapping("/current/{current}/size/{size}")
    public Response<List<CartRespVO_20>> getShoppingCartItems(@PathVariable Long current,
                                                              @PathVariable Long size) {
        CartReqVO_20 reqVO = CartReqVO_20.builder()
                .current(current)
                .size(size)
                .build();
        return shoppingCartService.queryCartPageByUserId(reqVO);
    }
}
