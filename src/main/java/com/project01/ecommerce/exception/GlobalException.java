package com.project01.ecommerce.exception;

import com.project01.ecommerce.enums.EnumException;
import com.project01.ecommerce.exception.CustomException.AppException;
import com.project01.ecommerce.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleException(MethodArgumentNotValidException ex) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getBindingResult().getFieldError().getDefaultMessage())
                .code(401)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> CustomException(AppException ex) {
        EnumException errorCode = ex.getEnumException();

        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();
        return  ResponseEntity.status(errorCode.getCode()).body(apiResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> AccessDenied(AccessDeniedException ex) {
        EnumException errorCode=EnumException.UNAUTHOUZATED;
        ApiResponse apiResponse = ApiResponse.builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();
        return  ResponseEntity.status(errorCode.getCode()).body(apiResponse);
    }
}
