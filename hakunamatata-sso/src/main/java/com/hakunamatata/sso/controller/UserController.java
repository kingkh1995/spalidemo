package com.hakunamatata.sso.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.hakunamatata.common.enums.GenderEnum;
import com.hakunamatata.common.model.bean.Result;
import com.hakunamatata.common.model.dto.UserDTO;
import com.hakunamatata.common.provider.UserProvider;
import com.hakunamatata.common.util.MyCollectionUtil;
import com.hakunamatata.sso.assembler.UserAssembler;
import com.hakunamatata.sso.entity.User;
import com.hakunamatata.sso.service.UserService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * user 前端控制器
 *
 * @author KaiKoo
 * @date 2020/4/23 22:14
 */
@Service //声明为dubbo生成者
@Slf4j
@RestController
public class UserController implements UserProvider {

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private UserService userService;

    @Override
    public Result<PageInfo<UserDTO>> list(List<GenderEnum> genders, int pageNum, int pageSize) {
        log.info("list user => genders:{}, pageNum:{}, pageSize:{}.", genders, pageNum, pageSize);
        var wrapper = Wrappers.<User>lambdaQuery();
        if (MyCollectionUtil.notEmptyAfterFilterNull(genders)) {
            wrapper.in(User::getGender, genders);
        }
        return Result.success(userAssembler
                .buildDTOPage(userService.page(new Page<>(pageNum, pageSize), wrapper)));
    }

    @Override
    public Result<UserDTO> getUser(Long id, Boolean existed) {
        log.info("get user info => id:{}, existed:{}.", id, existed);
        // todo. 防止雪崩、击穿处理
        // 先查有效用户
        var user = userService.getById(id);
        // 有效用户查不到并且需要查已失效用户才去从所有数据中查找
        if (null == user && existed) {
            user = userService.getExistedById(id);
            if (user.getDeleted()) {
                user.setId(-1L);
            } else {
                user.setId(0L);
            }
        }
        return Result.success(userAssembler.buildDTO(user));
    }

}
