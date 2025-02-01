package com.project01.ecommerce.controller;


import com.project01.ecommerce.model.dto.MessageDTO;
import com.project01.ecommerce.repository.UserRepository;
import com.project01.ecommerce.services.MessageServices;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MessageServices messageServices;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public List<MessageDTO> publicMessage(@Payload MessageDTO message) {
        if(message.getMessage()!=null && !message.getMessage().equals("")) {
            messageServices.saveMessagePublic(message);
        }
        List<MessageDTO> messageEntities = messageServices.getMessageByChatRoomId(message.getChatroomId());
        return messageEntities;
    }

    @MessageMapping("/private")
    public void privateMessage(@Payload MessageDTO message) {
        MessageDTO messageDTO = null;
        if(message.getMessage()!=null && !message.getMessage().equals("")) {
            messageDTO = messageServices.saveMessagePrivate(message);
        }
        messagingTemplate.convertAndSendToUser(message.getReceiverId().toString(),"/private",messageDTO);
    }
}
