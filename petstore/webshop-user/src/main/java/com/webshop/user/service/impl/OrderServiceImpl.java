package com.webshop.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.webshop.common.domain.dos.OrderDO;
import com.webshop.common.domain.dos.ProductDO;
import com.webshop.common.domain.dos.ShoppingCartDO;
import com.webshop.common.domain.dos.UserDO;
import com.webshop.common.domain.mapper.*;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.orders.*;
import com.webshop.user.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired private ProductMapper productMapper;
    @Autowired private ShoppingCartMapper shoppingcartMapper;
    @Qualifier("goodsmapper")
    @Autowired
    private GoodsMapper goodsmapper;

    /**
     * 查询订单信息（根据 orderid）
     */
    @Override
    public Response getOrderInfo(OrderQueryReqVO orderQueryReqVO) {
        if (orderQueryReqVO == null || orderQueryReqVO.getOrderid() == null) {
            return Response.fail("INVALID_REQUEST");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Integer userId = userMapper.findByUsername(username).getId();

        String orderId = orderQueryReqVO.getOrderid();
        List<OrderDO> orderList = orderMapper.findByOrderId(orderId,userId);

        if (orderList == null || orderList.isEmpty()) {
            return Response.fail("ORDER_NOT_FOUND");
        }

        OrderDO firstOrder = orderList.get(0);

        List<OrderQueryRspVO.ProductDTO> products = orderList.stream()
                .map(order -> OrderQueryRspVO.ProductDTO.builder()
                        .productid(order.getProductId())
                        .quantity(order.getQuantity())
                        .name(productMapper.getProductInfoById(Integer.valueOf(order.getProductId())).getProductName())
                        .build())
                .toList();

        double totalPrice = 0;
        for (OrderDO order : orderList) {
            try {
                double itemPrice = Double.parseDouble(order.getPrice());
                int quantity = order.getQuantity();
                totalPrice += itemPrice * quantity;
            } catch (NumberFormatException e) {
            }
        }
        OrderQueryRspVO vos = OrderQueryRspVO.builder()
                .products(products)
                .address(firstOrder.getAddress())
                .recipient(firstOrder.getRecipient())
                .is_paid(firstOrder.getIsPaid())
                .price(totalPrice)
                .build();
        return Response.success(vos);
    }
    public Response getOrdersByPage(GetOrderByPageReqVO getOrderByPageReqVO) {
        int current = getOrderByPageReqVO.getCurrent();
        int size = getOrderByPageReqVO.getSize();
        int offset = (current - 1) * size;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Integer userId = userMapper.findByUsername(username).getId();

        // 查询分页订单数据
        List<OrderDO> orderDOList = orderMapper.findOrdersByPage(offset, size,userId);
        Long total = orderMapper.countOrders(userId); // 总记录数
        Long pages = (total + size - 1) / size; // 计算总页数

        // 按 orderid 分组
        Map<String, List<OrderDO>> orderMap = orderDOList.stream()
                .collect(Collectors.groupingBy(OrderDO::getOrderId));

        List<GetOrderByPageRspVO.OrderDTO> orderDTOList = new ArrayList<>();
        for (Map.Entry<String, List<OrderDO>> entry : orderMap.entrySet()) {
            String orderid = entry.getKey();
            List<OrderDO> orders = entry.getValue();
            OrderDO first = orders.get(0); // 每个订单的公共信息只取第一条

            List<GetOrderByPageRspVO.ProductDTO> products = orders.stream()
                    .map(order -> GetOrderByPageRspVO.ProductDTO.builder()
                            .productid(Integer.valueOf(order.getProductId()))
                            .quantity(order.getQuantity())
                            .picture(goodsmapper.getGoodsById(Integer.valueOf(order.getGoodId())).getGoodspicture())
                            .name(productMapper.getProductInfoById(Integer.valueOf(order.getProductId())).getProductName())//这里查询两次是需要优化的，可以用缓存
                            .product_price(productMapper.getProductInfoById(Integer.valueOf(order.getProductId())).getProductPrice())
                            .build())
                    .collect(Collectors.toList());

            double totalPrice = orders.stream().mapToDouble(order -> {
                try {
                    return Double.parseDouble(order.getPrice()) * order.getQuantity();
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            }).sum();

            orderDTOList.add(GetOrderByPageRspVO.OrderDTO.builder()
                    .orderid(orderid)
                    .products(products)
                    .address(first.getAddress())
                    .recipient(first.getRecipient())
                    .is_paid(first.getIsPaid())
                    .price(String.valueOf(totalPrice))
                    .build());
        }

        GetOrderByPageRspVO rsp = GetOrderByPageRspVO.builder()
                .data(orderDTOList)
                .total(total)
                .size(size)
                .current(current)
                .pages(pages)
                .build();

        return Response.success(rsp);
    }
    public Response payOrder(PayOrderReqVO payOrderReqVO) {
        // 参数校验
        if (payOrderReqVO == null || payOrderReqVO.getOrderid() == null || payOrderReqVO.getOrderid().isEmpty()) {
            return Response.fail("无效请求");
        }
        String orderId = payOrderReqVO.getOrderid();
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Integer userId = userMapper.findByUsername(username).getId();

        // 查询订单是否存在
        List<OrderDO> orderList = orderMapper.findByOrderId(orderId,userId);
        if (orderList == null || orderList.isEmpty()) {
            return Response.fail("订单不存在");
        }
        // 判断是否已支付过
        boolean alreadyPaid = orderList.stream().allMatch(order -> order.getIsPaid() != null && Integer.parseInt(order.getIsPaid())== 1);
        if (alreadyPaid) {
            return Response.fail("订单已经支付");
        }

        // 更新 is_paid = 1
        int rows = orderMapper.updateIsPaidByOrderId(orderId, 1);
        if (rows <= 0) {
            return Response.fail("支付失败");
        }
        return Response.success();
    }

    @Override
    @Transactional
    public Response createOrder(CreateOrderReqVO reqVO) {
        // 获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDO user = userMapper.findByUsername(authentication.getName());
        Integer userId = user.getId();

        // 生成订单号
        String orderId = generateOrderId(userId);

        for (int i =0; i < reqVO.getShoppingcarts().size(); i++) {
            ShoppingCartItemVO item = reqVO.getShoppingcarts().get(i);
            // 转换购物车ID为Integer类型
            Integer shoppingcartId = Integer.valueOf(item.getShoppingcartid());

            // 查询购物车记录
            ShoppingCartDO shoppingcartDO = shoppingcartMapper.selectById(shoppingcartId);

            // 验证记录存在性
            if (shoppingcartDO == null) {
                log.warn("购物车记录不存在，ID: {}", shoppingcartId);
                return Response.fail("SHOPPINGCART_NOT_FOUND");
            }

            // 验证用户权限（比较字符串类型的用户ID）
            if (!shoppingcartDO.getUserId().equals(String.valueOf(user.getId()))) {
                log.warn("用户[{}]尝试删除他人购物车记录[{}]", user.getId(), shoppingcartId);
                return Response.fail("PERMISSION_DENIED");
            }

            //-----------------这个地方和下面的地方应该分别放在两个循环内进行，因为这一整个操作要么都完成，要么都不完成，事务处理的方式-------------------
            //------------------------------------------------------------------------------------------------------------------------

            // 构建更新对象（保持与添加方法一致的builder风格）
            ShoppingCartDO updateDO = ShoppingCartDO.builder()
                    .id(shoppingcartId)
                    .isInOrder("1")  // 修改状态为已生成订单
                    .build();

            // 执行更新操作
            shoppingcartMapper.updateById(updateDO);


            String productId = shoppingcartDO.getProductId();
            Integer quantity = Integer.valueOf(shoppingcartDO.getQuantity());

            // 查询商品信息
            ProductDO product = productMapper.selectById(productId);
            if (product == null) {
                throw new RuntimeException("商品不存在: " + productId);
            }

            // 计算金额
            BigDecimal price = new BigDecimal(product.getProductPrice());
            BigDecimal total = price;

            // 创建订单记录
            OrderDO orderDO = OrderDO.builder()
                    .userId(String.valueOf(userId))
                    .productId(String.valueOf(productId))
                    .quantity(quantity)
                    .price(String.valueOf(total))
                    .goodId(shoppingcartDO.getGoodId())
                    .orderId(orderId)
                    .address(reqVO.getAddress())
                    .recipient(reqVO.getRecipient())
                    .isPaid(String.valueOf(0))
                    .build();
            orderMapper.insert(orderDO);


        }

        return Response.success();
    }

    private String generateOrderId(Integer userId) {
        return userId + "#" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
