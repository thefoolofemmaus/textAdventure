package com.textadventure.textProcessor.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Entity;

@Data
@Entity
@Table(name = "ROOMS")
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
}
