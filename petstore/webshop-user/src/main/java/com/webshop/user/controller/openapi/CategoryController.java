package com.webshop.user.controller.openapi;

import com.webshop.common.aspect.ApiOperationLog;
import com.webshop.common.utils.Response;
import com.webshop.user.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "CategoryApi")
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @ApiOperationLog(description = "获取所有分类")
    @Operation(summary = "获取所有分类")
    public Response getAllCategories() {
        return categoryService.getAllCategories();
    }
}