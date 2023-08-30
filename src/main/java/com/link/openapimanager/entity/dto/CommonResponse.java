package com.link.openapimanager.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private boolean successFlag;

    private String errorCode;

    private String errorMsg;

    private T data;

    public static CommonResponse<?> error(String errorCode, String errorMsg){
        CommonResponse<?> response = new CommonResponse<>();
        response.setSuccessFlag(false);
        response.setErrorCode(errorCode);
        response.setErrorMsg(errorMsg);
        return response;
    }
}
