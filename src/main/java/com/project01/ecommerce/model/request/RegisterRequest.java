package com.project01.ecommerce.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "username thì phải 8 ký tự ")
    private String username;

    @NotBlank(message = "passowrd thì phải có 8 ký tự tro lên ")
    private String password;
    private String email;
    private String phone;
    private String fullname;
    private String role;
}
