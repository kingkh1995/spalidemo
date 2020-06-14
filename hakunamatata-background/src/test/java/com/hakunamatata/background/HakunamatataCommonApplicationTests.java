package com.hakunamatata.background;

import com.hakunamatata.common.model.bean.Result;
import com.hakunamatata.common.model.dto.UserDTO;
import com.hakunamatata.common.provider.UserProvider;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class HakunamatataCommonApplicationTests {

    @Reference
    private UserProvider userProvider;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void restTest() {
        var entity = restTemplate.getForObject("http://sso-provider/user/list", String.class);
        System.out.println(entity);
    }

    @Test
    void dubboTest() {
        Result<UserDTO> entity = userProvider.getUser(1L, false);
        System.out.println(entity);
    }

}
