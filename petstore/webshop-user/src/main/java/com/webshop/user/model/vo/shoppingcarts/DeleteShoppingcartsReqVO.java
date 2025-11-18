package com.webshop.user.model.vo.shoppingcarts;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteShoppingcartsReqVO {
    @NotBlank(message = "购物车ID不能为空")
    private String shoppingcartid;
}