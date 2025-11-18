package com.webshop.user.model.vo.goods;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsReqVO_15 {
    @NotNull(message = "关键词不能为空")
    private String searchkey;

    @NotNull(message = "当前页不能为空")
    private Long current;

    @NotNull(message = "每页大小不能为空")
    private Long size;
}
