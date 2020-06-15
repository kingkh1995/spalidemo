package com.hakunamatata.common.util;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hakunamatata.common.model.bean.BaseUtil;
import com.hakunamatata.common.model.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * sentinel公共处理工具类
 *
 * @author KaiKoo
 * @date 2020/5/11 23:40
 */
@Slf4j
public class SentinelExceptionUtil extends BaseUtil {

    private SentinelExceptionUtil() throws IllegalAccessException {
    }

    // SentinelResource资源的通用fallback处理，不在同一个类中时必须声明为static方法，返回值必须相同，参数列表为空，或者有一个Throwable参数
    public static Result defaultFallback(Throwable throwable) {
        if (BlockException.isBlockException(throwable)) {
            // 如果是BlockException，打印warn日志
            log.warn("throwable:{}", throwable);
            return Result.fail("resource default fallback");
        } else {
            //如果是其他异常，打印erro日志
            log.error("excption -> ", throwable);
            return Result.fail("third party service error");
        }
    }

    /**
     * resttemplate 通用限流降级处理 原方法为ClientHttpRequestInterceptor.intercept
     *
     * @see ClientHttpRequestInterceptor
     */
    public static ClientHttpResponse handleException(HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution, BlockException exception) {
        log.warn("sentinel blocked, uri:{}, type:{}", request.getURI(),
                exception.getRule().getClass().getSimpleName());
        var response = new SentinelClientHttpResponse("resttemplate blocked");
        return response;
    }

}
