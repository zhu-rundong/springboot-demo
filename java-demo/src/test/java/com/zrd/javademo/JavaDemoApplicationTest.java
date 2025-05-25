package com.zrd.javademo;

import com.zrd.javademo.interfaceDemo.config.NotificationFactory;
import com.zrd.javademo.interfaceDemo.enums.NotificationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName JavaDemoApplicationTest
 * @Description TODO
 * @Author ZRD
 * @Date 2025/5/25
 **/
@SpringBootTest
public class JavaDemoApplicationTest {
    @Autowired
    private NotificationFactory factory;
    @Test
    void testSend() {
        factory.getService(NotificationType.EMAIL).send("EMAIL...");
        factory.getService(NotificationType.SMS).send("SMS...");
        factory.getService(NotificationType.WECHAT).send("WECHAT...");
    }
}
