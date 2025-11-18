package com.webshop.user.model.vo.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsernameIsSameRspVO {
    /**
     * 0 表示用户名不存在（可用），1 表示用户名已存在（重复）
     */
    private Integer issame;
}
