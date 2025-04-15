package com.oten.test.ports.out;

import org.springframework.http.ResponseEntity;

import com.oten.test.domain.models.dto.DtoContactRequest;

public interface HubspotContactServicePort {
    ResponseEntity<String> listContacts(String token);
    ResponseEntity<String> getContactById(String token, String id);
    ResponseEntity<String> createContact(String token, DtoContactRequest contact);
    
}
