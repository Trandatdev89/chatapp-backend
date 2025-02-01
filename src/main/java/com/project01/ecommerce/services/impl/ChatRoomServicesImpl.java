package com.project01.ecommerce.services.impl;

import com.project01.ecommerce.entity.ChatRoomEntity;
import com.project01.ecommerce.entity.UserEntity;
import com.project01.ecommerce.entity.UserRole;
import com.project01.ecommerce.enums.EnumException;
import com.project01.ecommerce.exception.CustomException.AppException;
import com.project01.ecommerce.model.dto.ChatRoomDTO;
import com.project01.ecommerce.model.request.AddUserRequest;
import com.project01.ecommerce.model.request.ChatRoomRequest;
import com.project01.ecommerce.model.response.ChatRoomResponse;
import com.project01.ecommerce.repository.ChatRoomRepository;
import com.project01.ecommerce.repository.MessageRepository;
import com.project01.ecommerce.repository.UserRepository;
import com.project01.ecommerce.repository.UserRoleRepository;
import com.project01.ecommerce.services.ChatRoomServices;
import com.project01.ecommerce.utils.UploadfileUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChatRoomServicesImpl implements ChatRoomServices {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UploadfileUtil uploadfileUtil;

    @Override
    public ChatRoomResponse createChatRoom(ChatRoomRequest chatRoomRequest) {

        String userId= SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user=userRepository.findById(Long.parseLong(userId)).get();

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .name(chatRoomRequest.getName())
                .picture(uploadfileUtil.uploadFile(chatRoomRequest.getPicture()))
                .createBy(user.getUsername())
                .build();

        ChatRoomEntity chatRoomEntity1= chatRoomRepository.save(chatRoomEntity);

        List<UserEntity> userList=userRepository.findByIdIn(chatRoomRequest.getUserId());
        List<UserRole> userRoles = new ArrayList<>();
        for(UserEntity item : userList){
            if(userRoleRepository.existsByChatRoom_IdAndUser_Id(chatRoomEntity.getId(), item.getId())){
                throw new AppException(EnumException.USER_EXITSED);
            }else{
                UserRole userRole1 = UserRole.builder()
                        .chatRoom(chatRoomEntity1)
                        .user(item)
                        .role("MEMBER")
                        .build();
                userRoles.add(userRole1);
            }
        }
        userRoleRepository.saveAll(userRoles);

        ChatRoomResponse chatRoomResponse= ChatRoomResponse.builder()
                .name(chatRoomEntity1.getName())
                .id(chatRoomEntity1.getId())
                .build();
        return chatRoomResponse;
    }


    @Override
    public List<ChatRoomResponse> getChatRoomByUser() {
        String userId=SecurityContextHolder.getContext().getAuthentication().getName();
        List<UserRole> userRoles=userRoleRepository.findByUser_Id(Long.parseLong(userId));
        List<ChatRoomResponse> listResponse=new ArrayList<>();
        for(UserRole item : userRoles){
            if(item.getChatRoom()!=null){
                listResponse.add(new ChatRoomResponse(item.getChatRoom().getId(),item.getChatRoom().getName(), item.getChatRoom().getPicture()));
            }
        }
        return listResponse;
    }

    @Override
    public ChatRoomResponse getChatRoomById(Long id) {
        ChatRoomEntity list=chatRoomRepository.findById(id).get();
        ChatRoomResponse listResponse=new ChatRoomResponse(list.getId(),list.getName(), list.getPicture());
        return listResponse;
    }

    @Override
    public void deleteChatRoom(List<Long> id) {
        chatRoomRepository.deleteByIdIn(id);
    }

    @Override
    public void block(AddUserRequest addUserRequest) {
        userRoleRepository.deleteByUser_IdInAndChatRoomId(addUserRequest.getIdUser(),addUserRequest.getChatRoomId());
    }

    @Override
    public void updateChatRoom(Long id, AddUserRequest addUserRequest) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(id).get();
        List<UserRole> userRoles= chatRoomEntity.getRoles();
        List<UserEntity> listUser=userRepository.findByIdIn(addUserRequest.getIdUser());
        for(UserEntity item : listUser){
            if(userRoleRepository.existsByChatRoom_IdAndUser_Id(chatRoomEntity.getId(), item.getId())){
                throw new AppException(EnumException.USER_EXITSED);
            }
            else{
                UserRole userRole=UserRole.builder()
                        .chatRoom(chatRoomEntity)
                        .user(item)
                        .role("MEMBER")
                        .build();
                userRoles.add(userRole);
            }
        }
        userRoleRepository.saveAll(userRoles);
    }
}
