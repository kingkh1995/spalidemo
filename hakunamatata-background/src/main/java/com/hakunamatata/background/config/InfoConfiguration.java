package com.hakunamatata.background.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 后台信息配置（nacos config example）
 *
 * @author KaiKoo
 * @date 2020/5/10 15:11
 */
@Data
@Component
@ConfigurationProperties(prefix = "info") //使用ConfigurationProperties默认启动了配置自动刷新
public class InfoConfiguration {

    private String title;
    private List<String> rules;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Byte contributors;

    public String show() {
        var sb = new StringBuffer();
        sb.append(String.format("欢迎访问\'%s\'\n", title));
        sb.append("你务必遵守以下规定：\n");
        rules.forEach(s -> {
            sb.append("\t" + s + "\n");
        });
        sb.append(String.format("\n\t本网站构建于%s\n", date.toInstant().atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))));
        sb.append(String.format("\t--感谢所有%d名贡献者--\n", contributors));
        return sb.toString();
    }
}
