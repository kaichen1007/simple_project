package cn.kai.simple_project.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: chenKai
 * Date: 2023/1/5
 */
@Data
@Builder
public class ProductVO {
    /**
     * 商品SKU，唯一
     */
    private Long skuId;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品图片路径
     */
    private String Path;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;
}
