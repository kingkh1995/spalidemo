package com.hakunamatata.common.model.dto;

import com.hakunamatata.common.enums.GenderEnum;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * user 数据传输对象
 *
 * @author KaiKoo
 * @date 2020/4/24 20:35
 */
@Data
public class UserDTO implements Serializable {

    private Long id;

    private String name;

    private GenderEnum gender;

    private Byte age;

    private String email;

    private String desc;

    private String appNo;

    private List<String> favor;

}
