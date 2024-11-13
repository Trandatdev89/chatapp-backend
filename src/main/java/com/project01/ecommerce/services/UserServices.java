package com.project01.ecommerce.services;

import com.project01.ecommerce.model.request.AddUserRequest;
import com.project01.ecommerce.model.request.SearchRequest;
import com.project01.ecommerce.model.request.UserRequest;
import com.project01.ecommerce.model.response.ListSearch;
import com.project01.ecommerce.model.response.UserResponseDTO;

import java.util.List;

public interface UserServices {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getUserByChatroomId(Long id);
    UserResponseDTO getInfoUser();
    ListSearch getResult(SearchRequest search);
    void updateUser(Long id, UserRequest user);
    void leave(AddUserRequest addUserRequest);
}
