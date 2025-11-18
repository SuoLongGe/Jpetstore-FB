package com.webshop.user.model.vo.shopping_cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartReqVO_20 {
    private Long current;
    private Long size;
    private int userId;
}
