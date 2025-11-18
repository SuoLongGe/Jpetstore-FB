package com.webshop.user.model.vo.shopping_cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRespVO_20 {
    private Integer shoppingcartid;
    private Integer productid;
    private Integer quantity;
    private String productname;
    private String productprice;
    private Integer goodsid;
    private String goodspicture;
}
