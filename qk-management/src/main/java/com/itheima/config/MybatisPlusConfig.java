package com.itheima.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;

public class MybatisPlusConfig {
    /**
     * 分页插件（3.5.x 最新版写法）
     */

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        // 1) 创建MybatisPlusInterceptor拦截器对象
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();

        // 2) 添加分页拦截器（指定数据库类型，性能更好）
        PaginationInnerInterceptor paginationInnerInterceptor =  new PaginationInnerInterceptor(DbType.MYSQL);

        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);

        return mybatisPlusInterceptor;
    }
}
