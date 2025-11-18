package com.webshop.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.webshop.common.domain.dos.UserDO;
import com.webshop.common.domain.mapper.UserMapper;
import com.webshop.common.matchparam.RequestVO;
import com.webshop.common.utils.Response;
import com.webshop.jwt.utils.ResultUtil;
import com.webshop.user.model.vo.users.*;
import com.webshop.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;

import static com.webshop.common.enums.ResponseCodeEnum.CAPTCHA_ERROR;
import static com.webshop.common.enums.ResponseCodeEnum.REGISTER_FAIL;
@Service
@Slf4j
@CrossOrigin  //跨域
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @SuppressWarnings("unused")
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    @SuppressWarnings("unused")
    private RedisTemplate<String, String> redisTemplateString;


    /**
     * 注册用户
     * @param userRegisterReqVO
     * @return
     */
    @Override
    public Response register(UserRegisterReqVO userRegisterReqVO) {

        /*
         * ===== 注册验证码校验（已注释，便于接口测试；需要时恢复） =====
         *
         * String captcha = userRegisterReqVO.getCaptcha();
         * String cachedCaptcha = redisTemplateString.opsForValue().get("imageCode");
         * if (cachedCaptcha == null) {
         *     return Response.fail(CAPTCHA_ERROR);
         * }
         * boolean isValid = cachedCaptcha.equalsIgnoreCase(captcha);
         * if (isValid) {
         *     redisTemplateString.delete("imageCode");
         * } else {
         *     return Response.fail(CAPTCHA_ERROR);
         * }
         */


        System.out.println(userRegisterReqVO.getUsername());

        UserDO byUsername = userMapper.findByUsername(userRegisterReqVO.getUsername());

        int register = 0;

        if(byUsername != null) {
            //已经存在，因此不注册
        }else{
            //不存在已有用户，因此进行注册
            String password = userRegisterReqVO.getPassword();

            // 加密密码
            String encodePassword = passwordEncoder.encode(password);

            UserDO userDO = UserDO.builder()
                    .username(userRegisterReqVO.getUsername())
                    .password(encodePassword)
                    .name(userRegisterReqVO.getName())
                    .email(userRegisterReqVO.getEmail())
                    .address(userRegisterReqVO.getAddress())
                    .build();

            register = userMapper.insert(userDO);

        }

        return register==1?Response.success():Response.fail(REGISTER_FAIL);
    }

    //用户注册
    public Response register(String username, RegisterReqVO vo){
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserDO temp = userMapper.selectOne(queryWrapper);
        if (temp != null) {
            return Response.fail("用户名称已存在");
        }

        UserDO user = new UserDO();
        user.setUsername(username);
        user.setPassword(vo.getPassword());
        user.setName(vo.getName());
        user.setEmail(vo.getEmail());
        user.setAddress(vo.getAddress());

        int result = userMapper.insert(user);
        if (result == 1) {
            RegisterRespVO respVO = new RegisterRespVO();
            return Response.success(respVO);
        } else {
            return Response.fail("服务器异常");
        }
    }


    //用户登录
    public Response login(LoginReqVO loginReqVO) {
        String username = loginReqVO.getUsername();
        String password = loginReqVO.getPassword();

        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        queryWrapper.eq("is_admin", 1);

        UserDO user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        } else {
            // 清除密码
            user.setPassword(null);

            // 创建 LoginRespVO 对象
            LoginRespVO loginRespVO = new LoginRespVO();

            loginRespVO.setToken("示例tokens");

            // 生成并设置 Token
//            String token = generateToken(user); // 根据需要生成Token
//            loginRespVO.setToken(token);

            return Response.success(loginRespVO);
        }
    }


    //更新用户信息
    @Override
    public Response updateUserInfo(String name, String email, String address, UpdataReqVO_19 reqVO) {

        //通过就jwt用户鉴权获取username***************************************************************************
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        String password = passwordEncoder.encode(reqVO.getPassword());

        //username = "st" + username;通过这样来修改传入的参数username
        int rows = userMapper.updateUserInfo(username, password, name, email, address);
        if(rows > 0) {
            return Response.success(); // 更新成功，data 为 null
        }
        else{
            return Response.fail( "更新用户密码失败");
        }
    }

    public Response checkUsernameIsSame(@RequestVO @Validated UsernameIsSameReqVO usernameIsSameReqVO) {

        String username = usernameIsSameReqVO.getUsername();
        // 空值校验
        if (username == null || username.trim().isEmpty()) {
            return Response.fail("用户名不能为空");
        }
        // 查询数据库是否存在该用户名
        UserDO user = userMapper.findByUsername(username);

        // 判断是否存在，存在则为1，否则为0
        int isSame = (user != null) ? 1 : 0;

        UsernameIsSameRspVO rspVO = UsernameIsSameRspVO.builder()
                .issame(isSame)
                .build();

        return Response.success(rspVO);
    }
    @Override
    public Response getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserDO user = userMapper.findByUsername(username);
        if (user == null) {
            return Response.fail("USER_NOT_FOUND");
        }

        UserInfoRspVO vos = UserInfoRspVO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getAddress())
                .bindgitee(user.getGiteeId() == null ? 0 : Integer.parseInt(user.getGiteeId()))
                .build();

        return Response.success(vos);
    }

}
