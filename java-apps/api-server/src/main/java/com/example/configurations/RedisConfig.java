package com.example.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service.connections.redis")
@Data
public class RedisConfig {
    private String host;
    private int port;
}