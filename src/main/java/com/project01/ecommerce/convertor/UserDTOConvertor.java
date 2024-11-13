package com.project01.ecommerce.convertor;

import com.project01.ecommerce.entity.UserEntity;
import com.project01.ecommerce.model.response.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOConvertor {

    @Autowired
    private ModelMapper modelMapper;

    public UserResponseDTO toUserDTOResponse(UserEntity item){
        UserResponseDTO userResponseDTO = modelMapper.map(item, UserResponseDTO.class);
        if(item.getPicture()!= null && !item.getPicture().equals("")){
            String picture= ServletUriComponentsBuilder.fromCurrentContextPath().toUriString()+"/avatar/"+item.getPicture();
            userResponseDTO.setPicture(picture);
        }
        if(item.getGoogleid() != null && !item.getGoogleid().equals("")){
            userResponseDTO.setPicture(item.getPicture());
        }
        return userResponseDTO;
    }
}
