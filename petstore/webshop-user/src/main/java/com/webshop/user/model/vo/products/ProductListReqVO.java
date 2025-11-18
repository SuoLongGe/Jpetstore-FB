package com.webshop.user.model.vo.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListReqVO {
    private String goodsid;  // 对应路径参数名称
}
