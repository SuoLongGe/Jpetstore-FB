package com.webshop.user.service;

import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.orders.CreateOrderReqVO;
import com.webshop.user.model.vo.orders.GetOrderByPageReqVO;
import com.webshop.user.model.vo.orders.OrderQueryReqVO;
import com.webshop.user.model.vo.orders.PayOrderReqVO;

public interface OrderService {
    Response getOrderInfo(OrderQueryReqVO orderQueryReqVO) ;
    Response getOrdersByPage(GetOrderByPageReqVO getOrderByPageReqVO) ;
    Response payOrder(PayOrderReqVO payOrderReqVO);

    Response createOrder(CreateOrderReqVO reqVO);
}
