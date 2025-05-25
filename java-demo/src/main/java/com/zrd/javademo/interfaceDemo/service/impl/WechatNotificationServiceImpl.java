package com.zrd.javademo.interfaceDemo.service.impl;

import com.zrd.javademo.interfaceDemo.enums.NotificationType;
import com.zrd.javademo.interfaceDemo.service.NotificationService;
import org.springframework.stereotype.Service;

/**
 * @ClassName WechatNotificationServiceImpl
 * @Description 微信通知实现
 * @Author ZRD
 * @Date 2025/5/25
 **/
@Service
public class WechatNotificationServiceImpl implements NotificationService {
    @Override
    public NotificationType getType() {
        return NotificationType.WECHAT;
    }
    @Override
    public void send(String message) {
        System.out.println("发送微信: " + message);
    }
}
