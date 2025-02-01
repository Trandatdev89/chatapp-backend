package com.project01.ecommerce.services.impl;

import com.project01.ecommerce.entity.MessageEntity;
import com.project01.ecommerce.model.dto.MessageDTO;
import com.project01.ecommerce.repository.ChatRoomRepository;
import com.project01.ecommerce.repository.MessageRepository;
import com.project01.ecommerce.repository.UserRepository;
import com.project01.ecommerce.services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class MessageServicesImpl implements MessageServices {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;


    @Override
    public MessageDTO saveMessagePrivate(MessageDTO message) {
        MessageEntity messageEntity=MessageEntity.builder()
                .sender(userRepository.findById(message.getSenderId()).get())
                .recipient(userRepository.findById(message.getReceiverId()).get())
                .content(message.getMessage())
                .build();

        messageRepository.save(messageEntity);
        return MessageDTO.builder()
                .id(messageEntity.getId())
                .message(messageEntity.getContent())
                .receiverId(messageEntity.getRecipient().getId())
                .senderId(messageEntity.getSender().getId())
                .build();
    }

    @Override
    public void saveMessagePublic(MessageDTO message) {
        if(message.getStatus().name().equals("MESSAGE")){
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setCreatedDate(new Date());
            messageEntity.setSender(userRepository.findById(message.getSenderId()).get());
            messageEntity.setContent(message.getMessage());
            messageEntity.setChatRoom(chatRoomRepository.findById(message.getChatroomId()).get());
            messageRepository.save(messageEntity);
        }
    }

    @Override
    public List<MessageDTO> getMessageByReciveIdAndSenderId(Long sendId, Long RecipentId) {
        List<MessageDTO> messages=new ArrayList<>();
        List<MessageEntity> messageEntity = messageRepository.findBySender_IdAndRecipient_IdOrRecipient_IdAndSender_Id(sendId, RecipentId, sendId, RecipentId); // Lay het tin nhan giua 2 nguoi con neu khong co or thi no chi lay cua sender
        for(MessageEntity item : messageEntity){
            MessageDTO message=MessageDTO.builder()
                    .id(item.getId())
                    .message(item.getContent())
                    .senderId(item.getSender().getId())
                    .date(item.getCreatedDate())
                    .senderName(item.getRecipient().getUsername())
//                    .picture(item.getSender().getPicture()!=null? ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()+"/avatar/"+item.getSender().getPicture():null)
                    .receiverId(item.getRecipient().getId())
                    .build();
            messages.add(message);
        }
        return messages;
    }

    @Override
    public List<MessageDTO> getMessageByChatRoomId(Long chatRoomId) {
        List<MessageDTO> messages=new ArrayList<>();

        List<MessageEntity> messageEntity = messageRepository.findByChatRoom_Id(chatRoomId);
        for(MessageEntity item : messageEntity){
            MessageDTO message=MessageDTO.builder()
                    .id(item.getId())
                    .message(item.getContent())
                    .senderId(item.getSender().getId())
                    .senderName(item.getSender().getUsername())
                    .date(item.getCreatedDate())
//                    .picture(item.getSender().getPicture()!=null? ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()+"/avatar/"+item.getSender().getPicture():null)
                    .chatroomId(item.getChatRoom().getId())
                    .build();
            messages.add(message);
        }return messages;
    }


}
