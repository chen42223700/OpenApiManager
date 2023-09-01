package com.link.openapimanager.module.server.nacos;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import com.link.openapimanager.entity.dto.NacosDto;
import com.link.openapimanager.module.server.IRemoteServers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
@ConditionalOnClass(NacosDiscoveryClient.class)
public class NacosRemoteServerImpl implements IRemoteServers {

    @Value("${spring.application.nacos.discovery.server-addr}")
    private String nacosIp;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Set<String> getRemoteServers() {
        StringBuilder url = new StringBuilder("http://");
        url.append(nacosIp);
        url.append("/nacos/v1/ns/service/list?pageNo=%d&pageSize=%d");
        NacosDto nacosDto = null;
        try {
            nacosDto = restTemplate.getForObject(String.format(url.toString(), 0, 0), NacosDto.class);
        } catch (Exception e) {
            log.error("NacosServer pull failed: {0}", e);
            return Collections.emptySet();
        }
        if (nacosDto == null) {
            log.info("Alive Server is Empty");
            return Collections.emptySet();
        }
        nacosDto = restTemplate.getForObject(String.format(url.toString(), 1, nacosDto.getCount()), NacosDto.class);
        return new HashSet<>(nacosDto.getDoms());
    }
}
