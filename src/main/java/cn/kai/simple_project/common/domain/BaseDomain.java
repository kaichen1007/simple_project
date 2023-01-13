package cn.kai.simple_project.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Author: chenKai
 * Date: 2023/1/13
 */
@Data
public class BaseDomain {


    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("uuid")
    private String uuid;

    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;
}
