package com.scb.assignmentone.model.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ResponseModel<T> implements Serializable {
    private String statusCode = "200";
    private String statusHeader = "success";
    @JsonProperty("data")
    private T dataObj;

    public ResponseModel(String statusCode) {
        this.statusCode = statusCode;
    }

    public ResponseModel(T dataObj) {
        this.dataObj = dataObj;
    }

    public ResponseModel(String statusCode, String statusHeader) {
        this.statusCode = statusCode;
        this.statusHeader = statusHeader;
    }

    public ResponseModel(String statusCode, String statusHeader, T dataObj) {
        this.statusCode = statusCode;
        this.statusHeader = statusHeader;
        this.dataObj = dataObj;
    }
}

