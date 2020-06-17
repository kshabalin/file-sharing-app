package com.example.demo.repository;

import com.example.demo.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-16
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into shared_files(user_id, file_id) values (?1, ?2)", nativeQuery = true)
    void share(Long userId, Long fileId);
}
