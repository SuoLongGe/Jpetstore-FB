package com.webshop.user.model.vo.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqVO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;

    public @NotBlank(message = "用户名不能为空") String getUsername() {
        return username;
    }

    public @NotBlank(message = "密码不能为空") String getPassword() {
        return password;
    }
}

