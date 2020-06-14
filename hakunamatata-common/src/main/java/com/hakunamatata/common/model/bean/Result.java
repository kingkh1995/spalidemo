package com.hakunamatata.common.model.bean;

import com.github.pagehelper.PageInfo;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * Result
 *
 * @author KaiKoo
 * @date 2020/4/21 20:50
 */
@Getter
@ToString
public class Result<T> implements Serializable {

//    private final static long serialVersionUID = 1L;

    // 成功为0，失败为非0
    private final static int SUCCESS_CODE = 0;

    private final static int FAIL_CODE = 1;

    private final static String SUCCESS_MESSAGE = "succeed";

    private final static String FAIL_MESSAGE = "failed";

    private int code;

    private String message;

    private T data;

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static Result fail() {
        return new Result<>(FAIL_CODE, FAIL_MESSAGE);
    }

    public static Result fail(String message) {
        if (StringUtils.isBlank(message)) {
            return fail();
        }
        return new Result<>(FAIL_CODE, message);
    }

    // 以下方法必须定义为泛型方法 因为静态方法无法访问类上定义的泛型

    public static <T> Result<T> success(T t) {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, t);
    }

    public static <E> Result<List<E>> successWithEmptyList() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, Collections.emptyList());
    }

    public static <K, V> Result<Map<K, V>> successWithEmptyMap() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, Collections.emptyMap());
    }

    public static <T> Result<PageInfo<T>> successWithEmptyPage() {
        return new Result<>(SUCCESS_CODE, SUCCESS_MESSAGE, new PageInfo<>(Collections.emptyList()));
    }

}
