package com.webshop.user.controller;

import com.webshop.common.aspect.ApiOperationLog;
import com.webshop.common.matchparam.RequestVO;
import com.webshop.common.utils.PageResponse;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.TestMapperReqVO;
import com.webshop.user.model.vo.TestPageMapperReqVO;
import com.webshop.user.model.vo.TestRESTfulReqVO;
import com.webshop.user.model.vo.TestReqVO;
import com.webshop.user.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@ResponseBody
@Tag(name = "TestApi")
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 测试Controller是否可用
     * @param testReqVO
     * @return
     */
    @PostMapping("/test")
    @ApiOperationLog(description = "test")
    @Operation(summary = "test")
    public Response testApi(@RequestBody @Validated TestReqVO testReqVO) {
        return testService.testApi(testReqVO);
    }

    /**
     * 测试全局异常处理
     * @param testReqVO
     * @return
     */
    @PostMapping("/testerror")
    @ApiOperationLog(description = "testerror")
    @Operation(summary = "testerror")
    public Response testApiError(@RequestBody @Validated TestReqVO testReqVO) {
        return testService.testApiError(testReqVO);
    }

    /**
     * 测试数据库链接
     * @param testMapperReqVO
     * @return
     */
    @PostMapping("/testmapper")
    @ApiOperationLog(description = "testmapper")
    @Operation(summary = "testmapper")
    public Response testApiMapper(@RequestBody @Validated TestMapperReqVO testMapperReqVO) {
        return testService.testApiMapper(testMapperReqVO);
    }

    /**
     * 测试用于RESTful的自定义注解RequestVO是否可用
     * @param testRESTfulReqVO
     * @return
     */
    @PostMapping("/test/testreqid/{testreqid}")
    @ApiOperationLog(description = "/test/testreqid/{id}")
    @Operation(summary = "/test/testreqid/{id}")
    public Response testApiRESTful(@RequestVO @Validated TestRESTfulReqVO testRESTfulReqVO) {
        return testService.testApiRESTful(testRESTfulReqVO);
    }

    /**
     * 测试带JWT检测的url是否可用，/c
     * @param testRESTfulReqVO
     * @return
     */
    @PostMapping("/c/test/testreqid/{testreqid}")
    @ApiOperationLog(description = "/c/test/testreqid/{id}")
    @Operation(summary = "/c/test/testreqid/{id}")
    public Response testApiRESTfulToken(@RequestVO @Validated TestRESTfulReqVO testRESTfulReqVO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        return testService.testApiRESTful(testRESTfulReqVO);
    }

    /**
     * 测试分页功能
     * @param testPageMapperReqVO
     * @return
     */
    @GetMapping("/test/list/current/{current}/size/{size}")
    @ApiOperationLog(description = "/test/list/current/{current}/size/{size}")
    @Operation(summary = "/test/list/current/{current}/size/{size}")
    public PageResponse testApiPageSeachKey(@RequestVO @Validated TestPageMapperReqVO testPageMapperReqVO){
        System.out.println(testPageMapperReqVO.getCurrent());
        return testService.testApiPage(testPageMapperReqVO);
    }

}
