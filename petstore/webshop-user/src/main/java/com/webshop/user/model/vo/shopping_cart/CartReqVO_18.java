package com.webshop.user.model.vo.shopping_cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartReqVO_18 {

    @NotNull(message = "购物车ID不能为空")
    private Long shoppingcartid;

    @NotNull(message = "商品数量不能为空")
    private Integer quantity;
}
