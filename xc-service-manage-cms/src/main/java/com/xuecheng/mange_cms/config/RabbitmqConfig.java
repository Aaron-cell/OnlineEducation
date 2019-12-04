package com.xuecheng.mange_cms.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 码农界的小学生
 * @description:
 * @title: RabbitmqConfig
 * @projectName xc-edu
 * @description: TODO
 * @date 2019/11/8 20:27
 */
@Configuration
public class RabbitmqConfig {
    //交换机的名称
    public static final String  EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";
    /**
     * 容器中注入的bean名为ex_routing_cms_postpage
     * 交换机名为ex_routing_cms_postpage 类型为direct 即路由模式
     * durable：是否持久 默认true
     * @return
     */
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }
}
