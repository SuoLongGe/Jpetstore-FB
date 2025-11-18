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
public class GetOrderByPageRspVO {
    private List<OrderDTO> data;   // 订单数据列表
    private Long total;            // 总订单数
    private Integer size;          // 每页大小
    private Integer current;       // 当前页码
    private Long pages;            // 总页数

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderDTO {
        private String orderid;     // 订单编号（格式为 用户id#时间）
        private List<ProductDTO> products; // 订单中的商品列表
        private String address;     // 收货地址
        private String recipient;   // 收货人
        private String is_paid;      // 是否已支付
        private String price;       // 总价格（如果是 double 也可以写成 BigDecimal）
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDTO {
        private Integer productid;  // 商品ID
        private Integer quantity;   // 数量
        private String picture;     // 商品图片
        private String name;        // 商品名称
        private String product_price;       // 商品价格
    }
}
