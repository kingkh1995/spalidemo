package com.hakunamatata.sso;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hakunamatata.common.enums.GenderEnum;
import com.hakunamatata.sso.entity.User;
import com.hakunamatata.sso.mapper.UserMapper;
import com.hakunamatata.sso.message.UserMessageService;
import com.hakunamatata.sso.service.UserService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HakunamatataSsoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMessageService userMessageService;

    @Test
    void messageTest() {
        var huhu = new User().setName("HuHu").setAge((byte) 1).setGender(GenderEnum.MALE)
                .setAppNo("app1").setEmail("huhu@hakunamatata.com")
                .setFavor(Arrays.asList("sleep", "eat"));
        System.out.println(userMessageService.sendUserMessage(huhu));
    }

    @Test
    void mapperTest() {
        User entity = userMapper.selectById(1L);
        userMapper.updateById(entity);
//        userMapper.update(entity, Wrappers.<User>lambdaUpdate().set(User::getAppNo, "app2"));
//        System.out.println(userMapper.selectById(1L));
//        System.out.println(userMapper.selectExistedById(1L));
    }

    @Test
    void test() {
        System.out.println(userMapper.selectExistedById(4L));
        System.out.println(userService.getExistedById(4L));
        userMapper.selectExistedDTOPage(new Page<>(2, 2)).getRecords().forEach(System.out::println);
        userMapper
                .insert(new User().setName("haah").setAge((byte) 19).setFavor(Arrays.asList("gogo"))
                        .setGender(GenderEnum.FEMALE));
    }

}
