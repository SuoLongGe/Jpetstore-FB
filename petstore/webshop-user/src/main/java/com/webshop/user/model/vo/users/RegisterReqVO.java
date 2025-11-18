package com.webshop.user.model.vo.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterReqVO {
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "昵称不能为空")
    private String name;
    @Email(message = "邮箱格式不正确")
    private String email;
    @NotBlank(message = "地址不能为空")
    private String address;

    public String getUsername() {
        return username;
    }
}
