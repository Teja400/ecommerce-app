package com.blueyonder.ecomapp.repo;

import com.blueyonder.ecomapp.model.UserRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserRequest, Integer> {
    public UserRequest findByUsername(String username);
}
