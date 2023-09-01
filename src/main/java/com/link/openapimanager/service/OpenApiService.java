package com.link.openapimanager.service;

import com.link.openapimanager.entity.dto.CommonResponse;
import com.link.openapimanager.entity.dto.ParamInputDto;

public interface OpenApiService {

    CommonResponse<?> openRequest(ParamInputDto param);
}
