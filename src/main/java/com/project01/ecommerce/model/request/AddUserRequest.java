package com.project01.ecommerce.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest {
    private List<Long> idUser=new ArrayList<Long>();
    private Long chatRoomId;
}
