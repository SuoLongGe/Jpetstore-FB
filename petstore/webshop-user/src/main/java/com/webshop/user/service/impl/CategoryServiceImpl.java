package com.webshop.user.service.impl;

import com.webshop.common.domain.dos.CategoryDO;
import com.webshop.common.domain.mapper.CategoryMapper;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.category.CategoryVO;
import com.webshop.user.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Response getAllCategories() {
        try {
            List<CategoryDO> categories = categoryMapper.selectList(null);

            List<CategoryVO> vos = categories.stream().map(category ->
                    CategoryVO.builder()
                            .categoryid(String.valueOf(category.getId()))
                            .categoryname(category.getCategoryName())
                            .build()
            ).collect(Collectors.toList());

            return Response.success(vos);
        } catch (Exception e) {
            log.error("获取分类列表异常", e);
            return Response.fail("SYSTEM_ERROR");
        }
    }
}