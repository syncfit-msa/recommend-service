package com.amcamp.infra.config.chat;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gemini")
public record ChatProperties (
    String baseurl,
    String apiKey
){

}