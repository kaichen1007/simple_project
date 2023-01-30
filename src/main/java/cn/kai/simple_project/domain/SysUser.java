package cn.kai.simple_project.domain;

import cn.kai.simple_project.common.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BaseDomain{

    /**
     * 名称
     */
    private String name;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 地址
     */
    private String address;
}