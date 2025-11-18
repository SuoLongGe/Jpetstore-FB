package com.webshop.user.controller.openapi;

import com.webshop.common.aspect.ApiOperationLog;
import com.webshop.common.matchparam.RequestVO;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.orders.CreateOrderReqVO;
import com.webshop.user.model.vo.orders.GetOrderByPageReqVO;
import com.webshop.user.model.vo.orders.OrderQueryReqVO;
import com.webshop.user.model.vo.orders.PayOrderReqVO;
import com.webshop.user.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@ResponseBody
@Tag(name = "orderApi")
@RequestMapping("/c/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderid}")
    @ApiOperationLog(description = "/{orderid}")
    @Operation(summary = "/{orderid}")
    public Response getOrderInfo(@RequestVO @Validated OrderQueryReqVO orderQueryReqVO) {
        return orderService.getOrderInfo(orderQueryReqVO);
    }
    @GetMapping("/current/{current}/size/{size}")
    @ApiOperationLog(description = "/current/{current}/size/{size}")
    @Operation(summary = "/current/{current}/size/{size}")
    public Response getOrdersByPage(@RequestVO @Validated GetOrderByPageReqVO getOrderByPageReqVO) {
        return orderService.getOrdersByPage(getOrderByPageReqVO);
    }
    @PatchMapping("/{orderid}/paid")
    @ApiOperationLog(description = "/{orderid}/paid")
    @Operation(summary = "/{orderid}/paid")
    public Response payOrder(@RequestVO @Validated PayOrderReqVO payOrderReqVO) {
        return orderService.payOrder(payOrderReqVO);
    }

    @PostMapping("")
    @ApiOperationLog(description = "创建订单")
    @Operation(summary = "创建订单")
    public Response createOrder(@RequestBody @Valid CreateOrderReqVO reqVO) {
        return orderService.createOrder(reqVO);
    }


}
