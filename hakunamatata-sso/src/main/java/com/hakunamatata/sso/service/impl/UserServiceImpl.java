package com.hakunamatata.sso.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.hakunamatata.common.util.ShiroUtil;
import com.hakunamatata.sso.entity.User;
import com.hakunamatata.sso.entity.UserSsoReq;
import com.hakunamatata.sso.mapper.UserMapper;
import com.hakunamatata.sso.message.UserMessageService;
import com.hakunamatata.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * user 服务实现类
 *
 * @author KaiKoo
 * @date 2020/4/23 22:06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMessageService userMessageService;

    @Override
    public User getExistedById(Long id) {
        if (null != id) {
            var user = baseMapper.selectExistedById(id);
            userMessageService.sendUserMessage(user);
            return user;
        }
        return null;
    }

    @Override
    public User getByUsername(String username) {
        if (StringUtils.isNotBlank(username)) {
            return baseMapper
                    .selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        }
        return null;
    }

    @Override
    public boolean setPassword(UserSsoReq req) {
        var user = getByUsername(req.getUsername());
        Preconditions.checkNotNull(user, "username not exist");
        user.setPassword(ShiroUtil.encryptPassword(req.getPassword(), user.getId()));
        return baseMapper.updateById(user) > 0;
    }

}
