package com.textadventure.textProcessor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TextRequest {
    private String message;
    private int currentRoom;
}
