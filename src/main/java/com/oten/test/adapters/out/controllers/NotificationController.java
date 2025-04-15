package com.oten.test.adapters.out.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/notification")
public class NotificationController {
    

    @PostMapping
    public ResponseEntity<String> receiveNotification(@RequestBody Map<String, Object> entity) {
        System.out.println("/notification");        
        System.out.println("entity: " + entity);
        if(entity.containsKey("type")){
            Object typeValue = entity.get("type");
            /*
            melhoria: 
            - usar strategy, onde o tipo de evento recebido indica qual fluxo a ser seguindo
            - onde cada tipo tem seu fluxo apartado sem impacatar os demais fluxos mesmo que novos sejam criados
            - seguindo boas praticas
            */
            if("contact.creation".equalsIgnoreCase(String.valueOf(typeValue))){
                // TODO fluxo para o tipo contact.creation                                
                return ResponseEntity.ok("");
            }else {
                return ResponseEntity.badRequest().body("Campo 'type' com valor não mapeado.");
            }
        }else{
            return ResponseEntity.badRequest().body("Campo 'type' é obrigatório.");
        }

    }

    /* opção mais simplista e limitado */
    @GetMapping
    public ResponseEntity<String> receiveNotification(@RequestParam("type") String type, @RequestParam("ref") String ref) {
        System.out.println("/notification");        
        System.out.println("Params- type:" + type + ", id: " + ref);
        if("contact.creation".equalsIgnoreCase(type)){
            // TODO fluxo para o tipo contact.creation              
            return ResponseEntity.ok("");
        }else {
            return ResponseEntity.badRequest().body("Campo 'type' com valor não mapeado.");
        }
    }
    
}
