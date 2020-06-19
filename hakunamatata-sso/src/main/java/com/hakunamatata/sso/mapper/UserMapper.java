package com.hakunamatata.sso.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hakunamatata.common.model.dto.UserDTO;
import com.hakunamatata.sso.entity.User;

/**
 * user Mapper 接口 （方法名使用：insert delete update select selectList selectPage）
 *
 * @author KaiKoo
 */
public interface UserMapper extends BaseMapper<User> {

    @SqlParser(filter = true)
    User selectExistedById(Long id);

    /**
     * 分页查询存在的用户信息
     *
     * @param page 必须放在第一位，返回的对象与传入的对象是同一个，可以定义为Page<?>（建议不这么做）
     * @return
     */
    IPage<UserDTO> selectExistedDTOPage(Page<?> page);

}
