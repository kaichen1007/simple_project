package cn.kai.simple_project.config.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: chenKai
 * Date: 2023/1/13
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 自定义sql注入器
     * @return
     */
    @Bean
    public InsertBatchSqlInjector insertBatchSqlInjector(){
        return new InsertBatchSqlInjector();
    }
}
