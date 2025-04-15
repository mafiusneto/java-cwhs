package com.oten.test.domain.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "hubspot")
@Getter @Setter
public class HubspotOAuthConfig {
    private String userId;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scopes;
}
