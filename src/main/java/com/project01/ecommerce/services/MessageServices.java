package com.project01.ecommerce.services;

import com.project01.ecommerce.model.dto.MessageDTO;

import java.util.List;

public interface MessageServices {
    void saveMessagePrivate(MessageDTO message);
    void saveMessagePublic(MessageDTO message);
    List<MessageDTO> getMessageByReciveIdAndSenderId(Long sendId, Long RecipentId);
    List<MessageDTO> getMessageByChatRoomId(Long chatRoomId);
}
