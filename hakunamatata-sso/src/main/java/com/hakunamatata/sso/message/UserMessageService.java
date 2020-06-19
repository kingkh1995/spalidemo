package com.hakunamatata.sso.message;

import com.hakunamatata.sso.bean.CustomBinding;
import com.hakunamatata.sso.entity.User;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * user消息处理类
 * @author KaiKoo
 * @date 2020/5/27 23:24
 */
@Slf4j
@Component
public class UserMessageService {

    @Autowired
    CustomBinding binding; //注入自定义bingding配置

    public boolean sendUserMessage(User user) {
        var headers = new HashMap<String, Object>();
        headers.put(MessageConst.PROPERTY_TAGS, "user-tag");
        var message = MessageBuilder.createMessage(user, new MessageHeaders(headers));
        return binding.output().send(message); //发送完成返回true，并不一定会成功
    }

    // 配置监听 input1 binding 使用json方式传输数据 可直接使用类型作为接受参数
    @StreamListener(CustomBinding.INPUT1)
    public void receiveInput1(User receiveMsg) {
        System.out.println("input1 receive: " + receiveMsg);
    }

    // 配置监听 input2 binding
    @StreamListener(CustomBinding.INPUT2)
    public void receiveInput2(User receiveMsg) {
        System.out.println("input2 receive: " + receiveMsg);
    }

    // 消息消费统一异常处理
    @StreamListener("errorChannel")
    public void handleErrors(ErrorMessage message) {
        log.info("默认的消息失败处理器 ==> 收到处理失败的消息: {}，headers：{}", message.getOriginalMessage(),
                message.getHeaders());
    }
}
