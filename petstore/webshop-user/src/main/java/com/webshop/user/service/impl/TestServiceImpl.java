package com.webshop.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.webshop.common.domain.dos.CategoryDO;
import com.webshop.common.domain.mapper.CategoryMapper;
import com.webshop.common.utils.PageResponse;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.*;
import com.webshop.user.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.webshop.common.enums.ResponseCodeEnum.TEST_USE;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Response testApi(TestReqVO testReqVO) {

        TestRspVO testRspVO = TestRspVO.builder()
                .testRspId(testReqVO.getTestReqId())
                .testRspName(testReqVO.getTestReqName())
                .build();

        return Response.success(testRspVO);
    }

    @Override
    public Response testApiError(TestReqVO testReqVO) {

        return Response.fail(TEST_USE);
    }

    @Override
    public Response testApiMapper(TestMapperReqVO testMapperReqVO) {

        List<CategoryDO> categoryDOS = categoryMapper.searchAllCategoryByKey(testMapperReqVO.getSearchKey());

        List<TestMapperRspVO> vos = null;

        if(!CollectionUtils.isEmpty(categoryDOS)){
            vos = categoryDOS.stream()
                    .map(categoryDO -> TestMapperRspVO.builder()
                            .categoryId(categoryDO.getId())
                            .categoryName(categoryDO.getCategoryName())
                            .build())
                    .collect(Collectors.toList());
        }

        return Response.success(vos);
    }

    @Override
    public Response testApiRESTful(TestRESTfulReqVO testRESTfulReqVO) {
        TestRspVO testRspVO = TestRspVO.builder()
                .testRspId(testRESTfulReqVO.getTestreqid())
                .testRspName(testRESTfulReqVO.getTestReqName())
                .build();

        return Response.success(testRspVO);
    }

    @Override
    public PageResponse testApiPage(TestPageMapperReqVO testPageMapperReqVO) {
        // 获取当前页、以及每页需要展示的数据数量
        Long current = testPageMapperReqVO.getCurrent();
        Long size = testPageMapperReqVO.getSize();

        String searchKey = testPageMapperReqVO.getSearchkey();

        Page<CategoryDO> categoryDOPage = categoryMapper.searchPageCategoryByKey(current, size, searchKey);

        List<CategoryDO> categoryDOS = categoryDOPage.getRecords();

        List<TestPageMapperRspVO> vos = null;

        if(!CollectionUtils.isEmpty(categoryDOS)){
            vos = categoryDOS.stream()
                    .map(categoryDO -> TestPageMapperRspVO
                            .builder()
                            .id(categoryDO.getId())
                            .categoryName(categoryDO.getCategoryName())
                            .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(categoryDOPage, vos);
    }
}
