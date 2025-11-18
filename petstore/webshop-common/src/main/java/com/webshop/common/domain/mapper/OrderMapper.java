package com.webshop.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.webshop.common.domain.dos.OrderDO;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderDO> {

    /**
     * 根据订单ID查询订单
     */
    default List<OrderDO> findByOrderId(String orderId ,Integer userId) {
        LambdaQueryWrapper<OrderDO> wrapper = new LambdaQueryWrapper<>();
        if (orderId != null && !orderId.isEmpty()) {
            wrapper.eq(OrderDO::getOrderId, orderId);
        }
        wrapper.eq(OrderDO::getUserId, userId);
        return selectList(wrapper);
    }
    default List<OrderDO> findOrdersByPage(int offset, int size, Integer userId) {
        // 第一步：先查出分页的 orderid 列表（distinct）
        List<String> orderIds = this.selectList(
                        new LambdaQueryWrapper<OrderDO>()
                                .select(OrderDO::getOrderId)
                                .eq(OrderDO::getUserId, userId)
                                .groupBy(OrderDO::getOrderId)
                                .orderByDesc(OrderDO::getOrderId)
                ).stream()
                .skip(offset)
                .limit(size)
                .map(OrderDO::getOrderId)
                .toList();

        // 第二步：再查出这些 orderid 对应的所有明细记录
        if (orderIds.isEmpty()) {
            return List.of();
        }

        LambdaQueryWrapper<OrderDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(OrderDO::getOrderId, orderIds);
        return this.selectList(wrapper);
    }

    default Long countOrders(Integer userId ) {
        LambdaQueryWrapper<OrderDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(OrderDO::getOrderId).groupBy(OrderDO::getOrderId); // 分组计数
        wrapper.eq(OrderDO::getUserId, userId);
        return (long) this.selectList(wrapper).size();
    }
    default int updateIsPaidByOrderId(String orderId, int isPaid) {
        LambdaUpdateWrapper<OrderDO> wrapper = new LambdaUpdateWrapper<>();
        if (orderId != null && !orderId.isEmpty()) {
            wrapper.eq(OrderDO::getOrderId, orderId);
        }
        wrapper.set(OrderDO::getIsPaid, isPaid);
        return this.update(null, wrapper);
    }

}
