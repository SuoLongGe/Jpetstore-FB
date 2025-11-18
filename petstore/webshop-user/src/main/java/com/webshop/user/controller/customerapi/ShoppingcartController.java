package com.webshop.user.controller.customerapi;

import com.webshop.common.aspect.ApiOperationLog;
import com.webshop.common.matchparam.RequestVO;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.shoppingcarts.AddShoppingcartsReqVO;
import com.webshop.user.model.vo.shoppingcarts.DeleteShoppingcartsReqVO;
import com.webshop.user.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@ResponseBody
@Tag(name = "ShoppingcartApi")
@RequestMapping("/c/shoppingcarts")
public class ShoppingcartController {

    @Autowired
    private ShoppingCartService shoppingcartService;

    /**
     * 添加购物车
     * @param addShoppingcartsReqVO
     * @return
     */
    @PostMapping("/productid/{productid}/quantity/{quantity}")
    @ApiOperationLog(description = "/productid/{productid}/quantity/{quantity}")
    @Operation(summary = "/productid/{productid}/quantity/{quantity}")
    public Response addShoppingcarts(@RequestVO @Validated AddShoppingcartsReqVO addShoppingcartsReqVO) {
        return shoppingcartService.addShoppingcart(addShoppingcartsReqVO);
    }

    @DeleteMapping("/shoppingcartid/{shoppingcartid}")
    @ApiOperationLog(description = "/shoppingcartid/{shoppingcartid}")
    @Operation(summary = "/shoppingcartid/{shoppingcartid}")
    public Response deleteShoppingcart(@RequestVO @Validated DeleteShoppingcartsReqVO deleteShoppingcartsReqVO) {
        return shoppingcartService.deleteShoppingcart(deleteShoppingcartsReqVO);
    }

}
