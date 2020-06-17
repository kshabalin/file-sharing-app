package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
