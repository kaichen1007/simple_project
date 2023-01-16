package cn.kai.simple_project.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sys_log
 */
@TableName(value ="sys_log")
@Data
public class SysLog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 目标类
     */
    private String targetClass;

    /**
     * 目标方法
     */
    private String targetMethod;

    /**
     * 操作系统
     */
    private String operateOs;

    /**
     * 浏览器
     */
    private String operateBrowser;

    /**
     * 客户端类型
     */
    private String clientType;

    /**
     * 客户端ip
     */
    private String clientIp;

    /**
     * 
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}