package com.webshop.user.model.vo.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsRespVO_14 {
    private Integer  goodsid;
    private String goodsname;
    private String goodsprice;
    private String goodspicture;
    private Integer  categoryid;
}
