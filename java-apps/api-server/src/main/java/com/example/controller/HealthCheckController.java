package com.example.controller;

import com.example.constants.BeanConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    public static final Logger logger = LogManager.getLogger(HealthCheckController.class);

    private final MongoTemplate mongoTemplate;
    private final JdbcTemplate postgresTemplate;
    public HealthCheckController(MongoTemplate mongoTemplate, JdbcTemplate postgresTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.postgresTemplate = postgresTemplate;
    }


    @GetMapping
    public String healthCheck() {
        return "OK";
    }

    @GetMapping("/mongodb")
    public String mongoDbHealthCheck() {
        try {
            Document pingCommand = new Document("ping", 1);
            Document result = mongoTemplate.getDb().runCommand(pingCommand);
            logger.debug("MongoDB ping result: {}", result.toJson());
            return "OK";
        } catch (Exception e) {
            return "MongoDB is not reachable: " + e.getMessage();
        }
    }


    @GetMapping("/postgres")
    public String postgresHealthCheck() {
        try {
            Integer result = postgresTemplate.queryForObject("SELECT 1", Integer.class);
            if (result != null && result == 1) {
                return "✅ PostgreSQL is alive!";
            }
        } catch (Exception e) {
            logger.error("PostgreSQL health check failed: {}", e.getMessage(), e);
            return "Ping Failed";
        }
        return "PostgreSQL is not reachable";
    }
}
