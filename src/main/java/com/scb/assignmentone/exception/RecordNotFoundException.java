package com.scb.assignmentone.exception;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true)
public class RecordNotFoundException extends RuntimeException {
    private final String code;

    public RecordNotFoundException(Integer code) {
        super(String.valueOf(code));
        this.code = String.valueOf(code);
    }

    public RecordNotFoundException(String code) {
        super(code);
        this.code = code;
    }

    public RecordNotFoundException(Integer code, String message) {
        super(message);
        this.code = String.valueOf(code);
    }

    public RecordNotFoundException(Integer code, String message, Throwable ex) {
        super(message, ex);
        this.code = String.valueOf(code);
    }

}