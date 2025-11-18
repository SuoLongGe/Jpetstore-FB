package com.webshop.user.model.vo.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoRspVO {
    private String username;
    private String name;
    private String email;
    private String address;
    private int bindgitee; // 0 表示未绑定，1 表示已绑定
}
