package com.webshop.user.service;

import com.webshop.common.utils.PageResponse;
import com.webshop.user.model.vo.goods.GoodsReqVO_14;
import com.webshop.user.model.vo.goods.GoodsReqVO_15;
import com.webshop.user.model.vo.goods.GoodsReqVO_16;
import com.webshop.user.model.vo.goods.GoodsReqVO_17;

public interface GoodsService {
    PageResponse queryAllGoodsPage(GoodsReqVO_14 reqVO);

    PageResponse queryGoodsPageByKey(GoodsReqVO_15 reqVO);

    PageResponse queryGoodsByCategory(GoodsReqVO_16 reqVO);

    PageResponse queryGoodsPageByCategoryAndSearchKey(GoodsReqVO_17 reqVO);
}
