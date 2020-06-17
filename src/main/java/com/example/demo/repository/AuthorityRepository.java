package com.example.demo.repository;

import com.example.demo.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByUsername(String userName);
}
