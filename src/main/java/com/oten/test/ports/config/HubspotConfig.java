package com.oten.test.ports.config;

import org.springframework.context.annotation.Configuration;

import com.oten.test.domain.services.HubspotContactService;
import com.oten.test.ports.out.HubspotContactServicePort;

@Configuration
public class HubspotConfig {
    
    public HubspotContactServicePort hubspotContactServicePort(){
        return new HubspotContactService();
    }
}
