package com.webshop.user.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.webshop.jwt.config.JwtAuthenticationSecurityConfig;
import com.webshop.jwt.filter.TokenAuthenticationFilter;
import com.webshop.jwt.handler.RestAccessDeniedHandler;
import com.webshop.jwt.handler.RestAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig{

    @Autowired
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    @Autowired
    private RestAuthenticationEntryPoint authEntryPoint;
    @Autowired
    private RestAccessDeniedHandler deniedHandler;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 禁用 CSRF
                .formLogin(form -> form.disable()) // 禁用表单登录
                .with(jwtAuthenticationSecurityConfig, Customizer.withDefaults()) // 应用 JWT 认证配置
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/c/**").authenticated() // 需要认证的路径
                        .anyRequest().permitAll() // 其他路径允许访问
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPoint) // 未认证处理
                        .accessDeniedHandler(deniedHandler) // 权限不足处理
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 无状态会话
                )
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 添加 Token 认证过滤器

        return http.build();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }


}
