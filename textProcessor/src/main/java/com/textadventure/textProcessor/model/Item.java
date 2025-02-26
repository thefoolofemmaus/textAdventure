package com.textadventure.textProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    private String name;
    private String description;
    private boolean attack;
    private boolean equipable;
}
