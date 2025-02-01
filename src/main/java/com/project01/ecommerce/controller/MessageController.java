package com.project01.ecommerce.controller;


import com.project01.ecommerce.model.dto.MessageDTO;
import com.project01.ecommerce.model.response.ApiResponse;
import com.project01.ecommerce.services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/message")
public class MessageController {

    @Autowired
    private MessageServices messageServices;

    @PostMapping
    public List<MessageDTO> getMessageByReciveIdAndSenderId(@RequestBody MessageDTO message) {
        List<MessageDTO> messageDTOS=messageServices.getMessageByReciveIdAndSenderId(message.getSenderId(),message.getReceiverId());
        return messageDTOS;
    }

}
