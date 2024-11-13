package com.project01.ecommerce.repository;

import com.project01.ecommerce.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    boolean existsByChatRoom_IdAndUser_Id(Long chatId,Long userId);
    List<UserRole> findByChatRoom_Id(Long chatId);
    List<UserRole> findByUser_Id(Long userId);
    void deleteByUser_IdInAndChatRoomId(List<Long> users,Long chatId);
}
