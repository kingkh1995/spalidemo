package com.hakunamatata.common.util;

import com.hakunamatata.common.model.bean.base.BaseUtil;
import java.util.Collection;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;

/**
 * shiro 工具类
 *
 * @author KaiKoo
 */
public class ShiroUtil extends BaseUtil {

    public static Collection<Session> getActiveSessions() {
        var securityManager = (SessionsSecurityManager) SecurityUtils.getSecurityManager();
        var sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
        // MemorySessionDAO的实现中该方法返回内存中所有的session，包括没来得及被清除的过期session
        return sessionManager.getSessionDAO().getActiveSessions();
    }

    public static String encryptPassword(String password, long id) {
        //使用id加盐做3次md5
        return new Md5Hash(password, String.valueOf(id), 3).toHex();
    }

}
