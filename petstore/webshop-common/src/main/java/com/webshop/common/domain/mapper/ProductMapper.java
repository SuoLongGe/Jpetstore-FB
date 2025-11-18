package com.webshop.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.webshop.common.domain.dos.ProductDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProductMapper extends BaseMapper<ProductDO> {

    default ProductDO findById(Integer id) {
        LambdaQueryWrapper<ProductDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDO::getId, id);
        return selectOne(queryWrapper);
    }

    // 根据产品ID查询商品信息
    @Select("SELECT * FROM product WHERE id = #{productId}")
    ProductDO getProductInfoById(@Param("productId") Integer productId);


}
