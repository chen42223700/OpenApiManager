package com.link.openapimanager.handle;

import com.link.openapimanager.entity.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandle {

    /**
     * 统一异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleCustomException(Exception ex) {
        // 创建一个自定义的错误响应对象
        CommonResponse error = new CommonResponse(false, "", ex.getMessage(), null);
        // 返回自定义的错误响应和适当的HTTP状态码
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
