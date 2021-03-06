package io.github.talelin.latticy.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSimplifyVO {

    private Integer id;

    private String orderNo;

    private Integer userId;

    private BigDecimal totalPrice;

    private Integer totalCount;

    private String snapImg;

    private String snapTitle;

    private String prepayId;

    private BigDecimal finalTotalPrice;

    private Integer status;

    private Boolean expired;

}
