package com.hakunamatata.sso.handler;

import com.hakunamatata.common.model.bean.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author KaiKoo
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 500 服务内部错误
    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Throwable t) {
        log.error("Server Error", t);
        return Result.fail("Server Error");
    }

    // 400
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleBadRequest(RuntimeException e) {
        log.error("exception", e);
        return Result.fail("Bad Request");
    }

    /*//401 未登录
    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleNotLogin(Exception e) {
        log.error("Exception:", e);
        return Result.fail("Not Login");
    }

    //403 权限不足
    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result handlePermissionDenied(Exception e) {
        log.error("Exception:", e);
        return Result.fail("Permission Denied");
    }

    //503 服务暂时不可用
    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Result handleServerUnavailable(Exception e) {
        log.error("Exception:", e);
        return Result.fail("Server Unavailable");
    }*/

}
