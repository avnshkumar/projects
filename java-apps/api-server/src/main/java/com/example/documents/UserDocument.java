package com.example.documents;

import com.example.model.EmailAddress;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
public class UserDocument {
    @Indexed
    private String id;

    private String name;

    private EmailAddress primaryEmail;
    private List<EmailAddress> secondaryEmails;
}

