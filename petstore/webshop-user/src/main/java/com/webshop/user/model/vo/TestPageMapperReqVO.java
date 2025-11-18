package com.webshop.user.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestPageMapperReqVO {
    /**
     * 搜索的关键词
     */
    private String searchkey;

    private Long current = 1L;

    private Long size = 10L;
}
