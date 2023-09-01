package com.link.openapimanager.service.impl;

import com.link.openapimanager.entity.dto.CommonResponse;
import com.link.openapimanager.entity.dto.ParamInputDto;
import com.link.openapimanager.init.NacosServerInit;
import com.link.openapimanager.service.OpenApiService;
import lombok.AllArgsConstructor;
import org.json.JSONString;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OpenApiServiceImpl implements OpenApiService {

    private RestTemplate restTemplate;

    private NacosServerInit servers;

    @Override
    public CommonResponse<?> openRequest(ParamInputDto param) {
        //判断服务是否存在
        if (CollectionUtils.isEmpty(servers.getServerSet()) || !servers.getServerSet().contains(param.getOpenApi())){
            return CommonResponse.error("","");
        }
        //组装url
        StringBuilder url = new StringBuilder("http://");
        url.append(param.getOpenApi());
        if (!param.getUrlMap().isEmpty()){
            param.getUrlMap().forEach((k,v) -> {
                url.append("?").append(k).append("=").append(v);
            });
        }

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(param.getHeadMap());
        if (CollectionUtils.isEmpty(param.getHeadMap())){
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        // 设置请求体
        HttpEntity<JSONString> requestEntity = new HttpEntity<>(param.getBodyParam(), headers);
        // 发送请求
        ResponseEntity<byte[]> msg = restTemplate.exchange(url.toString(), param.getHttpMethod(), requestEntity, byte[].class);
        // 解码返回值
        String resp = "";
        if (Objects.nonNull(msg.getBody())){
            Charset charset = Optional.ofNullable(Charset.availableCharsets().get(param.getCharSet())).orElse(Charset.defaultCharset());
            resp = new String(msg.getBody(), charset);
        }

        return new CommonResponse<>(true, "", "", resp);
    }
}
