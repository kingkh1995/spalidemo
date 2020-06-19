package com.hakunamatata.common.model.bean.shiro;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

/**
 * 自定义SecurityManager
 *
 * @author KaiKoo
 * @date 2020/6/19 22:55
 */
public class TokenSecurityManager extends DefaultWebSecurityManager {

    public TokenSecurityManager() {
        //web应用使用DefaultWebSecurityManager
        super();
        //配置自定义的SessionManger
        setSessionManager(new TokenWebSessionManager());
        //因为是基于token认证，关闭掉默认添加的记住我功能
        setRememberMeManager(null);
    }
}
