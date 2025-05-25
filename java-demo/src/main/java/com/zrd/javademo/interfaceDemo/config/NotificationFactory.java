package com.zrd.javademo.interfaceDemo.config;

import com.zrd.javademo.interfaceDemo.enums.NotificationType;
import com.zrd.javademo.interfaceDemo.service.NotificationService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName NotificationFactory
 * @Description 通知调度工厂
 * @Author ZRD
 * @Date 2025/5/25
 **/
@Component
public class NotificationFactory {
    private final Map<NotificationType, NotificationService> services;

    public NotificationFactory(List<NotificationService> services) {
        this.services = services.stream()
                .collect(Collectors.toMap(
                        NotificationService::getType,
                        Function.identity()
                ));
    }

    public NotificationService getService(NotificationType type) {
        return Optional.ofNullable(services.get(type))
                .orElseThrow(() -> new IllegalArgumentException("未知的通知类型"));
    }
}
