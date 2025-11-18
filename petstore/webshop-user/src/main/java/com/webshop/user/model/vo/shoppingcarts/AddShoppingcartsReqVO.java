package com.webshop.user.model.vo.shoppingcarts;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddShoppingcartsReqVO {

    String productid;

    String quantity;
}
