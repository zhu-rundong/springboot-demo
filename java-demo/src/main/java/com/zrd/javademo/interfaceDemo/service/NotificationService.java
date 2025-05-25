package com.zrd.javademo.interfaceDemo.service;

import com.zrd.javademo.interfaceDemo.enums.NotificationType;

/**
 * @InterfaceName NotificationService
 * @Description TODO
 * @Author ZRD
 * @Date 2025/5/25
 */
public interface NotificationService {
    // 获取通知类型
    NotificationType getType();
    // 发送消息
    void send(String message);
}
