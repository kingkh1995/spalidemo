package com.hakunamatata.common.model.bean.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hakunamatata.common.util.MyCollectionUtil;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import org.springframework.beans.BeanUtils;

/**
 * 装配工具类基类
 *
 * @author KaiKoo
 * @date 2020/4/28 20:28
 */
public abstract class BaseAssembler<E, T extends Serializable> {

    {
        var type = (ParameterizedType) this.getClass().getGenericSuperclass();
        tClass = (Class) type.getActualTypeArguments()[1];
    }

    private Class<T> tClass;

    public T buildDTO(@Nullable E e) {
        if (null != e) {
            var t = BeanUtils.instantiateClass(tClass);
            BeanUtils.copyProperties(e, t);
            return t;
        }
        return null;
    }

    public List<T> buildDTOList(@Nullable List<E> eList) {
        if (MyCollectionUtil.notEmptyAfterFilterNull(eList)) {
            var dtoList = new ArrayList<T>(eList.size());
            eList.forEach(user -> dtoList.add(buildDTO(user)));
            return dtoList;
        }
        return Collections.emptyList();
    }

    public PageInfo<T> buildDTOPage(@Nullable Page<E> ePage) {
        if (null != ePage) {
            var page = new Page<T>(ePage.getPageNum(), ePage.getPageSize());
            page.setReasonable(true);
            page.setTotal(ePage.getTotal());
            ePage.forEach(user -> page.add(buildDTO(user)));
            return page.toPageInfo();
        }
        return new PageInfo<>(Collections.emptyList());
    }
}
