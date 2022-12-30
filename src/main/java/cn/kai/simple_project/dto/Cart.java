package cn.kai.simple_project.dto;

import cn.kai.simple_project.utils.BigDecimalUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * Author: chenKai
 * Date: 2022/12/30
 */
@Data
public class Cart {

    /**
     * 商品清单
     */
    private List<Item> items = new ArrayList<>();

    /**
     * 总优惠
     */
    private BigDecimal totalDiscount;

    /**
     * 商品总价
     */
    private BigDecimal totalItemPrice;

    /**
     * 总运费
     */
    private BigDecimal totalDeliveryPrice;

    /**
     * 应付总价
     */
    private BigDecimal payPrice;


    /**
     * 计算总价格
     * @param items
     * @return
     */
    public BigDecimal getCarTotalItemPrice(List<Item> items){
        return  BigDecimal.valueOf(
                items.stream().map(
                        item -> BigDecimalUtil.mul(item.getPrice().doubleValue(), item.getQuantity())
                ).reduce(0.0, BigDecimalUtil::add)
        );
    }


}
