package com.link.openapimanager.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class NacosDto {

    private int count;

    private List<String> doms;
}
