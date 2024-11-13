package com.project01.ecommerce.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.awt.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MessageDTO {
    private Long id;
    private String message;
    private Long senderId;
    private Long chatroomId;
    private Long receiverId;
    private Date date;
    private Status status;
    private String senderName;
    private String picture;
}
