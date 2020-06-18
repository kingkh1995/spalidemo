package com.hakunamatata.sso.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.hakunamatata.common.enums.GenderEnum;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * user
 *
 * @author KaiKoo
 * @date 2020/4/23 21:56
 */
@Data
@Accessors(chain = true)
@TableName(autoResultMap = true)
public class User {

    @TableId(type = IdType.ASSIGN_ID) //默认实现雪花算法
    private Long id;

    private String name;

    private String username;

    private String password;

    private GenderEnum gender;

    private Byte age;

    private String email;

    /**
     * 将对象json化保存至数据库VARCHAR
     * 1.默认方法时必须开启@TableName(autoResultMap = true)和配置@TableField
     * 2.自定义sql必须使用resultmap并定义column的typeHandler属性
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> favor;

    /**
     * 租户id需要配置插件，所有sql均会自动加上租户sql拦截，使用@SqlParser排除sql解析
     */
    private String appNo;

    /**
     * 需要配置插件并使用@Version注解
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法，使用时需要传入原版本号
     */
    @Version
    private Integer version;

    /**
     * 自带的方法才会自动带上sql，已配置全局逻辑删除字段不再需要 @TableLogic 注解
     */
    // @TableLogic
    private Boolean deleted;

}
