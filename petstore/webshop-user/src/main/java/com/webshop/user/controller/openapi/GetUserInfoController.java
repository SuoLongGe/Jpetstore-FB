package com.webshop.user.controller.openapi;

import com.webshop.common.aspect.ApiOperationLog;
import com.webshop.common.utils.Response;
import com.webshop.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@ResponseBody
@Tag(name = "userApi")
@RequestMapping("/c/users")
public class GetUserInfoController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @ApiOperationLog(description = "userInfo")
    @Operation(summary = "userInfo")
    public Response getUserInfo() {
        return userService.getCurrentUserInfo();
    }
}
