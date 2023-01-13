package cn.kai.simple_project.common.domain;

import lombok.Data;

import java.util.Date;

/**
 * Author: chenKai
 * Date: 2023/1/13
 */
@Data
public class BaseDomain {
    private String uuid;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

    private String createUser;

    private String updateUser;
}
