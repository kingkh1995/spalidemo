package com.hakunamatata.common.model.bean.shiro;

import java.io.PrintWriter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * 自定义 authc 过滤器
 *
 * @author KaiKoo
 */
public class TokenAuthenticationFilter extends AuthenticatingFilter {

    private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // 在AuthenticatingFilter的executeLogin方法中被引用
        // token校验失败则直接失败，不创建AuthenticationToken去尝试登录。
        return null;
    }

    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
            Object mappedValue) {
        var accessAllowed = super.isAccessAllowed(request, response, mappedValue);
        // 如果token校验成功，并且访问的是登录接口，则重定向到成功页面，不继续进行登录操作，防止重复登录多次创建session
        if (accessAllowed && isLoginRequest(request, response)) {
            issueSuccessRedirect(request, response);
        }
        return accessAllowed;
    }

    // 实现登录信息校验失败后处理逻辑
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {
        if (isLoginRequest(request, response)) {
            // 访问的是登录接口 => 放行 执行登录
            return true;
        } else {
            // 其他 => 返回错误信息
            if (!(request instanceof HttpServletRequest)) {
                log.debug("Current request is not an HttpServletRequest.");
                return false;
            }
            var httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value()); //401未登录
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setHeader("Content-Type", "application/json");
            PrintWriter out = response.getWriter();
            out.print("please login!");
            out.close();
            return false;
        }
    }
}
