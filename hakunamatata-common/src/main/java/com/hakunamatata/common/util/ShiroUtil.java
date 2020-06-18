package com.hakunamatata.common.util;

import com.hakunamatata.common.model.bean.BaseUtil;
import java.util.Collection;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;

/**
 * shiro session 工具类
 *
 * @author KaiKoo
 * @date 2020/6/18 23:12
 */
public class ShiroUtil extends BaseUtil {

    private ShiroUtil() throws IllegalAccessException {
    }

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
