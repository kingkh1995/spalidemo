package com.hakunamatata.background.controller;

import com.hakunamatata.background.client.RestClient;
import com.hakunamatata.background.config.InfoConfiguration;
import com.hakunamatata.common.model.bean.Result;
import com.hakunamatata.common.model.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * BaseController
 *
 * @author KaiKoo
 * @date 2020/4/22 20:41
 */
@Slf4j
@RefreshScope //使用@RefreshScope和@Value注解开启配置自动刷新
@RestController
public class BaseController {

    @Autowired
    private RestClient restClient;

    @Value("${switch}")
    private Boolean healthCheckSwitch;

    @Autowired
    private InfoConfiguration configuration;

    @GetMapping("/check")
    public Result<String> health() {
        log.info("checking health ...");
        if (!healthCheckSwitch) {
            return Result.fail("health check closed!");
        }
        return Result.success("Im healthy now!");
    }

    @GetMapping("/")
    public String show() {
        return configuration.show();
    }

    @GetMapping("/user/{id}")
    public Result<UserDTO> userInfo(@PathVariable Long id) {
        log.info("user/{}", id);
        return restClient.userInfo(id);
    }

}
