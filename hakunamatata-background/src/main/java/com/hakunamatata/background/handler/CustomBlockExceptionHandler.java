package com.hakunamatata.background.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hakunamatata.common.model.bean.base.Result;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义controller层sentinel拦截返回信息
 *
 * @author KaiKoo
 */
@Component
public class CustomBlockExceptionHandler implements BlockExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e)
            throws Exception {
        response.setStatus(429); // Return 429 (Too Many Requests) by default.
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(Result.fail("sentinel url blocked")));
        out.flush();
        out.close();
    }
}
