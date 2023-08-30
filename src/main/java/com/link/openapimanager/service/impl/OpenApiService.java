package com.link.openapimanager.service.impl;

import com.link.openapimanager.entity.dto.CommonResponse;
import com.link.openapimanager.entity.dto.ParamInputDto;

public interface OpenApiService {

    CommonResponse openRequest(ParamInputDto param);
}
