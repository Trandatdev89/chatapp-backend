package com.project01.ecommerce.services;

import com.project01.ecommerce.model.dto.ChatRoomDTO;
import com.project01.ecommerce.model.request.AddUserRequest;
import com.project01.ecommerce.model.request.ChatRoomRequest;
import com.project01.ecommerce.model.response.ChatRoomResponse;

import java.util.List;

public interface ChatRoomServices {
    ChatRoomResponse createChatRoom(ChatRoomRequest chatRoomRequest);
    List<ChatRoomResponse> getChatRoomByUser();
    ChatRoomResponse getChatRoomById(Long id);
    void deleteChatRoom(List<Long> id);
    void block(AddUserRequest addUserRequest);
    void updateChatRoom(Long id, AddUserRequest addUserRequest);
}
