package com.project01.ecommerce.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {
    @NotBlank(message = "Token thì không được null hoặc rỗng!")
    private String token;
}
