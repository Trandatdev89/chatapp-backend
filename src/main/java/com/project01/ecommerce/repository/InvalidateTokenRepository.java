package com.project01.ecommerce.repository;

import com.project01.ecommerce.entity.InvalidTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InvalidateTokenRepository extends JpaRepository<InvalidTokenEntity,String> {

    @Transactional
    @Modifying
    @Query("DELETE from InvalidTokenEntity t where t.expirytime<=CURRENT_TIMESTAMP")
    void deleteExpiryTime();
}
