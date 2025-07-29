package com.example.documents;

import com.example.model.EmailAddress;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "users")
@Data
public class UserDocument {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private EmailAddress primaryEmail;
    private List<EmailAddress> secondaryEmails;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
