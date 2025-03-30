package com.amcamp.infra.config.properties;

import com.amcamp.infra.config.chat.ChatProperties;
import com.amcamp.infra.config.jwt.JwtProperties;
import com.amcamp.infra.config.oauth.KakaoProperties;
import com.amcamp.infra.config.spotify.SpotifyProperties;
import com.amcamp.infra.config.youtube.YoutubeProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
        KakaoProperties.class,
        JwtProperties.class,
        ChatProperties.class,
        SpotifyProperties.class,
        YoutubeProperties.class
})
@Configuration
public class PropertiesConfig {
}