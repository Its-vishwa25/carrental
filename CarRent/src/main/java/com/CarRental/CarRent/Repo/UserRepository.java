package com.CarRental.CarRent.Repo;

import java.util.Optional;

import com.CarRental.CarRent.Entity.User; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
