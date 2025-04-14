package com.oten.test.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.oten.test.models.dto.DtoContactRequest;
import com.oten.test.services.HubspotContactService;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/crm")
public class CrmContactController {

    private final HubspotContactService service;

    public CrmContactController(HubspotContactService service) {
        this.service = service;
    }

    @GetMapping("/contacts")
    public ResponseEntity<String> getAll(@RequestHeader("Authorization") String token) {
        System.out.println("crm/contacts");
        return service.listContacts(stripBearer(token));
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<String> getById(@RequestHeader("Authorization") String token, @PathVariable String id) {
        System.out.println("getContact- id:" +id);
        return service.getContactById(stripBearer(token), id);
    }

    @PostMapping("/contact")
    public ResponseEntity<String> create(@RequestHeader("Authorization") String token, @RequestBody DtoContactRequest contact) {
        System.out.println("crateContact");
        return service.createContact(stripBearer(token), contact);
    }

    @PatchMapping("/contact/{id}")
    public ResponseEntity<String> update(@RequestHeader("Authorization") String token,
                                         @PathVariable String id,
                                         @RequestBody DtoContactRequest contact) {
        System.out.println("updateContact- id:" +id);
        return service.updateContact(stripBearer(token), id, contact);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String token, @PathVariable String id) {
        System.out.println("deleteContact- id:" +id);
        return service.deleteContact(stripBearer(token), id);
    }

    private String stripBearer(String header) {
        return header.replace("Bearer ", "").trim();
    }

}
