package com.textadventure.textProcessor.model;

import lombok.Data;

@Data
public class Action {
    Room startingRoom;
    Room endingRoom;
    String verb;
    String adverb;
    String object;
    boolean success;
    String message;
}
