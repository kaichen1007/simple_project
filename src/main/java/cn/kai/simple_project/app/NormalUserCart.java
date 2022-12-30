package cn.kai.simple_project.app;

import cn.kai.simple_project.dto.Cart;
import cn.kai.simple_project.dto.Item;
import cn.kai.simple_project.utils.BigDecimalUtil;
import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 普通用户购物车操作类
 * Author: chenKai
 * Date: 2022/12/30
 */
public class NormalUserCart {
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
            //普通用户无优惠
            item.setCouponPrice(BigDecimal.ZERO);
        });

        //计算购物车总价格
        cart.setTotalItemPrice(cart.getCarTotalItemPrice(itemList));

        return null;
    }
}
