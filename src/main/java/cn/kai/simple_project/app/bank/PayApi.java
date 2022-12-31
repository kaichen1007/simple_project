package cn.kai.simple_project.app.bank;

import cn.kai.simple_project.config.annot.BankApi;
import cn.kai.simple_project.config.annot.BankApiField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: chenKai
 * Date: 2022/12/31
 */
@Data
@BankApi(url = "/bank/pay", desc = "支付接口")
public class PayApi extends AbstractApi{

    @BankApiField(order = 1, type = "N", length = 20)
    private long userId;

    @BankApiField(order = 2, type = "M", length = 10)
    private BigDecimal amount;
}
