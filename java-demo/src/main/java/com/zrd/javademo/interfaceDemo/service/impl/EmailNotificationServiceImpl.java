package com.zrd.javademo.interfaceDemo.service.impl;

import com.zrd.javademo.interfaceDemo.enums.NotificationType;
import com.zrd.javademo.interfaceDemo.service.NotificationService;
import org.springframework.stereotype.Service;

/**
 * @ClassName EmailNotificationServiceImpl
 * @Description 邮件通知实现
 * @Author ZRD
 * @Date 2025/5/25
 **/
@Service
public class EmailNotificationServiceImpl implements NotificationService {


    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }

    @Override
    public void send(String message) {
        System.out.println("发送邮件: " + message);
    }
}
