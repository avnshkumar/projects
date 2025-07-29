package com.example.repository;

import com.example.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {
    @Query("{'primaryEmail.address': ?0}")
    Optional<UserDocument> findByPrimaryEmailAddress(String email);

    @Query(value = "{'primaryEmail.address': ?0}", exists = true)
    boolean existsByPrimaryEmailAddress(String email);
}
