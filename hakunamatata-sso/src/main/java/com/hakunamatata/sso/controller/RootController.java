package com.hakunamatata.sso.controller;

import com.hakunamatata.common.model.bean.Result;
import com.hakunamatata.common.util.ShiroUtil;
import com.hakunamatata.sso.bean.UserSsoReq;
import com.hakunamatata.sso.service.UserService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 单点登录控制器
 *
 * @author KaiKoo
 * @date 2020/6/16 22:34
 */
@Slf4j
@RestController
public class RootController {

    @Autowired
    private UserService userService;

    @PostMapping("/password/setup")
    public Result setPassword(@Valid @RequestBody UserSsoReq req) {
        userService.setPassword(req);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@Valid @RequestBody UserSsoReq req) {
        var token = new UsernamePasswordToken(req.getUsername(), req.getPassword());
        try {
            var subject = SecurityUtils.getSubject();
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(token);
        } catch (AuthenticationException e) {
            log.warn("login fail, {} => {}", e.getClass().getSimpleName(), e.getMessage());
            return Result.fail(e.getMessage());
        } catch (AuthorizationException e) {
            log.warn("没有权限", e);
            return Result.fail("没有权限");
        }
        var size = ShiroUtil.getActiveSessions().size();
        return Result.success(String.format("u are the %dth online user.", size));
    }

    @GetMapping("/")
    public String index() {
        var size = ShiroUtil.getActiveSessions().size();
        return String.format("there are the %d users online.", size);
    }

}
