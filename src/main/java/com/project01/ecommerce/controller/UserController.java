package com.project01.ecommerce.controller;


import com.project01.ecommerce.model.request.AddUserRequest;
import com.project01.ecommerce.model.request.SearchRequest;
import com.project01.ecommerce.model.request.UserRequest;
import com.project01.ecommerce.model.response.ApiResponse;
import com.project01.ecommerce.model.response.ListSearch;
import com.project01.ecommerce.model.response.UserResponseDTO;
import com.project01.ecommerce.repository.UserRepository;
import com.project01.ecommerce.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {


    @Autowired
    private UserServices userServices;

    @GetMapping
    public ApiResponse<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users=userServices.getAllUsers();

        return ApiResponse.<List<UserResponseDTO>>builder()
                .code(200)
                .message("OK")
                .data(users)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO users=userServices.getUserById(id);
        return ApiResponse.<UserResponseDTO>builder()
                .code(200)
                .message("OK")
                .data(users)
                .build();
    }

    @GetMapping("/chatroom/{id}")
    public ApiResponse<List<UserResponseDTO>> getUserByChatroomId(@PathVariable Long id) {
        List<UserResponseDTO> userResponseDTOS=userServices.getUserByChatroomId(id);
        return ApiResponse.<List<UserResponseDTO>>builder()
                .code(200)
                .message("OK")
                .data(userResponseDTOS)
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse updateUser(@PathVariable Long id, @ModelAttribute UserRequest user) {
        userServices.updateUser(id,user);
        return ApiResponse.builder()
                .code(200)
                .message("OK")
                .build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponseDTO> getInfoUser() {
        UserResponseDTO userResponseDTO=userServices.getInfoUser();
        return ApiResponse.<UserResponseDTO>builder()
                .code(200)
                .message("OK")
                .data(userResponseDTO)
                .build();
    }

    @PostMapping("/search")
    public ApiResponse<ListSearch> getResult(@RequestBody SearchRequest search) {
        ListSearch result=userServices.getResult(search);
        return ApiResponse.<ListSearch>builder()
                .code(200)
                .message("OK")
                .data(result)
                .build();
    }

    @PostMapping("/leave")
    public ApiResponse leave(@RequestBody AddUserRequest addUserRequest){
        userServices.leave(addUserRequest);
        return ApiResponse.builder()
                .code(200)
                .message("leave success!")
                .build();
    }
}
