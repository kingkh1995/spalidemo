package com.hakunamatata.sso.bean;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义binding配置
 * @author KaiKoo
 * @date 2020/6/1 20:48
 */
public interface CustomBinding {

    String INPUT1 = "input1";

    String INPUT2 = "input2";

    String OUTPUT = "output";

    @Input(INPUT1)
    SubscribableChannel input1();

    @Input(INPUT2)
    SubscribableChannel input2();

    @Output(OUTPUT)
    MessageChannel output();
}
