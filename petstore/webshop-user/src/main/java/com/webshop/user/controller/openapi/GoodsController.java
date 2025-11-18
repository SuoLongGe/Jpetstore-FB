package com.webshop.user.controller.openapi;

import com.webshop.common.matchparam.RequestVO;
import com.webshop.common.utils.PageResponse;
import com.webshop.user.model.vo.goods.GoodsReqVO_14;
import com.webshop.user.model.vo.goods.GoodsReqVO_15;
import com.webshop.user.model.vo.goods.GoodsReqVO_16;
import com.webshop.user.model.vo.goods.GoodsReqVO_17;
import com.webshop.user.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@ResponseBody
@Tag(name = "userApi")
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/current/{current}/size/{size}")
    @Operation(summary = "分页查询所有商品")
    public PageResponse queryGoods(@RequestVO @Validated GoodsReqVO_14 reqVO) {
        return goodsService.queryAllGoodsPage(reqVO);
    }

    /**
     * 根据当前页、每页大小和搜索关键字进行模糊查询
     */
    @GetMapping("/current/{current}/size/{size}/searchkey/{searchkey}")
    public PageResponse queryGoodsPage(@PathVariable Long current,
                                       @PathVariable Long size,
                                       @PathVariable String searchkey) {
        // 将请求参数封装为 GoodsReqVO_15 对象
        GoodsReqVO_15 reqVO = GoodsReqVO_15.builder()
                .current(current)
                .size(size)
                .searchkey(searchkey)
                .build();

        // 调用服务层方法获取分页查询结果
        return goodsService.queryGoodsPageByKey(reqVO);
    }

    //根据当前页、每页大小和搜索categoryid进行模糊查询
    @GetMapping("/current/{current}/size/{size}/categoryid/{categoryid}")
    public PageResponse queryGoodsByCategory(
            @PathVariable Long current,
            @PathVariable Long size,
            @PathVariable Integer categoryid) {

        // 将请求参数封装为 GoodsReqVO_15 对象
        GoodsReqVO_16 reqVO = GoodsReqVO_16.builder()
                .current(current)
                .size(size)
                .categoryid(categoryid)
                .build();

        // 调用服务层方法获取分页查询结果
        return goodsService.queryGoodsByCategory(reqVO);
    }

    //根据当前页、每页大小和搜索关键字和categoryid进行模糊查询
    @GetMapping("/current/{current}/size/{size}/categoryid/{categoryid}/searchkey/{searchkey}")
    public PageResponse queryGoodsPageByCategoryAndSearchKey(@PathVariable Long current,
                                                             @PathVariable Long size,
                                                             @PathVariable Integer categoryid,
                                                             @PathVariable String searchkey) {
        // 构建请求参数对象
        GoodsReqVO_17 reqVO = GoodsReqVO_17.builder()
                .current(current)
                .size(size)
                .categoryid(categoryid)
                .searchkey(searchkey)
                .build();

        // 调用 service 层方法获取分页数据
        return goodsService.queryGoodsPageByCategoryAndSearchKey(reqVO);
    }
}
