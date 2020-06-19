package com.hakunamatata.common.provider;

import com.github.pagehelper.PageInfo;
import com.hakunamatata.common.enums.GenderEnum;
import com.hakunamatata.common.model.bean.base.Result;
import com.hakunamatata.common.model.dto.UserDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * user 生产者
 *
 * @author KaiKoo
 */
public interface UserProvider {

    /**
     * 分页查询用户信息
     *
     * @param pageNum  查询页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("/user/list")
    Result<PageInfo<UserDTO>> list(@RequestParam(required = false) List<GenderEnum> genders,
            @RequestParam(required = false, defaultValue = "1") int pageNum,
            @RequestParam(required = false, defaultValue = "10") int pageSize);

    /**
     * 查询用户信息
     *
     * @param id      用户ID
     * @param existed 是否从所有用户中查询
     * @return
     */
    @GetMapping("/user/{id}")
    Result<UserDTO> getUser(@PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") Boolean existed);

}
