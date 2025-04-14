package com.oten.test.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.oten.test.models.dto.DtoContactRequest;

@Service
public class HubspotContactService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL_BASE = "https://api.hubapi.com/crm/v3/objects/contacts";

    public ResponseEntity<String> listContacts(String token) {
        return exchangeWithToken(token, URL_BASE, HttpMethod.GET, null);
    }

    public ResponseEntity<String> getContactById(String token, String id) {
        String url = URL_BASE + "/" + id;
        return exchangeWithToken(token, url, HttpMethod.GET, null);
    }

    public ResponseEntity<String> createContact(String token, DtoContactRequest contact) {
        String payload = """
        {
          "properties": {
            "firstname": "%s",
            "lastname": "%s",
            "email": "%s"
          }
        }
        """.formatted(contact.firstName(), contact.lastName(), contact.email());

        return exchangeWithToken(token, URL_BASE, HttpMethod.POST, payload);
    }

    
    public ResponseEntity<String> updateContact(String token, String id, DtoContactRequest contact) {
        ///crm/v3/objects/contacts/{email}?idProperty=email outra opção
        //https://developers.hubspot.com/docs/guides/api/crm/objects/contacts#update-contacts
        // String payntact.firstName(), contact.lastName(), contact.email());

        String payload = """
        {
          "properties": {
            "favorite_food": "burger",
            "jobtitle": "Manager",
            "lifecyclestage": "Customer"
          }
        }
        """;

        String url = URL_BASE + "/" + id;
        return exchangeWithToken(token, url, HttpMethod.PATCH, payload);
    }

    
    public ResponseEntity<String> deleteContact(String token, String id) {
        // "error": "Erro na requisição: 415 UNSUPPORTED_MEDIA_TYPE"

        String url = URL_BASE + "/" + id;
        return exchangeWithToken(token, url, HttpMethod.DELETE, null);
        // HttpHeaders headers = new HttpHeaders();
        // headers.set("Authorization", "Bearer " + token);
        // // headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        // headers.setAccept(List.of(MediaType.ALL));
        // HttpEntity<Void> entity = new HttpEntity<>(headers);
        
        // return restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);

        // onde achar os parametros?
        // String payload = """
        // {
        // "idProperty": "id",
        // "objectId": "%s"
        // }
        // """.formatted(id);
        // String url = URL_BASE + "/gdpr-delete";
        // return exchangeWithToken(token, url, HttpMethod.POST, payload);
    }

    // TODO add em lote?
    private ResponseEntity<String> exchangeWithToken(String token, String url, HttpMethod method, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        if (body != null) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        
        return restTemplate.exchange(url, method, entity, String.class);
    }
    
}
