package com.hakunamatata.sso.config;

import com.hakunamatata.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * shiro框架实现realm
 * 只继承AuthenticatingRealm做认证，如需做权限控制需要实现AuthorizingRealm
 *
 * @author KaiKoo
 * @date 2020/6/16 21:49
 */
public class UserRealm extends AuthenticatingRealm {

    public UserRealm() {
        // 配置加密匹配器
        var hashedCredentialsMatcher = new HashedCredentialsMatcher("md5");
        hashedCredentialsMatcher.setHashIterations(3);
        this.setCredentialsMatcher(hashedCredentialsMatcher);
    }

    private final static String REALM_NAME = "user_realm";

    @Autowired
    private UserService userService;

    /**
     * 常见的异常
     * ExpiredCredentialsException
     * IncorrectCredentialsException
     * ExcessiveAttemptsException
     * LockedAccountException
     * ConcurrentAccessException
     * UnknownAccountException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        // 从主体传过来的认证信息中获得用户名
        var username = (String) token.getPrincipal();
        var user = userService.getByUsername(username);
        if (null == user) {
            return null;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            throw new LockedAccountException("need to set password");
        }
        var authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(),
                ByteSource.Util.bytes(user.getId().toString()), REALM_NAME);
        return authenticationInfo;
    }
}
