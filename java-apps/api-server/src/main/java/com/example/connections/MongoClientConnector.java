package com.example.connections;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.lang.NonNull;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "service.connections.mongodb")
public class MongoClientConnector extends AbstractMongoClientConfiguration {

    private String mongoUri;

    private String database;

    // How to connect to multiple databases
    @NonNull
    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    @NonNull
    public MongoClientSettings mongoClientSettings() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        return MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
    }

    @PostConstruct
    public void validateProperties() {
        System.out.println("Database name: " + database);
    }

}
