package com.webshop.user.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestReqVO {
    @NotNull(message = "用户id不能为空")
    private Integer TestReqId;

    @NotBlank(message = "用户名称不能为空")
    private String TestReqName;

}
