package com.webshop.user.model.vo.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderQueryRspVO {

    // 产品信息列表
    private List<ProductDTO> products;

    // 收货地址
    private String address;

    // 收货人
    private String recipient;

    // 是否发货
    private String is_paid;

    // 总价格
    private Double price;

    // 产品DTO类，表示订单中的每个商品
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDTO {
        // 产品ID
        private String productid;

        // 产品数量
        private int quantity;

        private String name;
    }
}
