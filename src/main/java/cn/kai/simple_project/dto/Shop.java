package cn.kai.simple_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附近商铺简单实现dto
 * Author: chenKai
 * Date: 2023/1/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    /**
     * 商铺id
     */
    private Integer shopId;

    /**
     * 商铺名称
     */
    private String shopName;

    /**
     * 距离
     */
    private String distance;

    /**
     * 经度
     */
    private String x;

    /**
     * 纬度
     */
    private String y;

    /**
     * 地区
     */
    private String area;
}
