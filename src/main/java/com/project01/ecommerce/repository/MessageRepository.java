package com.project01.ecommerce.repository;

import com.project01.ecommerce.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findBySender_IdAndRecipient_IdOrRecipient_IdAndSender_Id(Long senderId, Long recipientId,Long senderId1,Long recipientId1);
    List<MessageEntity> findByChatRoom_Id(Long roomId);
}
