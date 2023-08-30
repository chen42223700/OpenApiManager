package com.link.openapimanager.entity.dto;

import lombok.Data;
import org.json.JSONString;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Data
public class ParamInputDto {

    private String openApi;

    private Map<String, String> headMap;

    private Map<String, String> urlMap;

    private JSONString bodyParam;

    private String charSet;

    private HttpMethod httpMethod;
}
