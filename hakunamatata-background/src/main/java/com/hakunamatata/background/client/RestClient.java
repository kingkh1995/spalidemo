package com.hakunamatata.background.client;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hakunamatata.common.model.bean.Result;
import com.hakunamatata.common.model.dto.UserDTO;
import com.hakunamatata.common.util.SentinelExceptionUtil;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 封装client
 *
 * @author KaiKoo
 * @date 2020/5/12 21:55
 */
@Service
@Slf4j
public class RestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @SentinelResource(value = "userInfo", blockHandler = "blockHandler", fallback = "fallback", defaultFallback = "defaultFallback", fallbackClass = SentinelExceptionUtil.class)
    public Result<UserDTO> userInfo(Long id) {
        UserDTO dto = null;
        try {
            var response = restTemplate
                    .getForObject("http://sso-provider/user/{1}", String.class, id);
            // 动态json转化为JsonNode处理
            var jsonNode = objectMapper.readTree(response);
            var code = objectMapper.treeToValue(jsonNode.findValue("code"), Integer.class);
            if (!Objects.equals(0, code)) {
                var message = objectMapper.treeToValue(jsonNode.findValue("message"), String.class);
                throw new IllegalArgumentException(message);
            }
            dto = objectMapper.treeToValue(jsonNode.findValue("data"), UserDTO.class);
        } catch (Exception e) {
            //todo. 异常处理
            log.error("rest api error", e);
        }
        return Result.success(dto);
    }

    // blockHandler方法，处理BlockException，参数列表和返回值必须相同，并且最后必须添加一个BlockException参数，如不在同一个类中必须声明为静态方法
    public Result<UserDTO> blockHandler(Long id, BlockException blockException) {
        log.warn("id:{}, blockException:{}", id, blockException);
        return Result.fail("userInfo blockHandler");
    }

    // fallback方法，处理所有异常，参数列表和返回值必须相同，可以添加一个Throwable参数，如不在同一个类中必须声明为静态方法
    public Result<UserDTO> fallback(Long id, Throwable throwable) {
        log.warn("d:{}, throwable:{}", id, throwable);
        return Result.fail("userInfo fallback");
    }

}
