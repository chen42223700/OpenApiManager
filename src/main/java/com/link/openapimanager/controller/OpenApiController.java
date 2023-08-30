package com.link.openapimanager.controller;

import com.link.openapimanager.entity.dto.CommonResponse;
import com.link.openapimanager.entity.dto.ParamInputDto;
import com.link.openapimanager.service.impl.OpenApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController()
@RequestMapping("/open-api")
@AllArgsConstructor
public class OpenApiController {

    private OpenApiService openApiService;

    @GetMapping("/health")
    public void test(){
        System.out.println("Alive...");
    }

    @PostMapping("/openRequest")
    public CommonResponse<?> openRequest(@RequestBody ParamInputDto param){
        return openApiService.openRequest(param);
    }
}
