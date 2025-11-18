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
@TableName("product")
public class ProductDO {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("goods_id")
    private String goodsId;

    @TableField("product_name")
    private String productName;

    @TableField("product_price")
    private String productPrice;
}
