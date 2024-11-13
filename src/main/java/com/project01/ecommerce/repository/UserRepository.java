package com.project01.ecommerce.repository;


import com.project01.ecommerce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByGoogleid(String googleId);
    UserEntity findByGoogleid(String googleId);
    List<UserEntity> findByIdIn(List<Long> ids);
}
