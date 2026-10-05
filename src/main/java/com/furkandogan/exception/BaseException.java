package com.furkandogan.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final MessageType messageType;

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.prepareErrorMessage());
        this.messageType = errorMessage.getMessageType();
    }
}