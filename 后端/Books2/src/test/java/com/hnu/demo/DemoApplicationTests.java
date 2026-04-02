package com.hnu.demo;

import com.hnu.util.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(SecurityUtils.md5Hex("ly123456"));
    }

}
