package com.hakunamatata.sso.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.hakunamatata.sso.bean.CustomIdGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置类
 *
 * @author KaiKoo
 * @date 2020/4/23 21:57
 */
@Configuration
public class MybatisPlusConfiguration {

    // 乐观锁插件
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    // 分页插件 多租户插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        var paginationInterceptor = new PaginationInterceptor();
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(100);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        // 多租户处理插件配置
        var tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId(boolean where) {
                //where表示当前sql是不是where条件
                //如果是where，可以追加多租户多个条件in，不是where的情况：比如当insert时，不能insert into user(name, tenant_id) values('test', tenant_id IN (1, 2));
                //todo. 判断当前是否拥有多个租户的权限
                final var multipleTenantIds = false;
                if (where && multipleTenantIds) {
                    return multipleTenantCondition();
                } else {
                    return singleTenantCondition();
                }

            }

            private Expression singleTenantCondition() {
                // todo. 从用户信息里面获取
                return new StringValue("app1");
            }

            private Expression multipleTenantCondition() {
                //todo. 目前有Bug
                //IN逻辑 InExpression
                final var inExpression = new InExpression();
                inExpression.setLeftExpression(new Column(getTenantIdColumn()));
                final var itemsList = new ExpressionList();
                final var inValues = new ArrayList<Expression>(2);
                inValues.add(new StringValue("app1"));
                inValues.add(new StringValue("app2"));
                itemsList.setExpressions(inValues);
                inExpression.setRightItemsList(itemsList);
                return inExpression;
            }

            @Override
            public String getTenantIdColumn() {
                return "app_no";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                // 过滤表处理
                return false;
            }
        });
        paginationInterceptor.setSqlParserList(Arrays.asList(tenantSqlParser));
        return paginationInterceptor;
    }

    // 设置自定义主键生成策略
    @Bean
    public IdentifierGenerator idGenerator() {
        return new CustomIdGenerator();
    }

}
