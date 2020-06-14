package com.hakunamatata.sso.assembler;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hakunamatata.common.model.bean.BaseAssembler;
import com.hakunamatata.common.model.dto.UserDTO;
import com.hakunamatata.sso.entity.User;
import java.util.Collections;
import javax.annotation.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * user 装配工具类
 *
 * @author KaiKoo
 * @date 2020/4/24 20:39
 */
@Component //定义为component使用
public class UserAssembler extends BaseAssembler<User, UserDTO> {

    @Override
    public UserDTO buildDTO(@Nullable User user) {
        var dto = super.buildDTO(user);
        if (null != dto) {
            dto.setDesc(String.format("%s(%d)", user.getName().toLowerCase(), user.getAge()));
        }
        return dto;
    }

    public PageInfo<UserDTO> buildDTOPage(@Nullable IPage<User> userPage) {
        if (null != userPage) {
            var page = new Page<UserDTO>((int) userPage.getCurrent(), (int) userPage.getSize());
            page.setReasonable(true);
            page.setTotal(userPage.getTotal());
            if (!CollectionUtils.isEmpty(userPage.getRecords())) {
                userPage.getRecords().forEach(user -> page.add(buildDTO(user)));
            }
            return page.toPageInfo();
        }
        return new PageInfo<>(Collections.emptyList());
    }

}
