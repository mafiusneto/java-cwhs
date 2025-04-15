package com.oten.test.adapters.out.controllers;

import java.net.URI;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.oten.test.domain.models.HubspotOAuthConfig;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {
    private final HubspotOAuthConfig  config;
    private final RestTemplate restTemplate = new RestTemplate();

    /*
    Geração da Authorization URL:
    Endpoint responsável por gerar e retornar a URL de autorização para iniciar o fluxo OAuth com o HubSpot.
    */ 
    @GetMapping("/authorize")
    public ResponseEntity<Void> hubspotAuthorize() {
        System.out.println("oauth/authorize");
        String url = "https://app.hubspot.com/oauth/authorize";
        String userId = config.getUserId();
        if (Strings.isNotEmpty(userId) && Strings.isNotBlank(userId) && userId.length() > 3){
            url = "https://app.hubspot.com/oauth/" + config.getUserId()+ "/authorize";
        }
        String authUrl = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("client_id", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", config.getScopes())
                .build()
                .toUriString();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(authUrl)).build();
    }

    /*
    Processamento do Callback OAuth:
    Endpoint recebe o código de autorização fornecido pelo HubSpot e realiza a troca pelo token de acesso.
     */
    @GetMapping("/callback")
    public ResponseEntity<String> hubspotCallback(@RequestParam String code) {
        System.out.println("oauth/callback");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", config.getClientId());
        body.add("client_secret", config.getClientSecret());
        body.add("redirect_uri", config.getRedirectUri());
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.hubapi.com/oauth/v1/token",
                request,
                String.class
        );
        
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> hubspotRefreshToken(@RequestParam String code) {
        System.out.println("oauth/refresh");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("client_id", config.getClientId());
        body.add("client_secret", config.getClientSecret());
        body.add("redirect_uri", config.getRedirectUri());
        body.add("refresh_token", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.hubapi.com/oauth/v1/token",
                request,
                String.class
        );
        

        return ResponseEntity.ok(response.getBody());
    }
}
