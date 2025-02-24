package com.textadventure.textProcessor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TextResponse {
    private String message;
    private int inRoom;
}
