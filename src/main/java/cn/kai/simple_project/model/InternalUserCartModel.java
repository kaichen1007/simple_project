package cn.kai.simple_project.model;

import cn.kai.simple_project.dto.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 内部用户处理逻辑
 * Author: chenKai
 * Date: 2022/12/30
 */
@Service(value = "InternalUserCart")
public class InternalUserCartModel extends AbstractCartModel{
    @Override
    protected void processCouponPrice(long userId, Item item) {
        item.setCouponPrice(BigDecimal.ZERO);
    }

    @Override
    protected void processDeliveryPrice(long userId, Item item) {
        item.setDeliveryPrice(BigDecimal.ZERO);
    }
}
