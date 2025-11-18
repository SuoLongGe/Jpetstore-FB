package com.webshop.user.service;

import com.webshop.common.utils.PageResponse;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.TestMapperReqVO;
import com.webshop.user.model.vo.TestPageMapperReqVO;
import com.webshop.user.model.vo.TestRESTfulReqVO;
import com.webshop.user.model.vo.TestReqVO;

public interface TestService {
    Response testApi(TestReqVO testReqVO);

    Response testApiError(TestReqVO testReqVO);

    Response testApiMapper(TestMapperReqVO testMapperReqVO);

    Response testApiRESTful(TestRESTfulReqVO testRESTfulReqVO);

    PageResponse testApiPage(TestPageMapperReqVO testPageMapperReqVO);

}
