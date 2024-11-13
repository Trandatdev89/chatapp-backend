package com.project01.ecommerce.controller;

import com.project01.ecommerce.model.dto.ChatRoomDTO;
import com.project01.ecommerce.model.request.AddUserRequest;
import com.project01.ecommerce.model.request.ChatRoomRequest;
import com.project01.ecommerce.model.response.ApiResponse;
import com.project01.ecommerce.model.response.ChatRoomResponse;
import com.project01.ecommerce.repository.ChatRoomRepository;
import com.project01.ecommerce.services.ChatRoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/chatroom")
public class ChatRoomController {

    @Autowired
    private ChatRoomServices chatRoomServices;

    @GetMapping
    public ApiResponse<List<ChatRoomResponse>> getChatRoomByUser() {
        List<ChatRoomResponse> chatRoomResponses=chatRoomServices.getChatRoomByUser();
        return ApiResponse.<List<ChatRoomResponse>>builder()
                .data(chatRoomResponses)
                .code(200)
                .message("Success!")
                .build();
    }


    @GetMapping("/{id}")
    public ApiResponse<ChatRoomResponse> getChatRoomById(@PathVariable Long id){
        ChatRoomResponse chatRoomResponses=chatRoomServices.getChatRoomById(id);
        return ApiResponse.<ChatRoomResponse>builder()
                .data(chatRoomResponses)
                .code(200)
                .message("Success!")
                .build();
    }


    @PostMapping
    public ApiResponse<ChatRoomResponse> createChatRoom(@ModelAttribute ChatRoomRequest chatRoomRequest){
        return ApiResponse.<ChatRoomResponse>builder()
                .code(200)
                .data(chatRoomServices.createChatRoom(chatRoomRequest))
                .message("Chat room created")
                .build();
    }

    @PostMapping("/user")
    public ApiResponse addUserToChatRoom(@RequestBody AddUserRequest addUserRequest){
        chatRoomServices.addUserToChatRoom(addUserRequest);
        return ApiResponse.builder()
                .code(200)
                .message("Add success!")
                .build();
    }


    @DeleteMapping("/{id}")
    public ApiResponse deleteChatRoom(@PathVariable List<Long> id){
        chatRoomServices.deleteChatRoom(id);
        return ApiResponse.builder()
                .code(200)
                .message("delete success!")
                .build();
    }

    @PostMapping("/block")
    public ApiResponse block(@RequestBody AddUserRequest addUserRequest){
        chatRoomServices.block(addUserRequest);
        return ApiResponse.builder()
                .code(200)
                .message("block success!")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse updateChatRoom(@PathVariable Long id,@RequestBody AddUserRequest addUserRequest){
        chatRoomServices.updateChatRoom(id,addUserRequest);
        return ApiResponse.builder()
                .code(200)
                .message("update success!")
                .build();
    }
}
