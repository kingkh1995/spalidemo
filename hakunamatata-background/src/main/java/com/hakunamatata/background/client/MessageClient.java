package com.hakunamatata.background.client;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * 消息处理client
 * @author KaiKoo
 * @date 2020/6/1 22:18
 */
@Component
public class MessageClient {

    @StreamListener(Sink.INPUT)
    public void receiveInput(String receiveMsg) {
        System.out.println("input receive: " + receiveMsg);
    }
}
