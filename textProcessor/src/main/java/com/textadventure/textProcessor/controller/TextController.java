package com.textadventure.textProcessor.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TextController {

    @GetMapping(path = "/hello")
    public String helloWorld() { return "hello world"; }

    @PutMapping(path = "/simon")
    public Map<String, String> simonSays(@RequestBody Map<String, Object> requestBody) {
        Object simonValue = requestBody.get("simon");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Simon says: " + (simonValue != null ? simonValue.toString() : "nothing"));

        return response;
    }
}
