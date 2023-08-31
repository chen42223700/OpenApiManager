package com.link.openapimanager.init;

import com.link.openapimanager.entity.dto.NacosDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
@EnableScheduling
@NoArgsConstructor
@Slf4j
public class NacosServerInit {

    private Set<String> serverSet;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.nacos.discovery.server-addr}")
    private String nacosIp;

    public Set<String> getServerSet() {
        return serverSet;
    }

    @Scheduled(fixedDelay = 1000 * 60)
    public void refreshServerSet(){
        this.serverSet = Collections.EMPTY_SET;
        try {
            StringBuilder url = new StringBuilder();
            url.append("http://");
            url.append(nacosIp);
            url.append("/nacos/v1/ns/service/list?pageNo=%d&pageSize=%d");
            NacosDto nacosDto = restTemplate.getForObject(String.format(url.toString(),0, 0), NacosDto.class);
            if (Objects.isNull(nacosDto)){
                return;
            }
            nacosDto = restTemplate.getForObject(String.format(url.toString(),1, nacosDto.getCount()), NacosDto.class);
            this.serverSet = new HashSet<>(nacosDto.getDoms());
        } catch (Exception e) {
            log.error("NacosServer pull failed:{0}", e);
        }
    }
}
