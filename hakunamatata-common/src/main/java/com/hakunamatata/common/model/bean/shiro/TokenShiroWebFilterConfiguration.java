package com.hakunamatata.common.model.bean.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.AbstractShiroWebFilterConfiguration;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义 shiro filter 配置类
 *
 * @author KaiKoo
 */
@Configuration
public class TokenShiroWebFilterConfiguration extends AbstractShiroWebFilterConfiguration {

    @Bean
    @Override
    protected ShiroFilterFactoryBean shiroFilterFactoryBean() {
        var shiroFilterFactoryBean = super.shiroFilterFactoryBean();
        // 注入自定义的过滤器，替换掉默认的 authc 过滤器
        // 也可以自定义新的拦截类型
        shiroFilterFactoryBean.getFilters()
                .put(DefaultFilter.authc.name(), new TokenAuthenticationFilter());
        return shiroFilterFactoryBean;
    }

}
