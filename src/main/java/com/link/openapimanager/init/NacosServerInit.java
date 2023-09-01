package com.link.openapimanager.init;

import com.link.openapimanager.module.server.IRemoteServers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@EnableScheduling
@AllArgsConstructor
@Slf4j
public class NacosServerInit {

    private Set<String> serverSet;

    private final IRemoteServers remoteServers;

    public Set<String> getServerSet() {
        return serverSet;
    }

    @Scheduled(fixedDelay = 1000 * 60)
    public void refreshServerSet(){
        this.serverSet = remoteServers.getRemoteServers();
    }
}
