package com.hakunamatata.background.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * 基础配置类
 *
 * @author KaiKoo
 * @date 2020/5/10 15:03
 */
@Configuration
public class BaseConfiguration {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        // 包含 NON_NULL NON_ABSENT
        objectMapper.setSerializationInclusion(Include.NON_EMPTY);
        return objectMapper;
    }

    @Bean
    @LoadBalanced //配置负载均衡
    @SentinelRestTemplate //配置Sentinel
    public RestTemplate stringRestTemplate() {
        // 仅配置 StringHttpMessageConverter
        var stringRestTemplate = new RestTemplate(Arrays.asList(new StringHttpMessageConverter()));
        return stringRestTemplate;
    }

}
