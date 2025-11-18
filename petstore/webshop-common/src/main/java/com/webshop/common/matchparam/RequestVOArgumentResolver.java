package com.webshop.common.matchparam;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@Component
public class RequestVOArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private Validator validator; // 注入 Validator


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestVO.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String, String> pathVariables =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Class<?> paramType = parameter.getParameterType();
        Object instance = paramType.getDeclaredConstructor().newInstance();

        for (Field field : paramType.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();

            // 1. PathVariable 优先
            if (pathVariables != null && pathVariables.containsKey(fieldName)) {
                String pathValue = pathVariables.get(fieldName);
                field.set(instance, convertValue(pathValue, field.getType()));
                continue;
            }

            // 2. RequestParam 查询参数
            String paramValue = webRequest.getParameter(fieldName);
            if (paramValue != null) {
                field.set(instance, convertValue(paramValue, field.getType()));
            }
        }

        // 3. 请求体（仅当 content-type 是 application/json 或 form-urlencoded）
        if (request.getContentType() != null &&
                request.getContentType().toLowerCase().contains("application/json") &&
                request.getContentLength() > 0) {
            Object bodyObj = objectMapper.readValue(request.getInputStream(), paramType);
            // 用 bodyObj 补充未被赋值的字段（防止被 path/query 填过）
            for (Field field : paramType.getDeclaredFields()) {
                field.setAccessible(true);
                Object currentValue = field.get(instance);
                if (currentValue == null) {
                    Field bodyField = paramType.getDeclaredField(field.getName());
                    bodyField.setAccessible(true);
                    Object valueFromBody = bodyField.get(bodyObj);
                    if (valueFromBody != null) {
                        field.set(instance, valueFromBody);
                    }
                }
            }
        }

        // 校验对象（启用 @Validated 的效果）
        Set<ConstraintViolation<Object>> violations = validator.validate(instance);
        if (!violations.isEmpty()) {
            // 如果校验失败，创建一个 BindingResult 并将错误信息添加进去
            BindingResult bindingResult = new BeanPropertyBindingResult(instance, parameter.getParameterName());
            for (ConstraintViolation<Object> violation : violations) {
                // 将 ConstraintViolation 的错误信息添加到 BindingResult
                bindingResult.rejectValue(
                        violation.getPropertyPath().toString(),
                        violation.getMessage(),
                        violation.getMessage()
                );
            }

            // 抛出 MethodArgumentNotValidException 并传递 BindingResult
            throw new MethodArgumentNotValidException(parameter, bindingResult);
        }

        return instance;
    }

    // 类型转换
    private Object convertValue(String value, Class<?> targetType) {
        if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        }
        return value;
    }

}
