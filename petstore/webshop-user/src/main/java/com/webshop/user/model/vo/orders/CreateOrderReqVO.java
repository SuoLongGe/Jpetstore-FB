package com.webshop.user.model.vo.orders;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderReqVO {
    @Valid
    @NotEmpty(message = "商品列表不能为空")
    private List<ShoppingCartItemVO> shoppingcarts;

    @NotBlank(message = "收货地址不能为空")
    private String address;

    @NotBlank(message = "收货人不能为空")
    private String recipient;
}

