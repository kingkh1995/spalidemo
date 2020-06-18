package com.hakunamatata.sso.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hakunamatata.sso.bean.UserSsoReq;
import com.hakunamatata.sso.entity.User;

/**
 * user 服务类 （方法名使用：save remove update get list page）
 *
 * @author KaiKoo
 * @date 2020/4/23 22:02
 */
public interface UserService extends IService<User> {

    User getExistedById(Long id);

    User getByUsername(String username);

    boolean setPassword(UserSsoReq req);
}
