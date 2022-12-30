package cn.kai.simple_project.model;

import cn.kai.simple_project.dto.Item;
import cn.kai.simple_project.common.utils.BigDecimalUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 普通用户处理逻辑
 * Author: chenKai
 * Date: 2022/12/30
 */
@Service(value = "NormalUserCart")
public class NormalUserCartModel extends AbstractCartModel{
    @Override
    protected void processCouponPrice(long userId, Item item) {
        //无优惠
        item.setCouponPrice(BigDecimal.ZERO);
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
