package com.project01.ecommerce.repository;

import com.project01.ecommerce.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity,Long> {
   Optional<ChatRoomEntity> findByName(String name);
   void deleteByIdIn(List<Long> id);
}
