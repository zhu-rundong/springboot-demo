package com.zrd.javademo.interfaceDemo.service.impl;

import com.zrd.javademo.interfaceDemo.enums.NotificationType;
import com.zrd.javademo.interfaceDemo.service.NotificationService;
import org.springframework.stereotype.Service;

/**
 * @ClassName SmsNotificationServiceImpl
 * @Description 短信通知实现
 * @Author ZRD
 * @Date 2025/5/25
 **/
@Service
public class SmsNotificationServiceImpl implements NotificationService {
    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }

    @Override
    public void send(String message) {
        System.out.println("发送短信: " + message);
    }
}
