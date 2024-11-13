package com.project01.ecommerce.exception.CustomException;

import com.project01.ecommerce.enums.EnumException;

public class AppException extends RuntimeException {

    private EnumException enumException;

    public AppException(EnumException enumException) {
        this.enumException = enumException;
    }

    public EnumException getEnumException() {
        return enumException;
    }

    public void setEnumException(EnumException enumException) {
        this.enumException = enumException;
    }
}
