package cn.kai.simple_project.common.domain;

import cn.kai.simple_project.app.bank.CreateUserApi;
import cn.kai.simple_project.app.bank.PayApi;
import lombok.Data;

/**
 * 公共请求类
 * Author: chenKai
 * Date: 2022/12/30
 */
@Data
public class CommonRequestDomain {

    private long id;

    private String type;

    private CreateUserApi createUserApi;

    private PayApi payApi;
}
