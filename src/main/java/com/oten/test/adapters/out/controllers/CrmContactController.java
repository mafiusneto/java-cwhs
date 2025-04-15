package com.oten.test.adapters.out.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oten.test.domain.models.dto.DtoContactRequest;
import com.oten.test.ports.out.HubspotContactServicePort;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/crm")
public class CrmContactController {

    private final HubspotContactServicePort service;

    public CrmContactController(HubspotContactServicePort service) {
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

    private String stripBearer(String header) {
        return header.replace("Bearer ", "").trim();
    }

}
