package com.tutorail.ai.mcp_server.repository;

import com.tutorail.ai.mcp_server.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByName(String name);
}
