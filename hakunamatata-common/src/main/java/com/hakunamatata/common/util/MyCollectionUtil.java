package com.hakunamatata.common.util;

import com.hakunamatata.common.model.bean.BaseUtil;
import org.springframework.lang.Nullable;

/**
 * 集合工具类
 *
 * @author KaiKoo
 * @date 2020/4/15 20:18
 */
public class MyCollectionUtil extends BaseUtil {

    private MyCollectionUtil() throws IllegalAccessException {
    }

    /**
     * 过滤空元素后判断是否非空
     *
     * @param iterable
     * @return
     */
    public static boolean notEmptyAfterFilterNull(@Nullable Iterable<?> iterable) {
        var flag = false;
        if (null != iterable) {
            var it = iterable.iterator();
            while (it.hasNext()) {
                if (null == it.next()) {
                    it.remove();
                } else if (!flag) {
                    flag = true;
                }
            }
        }
        return flag;
    }

}
