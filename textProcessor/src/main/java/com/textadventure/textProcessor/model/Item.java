package com.textadventure.textProcessor.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Item implements Serializable {
    private String name;
    private String description;
    private boolean attack;
    private boolean equipable;
}
