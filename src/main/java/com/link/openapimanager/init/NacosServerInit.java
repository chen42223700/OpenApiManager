package com.link.openapimanager.init;

import com.link.openapimanager.entity.dto.NacosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class NacosServerInit {

    private Set<String> serverSet;

    private RestTemplate restTemplate;

    @Value("${spring.application.nacos.discovery.server-addr}")
    private String nacosIp;

    @Autowired
    private NacosServerInit(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bean
    private void initServerSet(){
        StringBuilder url = new StringBuilder();
        url.append("http://");
        url.append(nacosIp);
        url.append("/nacos/v1/ns/service/list?pageNo=%d&pageSize=%d");
        NacosDto nacosDto = restTemplate.getForObject(String.format(url.toString(),0, 0), NacosDto.class);
        if (Objects.isNull(nacosDto)){
            this.serverSet = Collections.EMPTY_SET;
        }
        nacosDto = restTemplate.getForObject(String.format(url.toString(),1, nacosDto.getCount()), NacosDto.class);
        this.serverSet = new HashSet<>(nacosDto.getDoms());
    }

    public Set<String> getServerSet() {
        return serverSet;
    }
}
