package com.webshop.user.service;

import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.products.ProductListReqVO;

public interface ProductService {
    Response getProductsByGoodsId(ProductListReqVO reqVO);
}