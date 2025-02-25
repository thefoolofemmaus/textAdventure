package com.textadventure.textProcessor.controller;

import com.textadventure.textProcessor.dto.TextRequest;
import com.textadventure.textProcessor.dto.TextResponse;
import com.textadventure.textProcessor.service.TextProcessorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class TextController {

    private final TextProcessorService textProcessorService;

    @GetMapping(path = "/hello")
    public String helloWorld() { return "hello world"; }

    @PutMapping(path = "/simon")
    public Map<String, String> simonSays(@RequestBody Map<String, Object> requestBody) {
        Object simonValue = requestBody.get("simon");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Simon says: " + (simonValue != null ? simonValue.toString() : "nothing"));

        return response;
    }

    @PostMapping(path = "/say")
    public TextResponse saySomething(@RequestBody TextRequest textRequest) {
        return textProcessorService.processMessage(textRequest);
    }

    @PutMapping(path = "/addRoom")
    public void putTheRoom() {
        textProcessorService.putTheRoom();
    }
}
