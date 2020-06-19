package com.hakunamatata.common.model.bean.base;

/**
 * 工具类基类
 *
 * @author KaiKoo
 * @date 2020/4/25 18:36
 */
public abstract class BaseUtil {

    // 保证无法通过构造方法、自身调用以及反射获取工具类的对象
    protected BaseUtil() throws IllegalAccessException {
        throw new IllegalAccessException("cannot access to constructor");
    }

}
