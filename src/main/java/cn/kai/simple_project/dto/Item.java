package cn.kai.simple_project.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品类
 * Author: chenKai
 * Date: 2022/12/30
 */
@Data
public class Item {

    private long id;

    /**
     * 商品数量
     */
    private int quantity;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 商品优惠
     */
    private BigDecimal couponPrice;
    
    /**
     * 商品运费
     */
    private BigDecimal deliveryPrice;
}
