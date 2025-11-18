package com.webshop.user.controller.openapi;

import com.webshop.common.domain.dos.UserDO;
import com.webshop.common.domain.mapper.UserMapper;
import com.webshop.common.utils.Response;
import com.webshop.jwt.model.LoginRspVO;
import com.webshop.jwt.utils.JwtTokenHelper;
import com.webshop.jwt.utils.ResultUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

import static com.webshop.common.enums.ResponseCodeEnum.NEED_BIND_ACCOUNT;

@Controller
@Slf4j
@ResponseBody
public class GiteeOAuthController {

    @Value("${spring.security.oauth2.client.registration.gitee.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.gitee.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.gitee.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.gitee.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.registration.gitee.authorize-uri}")
    private String authorizeUri;

    @Value("${spring.security.oauth2.client.registration.gitee.user-info-uri}")
    private String userInfoUri;

    @Value("${spring.security.oauth2.client.registration.gitee.redirect-uri-bind}")
    private String redirectUriBind;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 这个是登录处理回调的，需要访问https://gitee.com/oauth/authorize?client_id=26c76ab8dd757ae7abf4f94405ab8389cf2c0967215de16fd453488052ba39ca&redirect_uri=http://localhost:8080/oauth/gitee&response_type=code
     * @param code
     * @param response
     * @throws IOException
     */
    @GetMapping("/oauth/gitee")
    public void oAuthCallback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

        System.out.println("code:" + code);

        Map userInfo = getGiteeUserInfoBycode(code,redirectUri);
        String giteeUsername = (String) userInfo.get("login");
        String giteeId = String.valueOf(userInfo.get("id"));


        UserDO byGiteeId = userMapper.findByGiteeId(giteeId);

        if(byGiteeId!=null){
            String username = byGiteeId.getUsername();
            // 根据username用户名生成JWT
            String jwtToken = jwtTokenHelper.generateToken(username);

            // 返回 Token
            LoginRspVO loginRspVO = LoginRspVO.builder().token(jwtToken).build();

            ResultUtil.ok(response, Response.success(loginRspVO));
        }else{
            ResultUtil.fail(response,Response.fail(NEED_BIND_ACCOUNT));
        }

    }

    /**
     * 这个给是用户绑定账号的https://gitee.com/oauth/authorize?client_id=26c76ab8dd757ae7abf4f94405ab8389cf2c0967215de16fd453488052ba39ca&redirect_uri=http://localhost:8080/oauth/gitee/bind&response_type=code&state=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6ImppZWppZSIsImlhdCI6MTc0MzkyMDY3OCwiZXhwIjoxNzQ0MDA3MDc4fQ.o3l1lblCXzOFVv5iS5smwwVQMHr-QU-ZrKBA6vOH7up2vdFUsnuMTB0MzSX3otJ2xXZoBEuOQeIapBo0ljAnDg
     * @param code
     * @param response
     * @throws IOException
     */
    @GetMapping("/c/oauth/gitee/bind")
    public void oAuthBindGitee(@RequestParam("code") String code, HttpServletResponse response)throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        Map userInfo = getGiteeUserInfoBycode(code,redirectUriBind);
        String giteeId = String.valueOf(userInfo.get("id"));

        userMapper.updateGiteeIdByUsername(username, giteeId);

        ResultUtil.ok(response, Response.success());

    }

    private Map getGiteeUserInfoBycode(String code,String redirectUri) throws IOException {
        // 获取access_token
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenUri, request, Map.class);

        String accessToken = (String) tokenResponse.getBody().get("access_token");

        // 使用access_token获取用户信息
        String userApiUrl = userInfoUri + "?access_token=" + accessToken;
        Map userInfo = restTemplate.getForObject(userApiUrl, Map.class);

        return userInfo;
    }


}
