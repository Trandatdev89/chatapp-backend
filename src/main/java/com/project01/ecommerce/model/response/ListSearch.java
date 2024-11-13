package com.project01.ecommerce.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListSearch{
    private List<UserResponseDTO> users;
    private List<ChatRoomResponse> chats;
}
