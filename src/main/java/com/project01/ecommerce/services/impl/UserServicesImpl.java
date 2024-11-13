package com.project01.ecommerce.services.impl;

import com.project01.ecommerce.convertor.UserDTOConvertor;
import com.project01.ecommerce.entity.ChatRoomEntity;
import com.project01.ecommerce.entity.UserEntity;
import com.project01.ecommerce.entity.UserRole;
import com.project01.ecommerce.enums.EnumException;
import com.project01.ecommerce.exception.CustomException.AppException;
import com.project01.ecommerce.model.request.AddUserRequest;
import com.project01.ecommerce.model.request.SearchRequest;
import com.project01.ecommerce.model.request.UserRequest;
import com.project01.ecommerce.model.response.ChatRoomResponse;
import com.project01.ecommerce.model.response.ListSearch;
import com.project01.ecommerce.model.response.UserResponseDTO;
import com.project01.ecommerce.repository.ChatRoomRepository;
import com.project01.ecommerce.repository.UserRepository;
import com.project01.ecommerce.repository.UserRoleRepository;
import com.project01.ecommerce.services.UserServices;
import com.project01.ecommerce.utils.UploadfileUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@Transactional
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTOConvertor userDTOConvertor;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UploadfileUtil uploadfileUtil;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        for (UserEntity user : users) {
            UserResponseDTO userResponseDTO=userDTOConvertor.toUserDTOResponse(user);
            userResponseDTOS.add(userResponseDTO);
        }
        return userResponseDTOS;
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        UserEntity user=userRepository.findById(id).get();
        UserResponseDTO userResponseDTO=userDTOConvertor.toUserDTOResponse(user);
        return userResponseDTO;
    }

    @Override
    public List<UserResponseDTO> getUserByChatroomId(Long id) {
        List<UserRole> userRoles=userRoleRepository.findByChatRoom_Id(id);
        List<UserResponseDTO> userResponseDTOS=new ArrayList<>();
        for (UserRole item : userRoles) {
            UserEntity user=userRepository.findById(item.getUser().getId()).get();
            UserResponseDTO userResponseDTO=userDTOConvertor.toUserDTOResponse(user);
            userResponseDTOS.add(userResponseDTO);
        }
        return userResponseDTOS;
    }

    @Override
    public UserResponseDTO getInfoUser() {
        Long userId=Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        UserEntity user=userRepository.findById(userId).get();
        UserResponseDTO userResponseDTO=userDTOConvertor.toUserDTOResponse(user);
        return userResponseDTO;
    }

    @Override
    public ListSearch getResult(SearchRequest search) {
        ListSearch listSearch=new ListSearch();
        if(userRepository.findByUsername(search.getTitle()).isPresent()){
            UserEntity user=userRepository.findByUsername(search.getTitle()).get();
            UserResponseDTO userResponseDTO=userDTOConvertor.toUserDTOResponse(user);
            listSearch.setUsers(List.of(userResponseDTO));
        }
        if(chatRoomRepository.findByName(search.getTitle()).isPresent()){
            ChatRoomEntity chat=chatRoomRepository.findByName(search.getTitle()).get();
            ChatRoomResponse chatRoomResponse=ChatRoomResponse.builder()
                    .id(chat.getId())
                    .name(chat.getName())
                    .build();
            listSearch.setChats(List.of(chatRoomResponse));
        }
       return listSearch;
    }

    @Override
    public void updateUser(Long id, UserRequest user){
        UserEntity users=userRepository.findById(id).orElseThrow(()->new AppException(EnumException.USER_NOTFOUND));
        users.setUsername(user.getUsername());
        users.setEmail(user.getEmail());
        users.setFullname(user.getFullname());
        users.setPhone(user.getPhone());
        try {
            users.setPicture(uploadfileUtil.uploadFile(user.getPicture()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userRepository.save(users);
    }

    @Override
    public void leave(AddUserRequest addUserRequest) {
        userRoleRepository.deleteByUser_IdInAndChatRoomId(addUserRequest.getIdUser(),addUserRequest.getChatRoomId());
    }
}
