package com.webshop.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.webshop.common.domain.dos.ShoppingCartDO;

public interface ShoppingCartMapper extends BaseMapper<ShoppingCartDO> {


    // 更新购物车数量
    default int updateShoppingCartQuantity(Long shoppingcartid, Integer quantity, Integer userId) {
        LambdaUpdateWrapper<ShoppingCartDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ShoppingCartDO::getId, shoppingcartid)
                .set(ShoppingCartDO::getQuantity, quantity)
                .eq(ShoppingCartDO::getUserId, userId);
        return update(null, updateWrapper);
    }

    // 查询购物车中的所有商品（分页）
    // 仅返回购物车基础信息，不含商品详情
    default Page<ShoppingCartDO> pageShoppingCart(Long current, Long size, Integer userId) {
        Page<ShoppingCartDO> page = new Page<>(current, size);

        LambdaQueryWrapper<ShoppingCartDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCartDO::getUserId, userId);
        queryWrapper.eq(ShoppingCartDO::getIsInOrder, 0);  // 未下单商品
        System.out.println(queryWrapper);
        return selectPage(page, queryWrapper);
    }


}
