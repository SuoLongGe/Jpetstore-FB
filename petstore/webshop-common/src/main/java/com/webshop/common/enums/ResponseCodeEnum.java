package com.webshop.common.enums;

import com.webshop.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jiejie
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // --------通用异常-----------
    SYSTEM_ERROR("10000", "未知错误"),
    PARAM_NOT_VALID("10001", "参数错误"),

    // ----------业务异常状态码-----------
    TEST_USE("20000", "测试使用"),
    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),
    UNAUTHORIZED("20002", "无访问权限，请先登录！"),
    USERNAME_NOT_FOUND("20003", "该用户不存在"),
    FORBIDDEN("20004", "演示账号仅支持查询操作！"),
    CATEGORY_NAME_IS_EXISTED("20005", "该分类已存在，请勿重复添加！"),
    TAG_NOT_EXISTED("20007", "该标签不存在"),
    LOGIN_FAIL("20008", "登录失败"),
    REGISTER_FAIL("20009","注册失败，已存在该用户"),
    NEED_BIND_ACCOUNT("20010","登录失败，请先绑定账号"),
    CAPTCHA_ERROR("20011","验证码错误")
    ;

    private String errorCode;

    private String errorMessage;

}
