package cn.kai.simple_project.app.bank;

import cn.kai.simple_project.common.comenum.DataType;
import cn.kai.simple_project.config.annot.BankApi;
import cn.kai.simple_project.config.annot.BankApiField;
import lombok.Data;

/**
 * 创建用户接口
 * Author: chenKai
 * Date: 2022/12/31
 */
@BankApi(url = "/bank/createUser", desc = "行内创建用户接口")
@Data
public class CreateUserApi extends AbstractApi{
    @BankApiField(order = 1, type = "S", length = 10)
    private String name;

    @BankApiField(order = 2, type = "S", length = 18)
    private String identity;

    @BankApiField(order = 4, type = "S", length = 11)
    private String mobile;

    @BankApiField(order = 3, type = "N", length = 5)
    private int age;
}
