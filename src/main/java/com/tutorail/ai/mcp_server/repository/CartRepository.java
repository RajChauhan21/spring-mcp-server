package com.tutorail.ai.mcp_server.repository;

import com.tutorail.ai.mcp_server.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart,String> {
    Cart findByProductId(String id);
    void deleteByProductId(String id);
}
