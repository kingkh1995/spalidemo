package com.hakunamatata.sso;

import com.hakunamatata.sso.bean.CustomBinding;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableBinding({CustomBinding.class}) //加载自定义binding配置
@EnableDiscoveryClient
@DubboComponentScan("com.hakunamatata.sso.controller") //服务提供者需要配置包扫描
@MapperScan("com.hakunamatata.sso.mapper")
@SpringBootApplication
public class HakunamatataSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HakunamatataSsoApplication.class, args);
    }

}
