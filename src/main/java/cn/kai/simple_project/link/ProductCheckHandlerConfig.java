package cn.kai.simple_project.link;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 处理器配置类
 * Author: chenKai
 * Date: 2023/1/5
 */
@Data
@Builder
public class ProductCheckHandlerConfig {
    /**
     * 处理器Bean名称
     */
    private String handler;
    /**
     * 下一个处理器
     */
    private ProductCheckHandlerConfig next;
    /**
     * 是否降级
     */
    private Boolean down = Boolean.FALSE;
}
