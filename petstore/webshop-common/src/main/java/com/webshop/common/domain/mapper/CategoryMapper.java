package com.webshop.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.webshop.common.domain.dos.CategoryDO;

import java.util.List;

public interface CategoryMapper extends BaseMapper<CategoryDO> {

    default List<CategoryDO> searchAllCategoryByKey(String keyword) {
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        if(keyword != null && !"".equals(keyword)) {
            wrapper.like(CategoryDO::getCategoryName, keyword);
        }
        return selectList(wrapper);
    }

    default Page<CategoryDO> searchPageCategoryByKey(Long current, Long size, String keyword) {
        Page<CategoryDO> page = new Page<>(current, size);
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        if(keyword != null && !"".equals(keyword)) {
            wrapper.like(CategoryDO::getCategoryName, keyword);
        }
        return selectPage(page, wrapper);
    }
}
