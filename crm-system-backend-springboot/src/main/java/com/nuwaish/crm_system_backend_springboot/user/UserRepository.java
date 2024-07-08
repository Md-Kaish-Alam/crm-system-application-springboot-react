package com.nuwaish.crm_system_backend_springboot.user;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{email :?0}")
    User findByEmail(String email);
}
