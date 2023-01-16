package cn.kai.simple_project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Author: chenKai
 * Date: 2023/1/16
 */
@Data
@TableName(value ="visit_stats")
public class VisitStatus {

    /**
     * 商品
     */
    private Long productId;

    /**
     * 访问时间
     */
    private String visitTime;


    /**
     * 1是新访客，0是老访客
     */
    private Integer isNew;

    /**
     * 访问量
     */
    private Integer pv;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

//    /**
//     * ========度量值=========
//     */
//    private Long pvCount=0L;
//
//    /**
//     * 时间的字符串映射，天、小时
//     */
//    private String dateTimeStr;
}
