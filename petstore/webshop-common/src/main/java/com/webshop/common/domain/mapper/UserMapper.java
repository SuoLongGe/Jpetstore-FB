package com.webshop.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.webshop.common.domain.dos.UserDO;

public interface UserMapper extends BaseMapper<UserDO> {
    default UserDO findByUsername(String username) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username);
        return selectOne(wrapper);
    }

    default UserDO findByGiteeId(String giteeId) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getGiteeId, giteeId);
        return selectOne(wrapper);
    }

    default int updatePasswordByUsername(String username, String password) {
        LambdaUpdateWrapper<UserDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserDO::getUsername, username);
        wrapper.set(UserDO::getPassword, password);
        return update(null, wrapper);
    }

    default int updateGiteeIdByUsername(String username, String giteeId) {
        LambdaUpdateWrapper<UserDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(UserDO::getGiteeId, giteeId);
        wrapper.eq(UserDO::getUsername, username);
        return update(null, wrapper);
    }

    //更新用户信息
    default int updateUserInfo(String username, String password, String name, String email, String address) {
        LambdaUpdateWrapper<UserDO> updateWrapper = new LambdaUpdateWrapper<>();

        // 设置更新的字段
        updateWrapper.set(UserDO::getName, name)
                .set(UserDO::getEmail, email)
                .set(UserDO::getAddress, address);

        // 设置查询条件，用户名和密码都需要匹配
        updateWrapper.eq(UserDO::getUsername, username);
        if(password != null && !password.equals("")) {
            updateWrapper.set(UserDO::getPassword, password);
        }

        // 执行更新操作
        return this.update(null, updateWrapper);
    }
}
