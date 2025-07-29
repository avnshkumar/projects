package com.example.service;

import com.example.documents.UserDocument;
import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.dto.SignupRequest;
import com.example.model.EmailAddress;
import com.example.repository.UserRepository;
import com.example.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MongoTemplate mongoTemplate;

    public AuthResponse signup(SignupRequest request) {
        // Check if user already exists using MongoTemplate for better query control
        Query query = new Query(Criteria.where("primaryEmail.address").is(request.getEmail()));
        boolean userExists = mongoTemplate.exists(query, UserDocument.class);

        if (userExists) {
            throw new RuntimeException("User with this email already exists");
        }

        // Create new user
        UserDocument user = new UserDocument();
        user.setName(request.getName());
        user.setPrimaryEmail(new EmailAddress(request.getEmail()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserDocument savedUser = userRepository.save(user);

        return new AuthResponse("User registered successfully", savedUser.getId(), savedUser.getName());
    }

    public AuthResponse login(LoginRequest request) {
        Query query = new Query(Criteria.where("primaryEmail.address").is(request.getEmail()));
        UserDocument user = mongoTemplate.findOne(query, UserDocument.class);

        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return new AuthResponse("Login successful", user.getId(), user.getName());
    }

    public String generateToken(String email) {
        return jwtUtil.generateToken(email);
    }

    public Optional<UserDocument> findByEmail(String email) {
        Query query = new Query(Criteria.where("primaryEmail.address").is(email));
        UserDocument user = mongoTemplate.findOne(query, UserDocument.class);
        return Optional.ofNullable(user);
    }
}
