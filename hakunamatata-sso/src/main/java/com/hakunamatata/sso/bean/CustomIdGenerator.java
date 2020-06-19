package com.hakunamatata.sso.bean;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import java.util.concurrent.atomic.LongAdder;
import javax.annotation.PostConstruct;

/**
 * 自定义ID生成策略
 *
 * @author KaiKoo
 */
public class CustomIdGenerator implements IdentifierGenerator {

    /**
     * 竞争激烈的情况下比AtomicLong性能好很多
     */
    private final LongAdder adder = new LongAdder();

    @PostConstruct
    public void init() {
        adder.add(7);
    }

    @Override
    public Long nextId(Object entity) {
        //todo. 待实现
        adder.increment();
        final var id = adder.sum();
        return id;
    }

}
