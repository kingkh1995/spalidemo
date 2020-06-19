package com.hakunamatata.sso.entity;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 设置密码接口requst
 * @author KaiKoo
 * @date 2020/6/16 22:35
 */
@Data
public class UserSsoReq implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
