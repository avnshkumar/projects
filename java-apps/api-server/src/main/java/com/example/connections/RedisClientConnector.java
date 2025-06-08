package com.example.connections;


import com.example.configurations.RedisConfig;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class RedisClientConnector{
    private RedisConfig redisConfig;
    private RedisClient redisClient;

    public RedisClientConnector(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
        try {
            RedisURI redisURI = RedisURI.builder()
                    .withHost(redisConfig.getHost())
                    .withPort(redisConfig.getPort())
                    .build();
            this.redisClient = RedisClient.create(redisURI);
            System.out.println("Connected to Redis");
        } catch (Exception e) {
            System.out.println("Error creating Redis client: " + e.getMessage());
        }
    }

    @PreDestroy
    public void onDestroy() {
        if (redisClient != null) {
            redisClient.close();
        }
    }





}
