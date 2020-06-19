package com.hakunamatata.sso.config;

import com.hakunamatata.common.model.bean.shiro.TokenSecurityManager;
import com.hakunamatata.common.model.bean.shiro.TokenShiroWebFilterConfiguration;
import com.hakunamatata.sso.bean.UserRealm;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * shiro配置类
 * todo. redis实现缓存
 *
 * @author KaiKoo
 */
@Configuration
@Import(TokenShiroWebFilterConfiguration.class) //注入shiro filter配置
public class ShiroConfiguration {

    /**
     * Apache Shiro 的核心通过 Filter 来实现
     *
     * anno：不需要授权、登录就可以访问
     * authc：需要登录授权才能访问
     * logout：退出拦截器。退出成功后，会 redirect到设置的/URI
     * noSessionCreation：不创建会话连接器
     * rest：rest风格拦截器
     * user：用户拦截器。eg：登录后（authc），第二次没登陆但是有记住我(rememberMe)都可以访问
     * @see DefaultFilter
     *
     * 如无需定制化ShiroFilter，只需要注册ShiroFilterChainDefinition
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        var filterChainDefinition = new DefaultShiroFilterChainDefinition();
        // 拦截器配置 使用的是LinkedHashMap 会进行顺序判断
        filterChainDefinition.addPathDefinition("/static/**", "anon");
        filterChainDefinition.addPathDefinition("/swagger-ui.html", "anon");
        filterChainDefinition.addPathDefinition("/webjars/**", "anon");
        filterChainDefinition.addPathDefinition("/v2/**", "anon");
        filterChainDefinition.addPathDefinition("/swagger-resources/**", "anon");
        filterChainDefinition.addPathDefinition("/h2-console/**", "anon");

        // 开放登录和登录成功接口
        filterChainDefinition.addPathDefinition("/login", "anon");
        filterChainDefinition.addPathDefinition("/", "anon");
        // 退出接口，shiro已直接实现，无需写controller，会重定向到"/"路径下
        filterChainDefinition.addPathDefinition("/logout", "logout");

        //需要放开的接口
        filterChainDefinition.addPathDefinition("/password/setup", "anon");

        // 将/**放在最为下边
        filterChainDefinition.addPathDefinition("/**", "authc");
        return filterChainDefinition;
    }

    // 注册SecurityManager
    @Bean
    public SessionsSecurityManager tokenSecurityManager() {
        var securityManager = new TokenSecurityManager();
        //配置realm
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    // 注册为bean，因为需要自动注入UserService
    @Bean
    public Realm userRealm() {
        return new UserRealm();
    }

}
