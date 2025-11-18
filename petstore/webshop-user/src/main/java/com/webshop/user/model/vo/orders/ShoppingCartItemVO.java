package com.webshop.user.model.vo.orders;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemVO {
    @NotBlank(message = "商品ID不能为空")
    private String shoppingcartid;
}