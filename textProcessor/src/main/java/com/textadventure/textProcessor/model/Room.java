package com.textadventure.textProcessor.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Entity;

@Data
@Entity
@Table(name = "ROOMS")
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    Integer id;
    String title;
    String description;
    Integer north;
    Integer east;
    Integer south;
    Integer west;

    public Room() {
        this.north = 0;
        this.east = 0;
        this.south = 0;
        this.west = 0;
    }
}
