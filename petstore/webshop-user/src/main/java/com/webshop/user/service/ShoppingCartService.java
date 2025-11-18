package com.webshop.user.service;

import com.webshop.common.utils.PageResponse;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.shopping_cart.CartReqVO_20;
import com.webshop.user.model.vo.shopping_cart.CartRespVO_20;
import com.webshop.user.model.vo.shoppingcarts.AddShoppingcartsReqVO;
import com.webshop.user.model.vo.shoppingcarts.DeleteShoppingcartsReqVO;

public interface ShoppingCartService {

    Response addShoppingcart(AddShoppingcartsReqVO addShoppingcartsReqVO);

    Response<Void> updateShoppingCartQuantity(Long shoppingcartid, Integer quantity);

    PageResponse<CartRespVO_20> queryCartPageByUserId(CartReqVO_20 reqVO);

    Response deleteShoppingcart(DeleteShoppingcartsReqVO deleteShoppingcartsReqVO);
}
