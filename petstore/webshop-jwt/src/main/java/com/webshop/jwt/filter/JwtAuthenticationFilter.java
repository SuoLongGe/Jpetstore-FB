package com.webshop.jwt.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.webshop.jwt.exception.UsernameOrPasswordNullException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    @SuppressWarnings("unused")
    private final DefaultKaptcha defaultKaptcha;

    @SuppressWarnings("unused")
    private final RedisTemplate<String, String> redisTemplateString;



    /**
     * 指定用户登录的访问地址
     */
    public JwtAuthenticationFilter( DefaultKaptcha defaultKaptcha, RedisTemplate<String, String> redisTemplateString) {
        super(new AntPathRequestMatcher("/tokens", "POST"));
        this.defaultKaptcha = defaultKaptcha;
        this.redisTemplateString = redisTemplateString;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        ObjectMapper mapper = new ObjectMapper();
        // 解析提交的 JSON 数据
        JsonNode jsonNode = mapper.readTree(request.getInputStream());
        JsonNode usernameNode = jsonNode.get("username");
        JsonNode passwordNode =  jsonNode.get("password");

        // 判断用户名、密码是否为空
        if (Objects.isNull(usernameNode) || Objects.isNull(passwordNode)
                || StringUtils.isBlank(usernameNode.textValue()) || StringUtils.isBlank(passwordNode.textValue())) {
            throw new UsernameOrPasswordNullException("用户名或密码不能为空");
        }

        /*
         * ===== 验证码校验（已注释，便于接口测试；需要启用时取消注释） =====
         *
         * JsonNode captchaNode = jsonNode.get("captcha");
         *
         * if (Objects.isNull(captchaNode) || StringUtils.isBlank(captchaNode.textValue())) {
         *     ResultUtil.fail(response, Response.fail(CAPTCHA_ERROR));
         *     return null;
         * }
         *
         * String cachedCaptcha = redisTemplateString.opsForValue().get("imageCode");
         * if (cachedCaptcha == null) {
         *     ResultUtil.fail(response, Response.fail(CAPTCHA_ERROR));
         *     return null;
         * }
         *
         * boolean isValid = cachedCaptcha.equalsIgnoreCase(captchaNode.textValue());
         * if (isValid) {
         *     redisTemplateString.delete("imageCode");
         * } else {
         *     ResultUtil.fail(response, Response.fail(CAPTCHA_ERROR));
         *     return null;
         * }
         */

        String username = usernameNode.textValue();
        String password = passwordNode.textValue();


        // 将用户名、密码封装到 Token 中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
