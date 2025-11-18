package com.webshop.user.service;

import com.webshop.common.utils.Response;
import com.webshop.user.model.vo.users.*;

public interface UserService {
    Response register(UserRegisterReqVO userRegisterReqVO);

    Response login(LoginReqVO loginReqVO);

    Response register(String username, RegisterReqVO vo);


    Response updateUserInfo(String name, String email, String address, UpdataReqVO_19 reqVO);

    Response checkUsernameIsSame(UsernameIsSameReqVO usernameIsSameReqVO);

    Response getCurrentUserInfo();
}
