package com.webshop.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("orders")
public class OrderDO {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private String userId;

    @TableField("good_id")
    private String goodId;

    @TableField("quantity")
    private int quantity;

    @TableField("price")
    private String price;

    @TableField("is_paid")
    private String isPaid;

    @TableField("order_id")
    private String orderId;

    @TableField("product_id")
    private String productId;

    @TableField("address")
    private String address;

    @TableField("recipient")
    private String recipient;
}
