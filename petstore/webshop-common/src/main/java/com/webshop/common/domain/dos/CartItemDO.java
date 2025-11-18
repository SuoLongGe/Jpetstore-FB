package com.webshop.common.domain.dos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDO {
    private Integer shoppingcartid;  // 购物车ID
    private Integer productid;       // 商品ID
    private Integer quantity;        // 商品数量
    private String productname;      // 商品名称
    private String productprice;     // 商品价格
    private Integer goodsid;          // 商品的goodsid
    private String goodspicture;     // 商品图片路径

    public CartItemDO(Integer shoppingcartid, Integer productid, Integer goodsid, Integer quantity,
                    String productname, String productprice, String goodspicture) {
        this.shoppingcartid = shoppingcartid;
        this.productid = productid;
        this.goodsid = goodsid;
        this.quantity = quantity;
        this.productname = productname;
        this.productprice = productprice;
        this.goodspicture = goodspicture;
    }
}
