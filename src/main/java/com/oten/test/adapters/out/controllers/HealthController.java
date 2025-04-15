package com.oten.test.adapters.out.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> map = new HashMap<>();
        map.put("status", "ok");
        return map;
    }
}
