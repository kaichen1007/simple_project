package cn.kai.simple_project.app;

import cn.kai.simple_project.dto.Cart;
import cn.kai.simple_project.dto.Item;
import cn.kai.simple_project.common.utils.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * vip用户计算逻辑
 * Author: chenKai
 * Date: 2022/12/30
 */
public class VipUserCart {
    public Cart process(long userId, Map<Long, Integer> items){
        Cart cart = new Cart();

        //把购物车map转换为商品列表
        List<Item> itemList = new ArrayList<>();
        items.entrySet().forEach(entry->{
            Item item = new Item();
            item.setId(entry.getKey());
//            item.setPrice(Db.getItemPrice(entry.getKey()));//根据数据库拿到价格
            item.setPrice(new BigDecimal("34"));
            item.setQuantity(entry.getValue());
            itemList.add(item);
        });
        cart.setItems(itemList);

        //处理运费和商品优惠
        itemList.forEach(item->{
            //运费是商品总价的10%
            item.setDeliveryPrice(
                    BigDecimal.valueOf(BigDecimalUtil.mul(BigDecimalUtil.mul(item.getPrice().doubleValue(),(double)item.getQuantity()), 0.1))
            );
            //购买两件以上相同商品，第三件开始享受一定折扣
            if (item.getQuantity() > 2){
                item.setCouponPrice(
                        item.getPrice().multiply(
                                BigDecimal.valueOf(
                                        100 - 30 //模拟用户表优惠金额
                                ).divide(new BigDecimal("100"))
                        )
                );
            }else {
                item.setCouponPrice(BigDecimal.ZERO);
            }
        });

        //计算购物车总价格
        cart.setTotalItemPrice(cart.getCarTotalItemPrice(itemList));

        //计算总运费
        cart.setTotalDeliveryPrice(cart.getCartTotalDeliveryPrice(itemList));

        //计算总优惠
        cart.setTotalDiscount(cart.getCartTotalDiscount(itemList));

        //计算应付价格 商品总价+总运费-优惠总价
        cart.setPayPrice(cart.getCartPayPrice(itemList));

        return cart;
    }

}
