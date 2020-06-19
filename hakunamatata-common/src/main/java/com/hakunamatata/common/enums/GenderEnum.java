package com.hakunamatata.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author KaiKoo
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    MALE("male", "男性"), FEMALE("female", "女性");

    private String value;
    private String desc;

}
