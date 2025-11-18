package com.webshop.user.controller.openapi;

import com.webshop.common.aspect.ApiOperationLog;
import com.webshop.common.matchparam.RequestVO;
import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.users.*;
import com.webshop.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@ResponseBody
@Tag(name = "userApi")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterReqVO
     * @return
     */
    @PostMapping("/users/{username}")
    @ApiOperationLog(description = "/users/{username}")
    @Operation(summary = "/users/{username}")
    public Response register(@RequestVO @Validated UserRegisterReqVO userRegisterReqVO) {
        return userService.register(userRegisterReqVO);
    }

//    @PostMapping("/users/{username}")
//    public Response<RegisterRespVO> register(@PathVariable String username,
//                                             @Valid @RequestBody RegisterReqVO registerReqVO){
//        return userService.register(username, registerReqVO);
//    }

    /*用户登录*/

    /**
     * 其实这个不是在这里执行的，这里相当于一个空的测试方法，真正的实现不在这里
     * @param loginReqVO
     * @param session
     * @return
     */
    @PostMapping("/tokens")
    public Response<LoginRespVO> login(@RequestBody @Valid LoginReqVO loginReqVO, HttpSession session) {
        // 调用 service 层的 login 方法，返回 Response<LoginRespVO>
        Response<LoginRespVO> result = userService.login(loginReqVO);
        // 检查登录是否成功
        if (result.isSuccess()) {
            // 将登录用户信息存入 session
            session.setAttribute("loginUser", result.getData());
        }
        System.out.println("login");
        // 返回响应，包含 LoginRespVO 数据
        return result;
    }

    //19.更新用户信息
    @PatchMapping("/c/users")
    public Response<Void> updateUserPassword(@RequestParam String name,
                                             @RequestParam String email,
                                             @RequestParam String address,
                                             @RequestBody UpdataReqVO_19 reqVO) {



        return userService.updateUserInfo(name,email,address,reqVO);
    }

    @GetMapping("/users/{username}")
    @ApiOperationLog(description = "/{username}")
    @Operation(summary = "/{username}")
    public Response checkUsernameIsSame(@RequestVO @Validated UsernameIsSameReqVO usernameIsSameReqVO) {
        return userService.checkUsernameIsSame(usernameIsSameReqVO);
    }

}
