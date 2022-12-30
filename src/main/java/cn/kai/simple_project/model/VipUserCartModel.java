package cn.kai.simple_project.model;

import cn.kai.simple_project.dto.Item;
import cn.kai.simple_project.common.utils.BigDecimalUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * vip用户处理逻辑
 * Author: chenKai
 * Date: 2022/12/30
 */
@Service("VipUserCart")
public class VipUserCartModel extends AbstractCartModel {

    @Override
    protected void processCouponPrice(long userId, Item item) {
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
    }

    @Override
    protected void processDeliveryPrice(long userId, Item item) {
        //运费是商品总价的10%
        item.setDeliveryPrice(
                BigDecimal.valueOf(
                        BigDecimalUtil.mul(
                                BigDecimalUtil.mul(
                                        item.getPrice().doubleValue(),(double)item.getQuantity()
                                ), 0.1)
                )
        );
    }
}
