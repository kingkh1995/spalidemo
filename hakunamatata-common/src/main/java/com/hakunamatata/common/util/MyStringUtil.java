package com.hakunamatata.common.util;

import com.hakunamatata.common.model.bean.base.BaseUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 字符串工具类
 *
 * @author KaiKoo
 */
public class MyStringUtil extends BaseUtil {

    // 使用kmp算法查找子字符串
    public static Map<String, Integer> search(String template, Set<String> strings) {
        var map = new HashMap<String, Integer>();
        var kmp = new KMP(template);
        for (String s : strings) {
            map.put(s, kmp.search(s));
        }
        return map;
    }

    private static class KMP {

        private final int[] value;

        private final char[] chars;

        private KMP(String template) {
            template = Objects.requireNonNullElse(template, "");
            var tLen = template.length();
            if (tLen != 0) {
                chars = new char[tLen];
                for (int i = 0; i < tLen; i++) {
                    chars[i] = template.charAt(i);
                }
                value = new int[tLen];
                value[0] = -1;
                var k = -1;
                var j = 0;
                while (j < tLen - 1) {
                    if (k == -1 || chars[j] == chars[k]) {
                        k++;
                        j++;
                        value[j] = k;
                    } else {
                        k = value[k];
                    }
                }
            } else {
                value = null;
                chars = null;
            }
        }

        private int search(String string) {
            if (value == null) {
                return 0;
            }
            var sLen = string.length();
            var i = 0;
            var j = 0;
            while (i < sLen && j < value.length) {
                if (j == -1 || string.charAt(i) == chars[j]) {
                    i++;
                    j++;
                    if (j == value.length) {
                        return i - j;
                    }
                } else {
                    j = value[j];
                }
            }
            return -1;
        }
    }

}
