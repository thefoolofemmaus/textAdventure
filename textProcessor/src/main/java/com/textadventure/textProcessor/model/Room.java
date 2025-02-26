package com.textadventure.textProcessor.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.textadventure.textProcessor.converter.JsonBConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@Entity
@Table(name = "ROOMS")
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Integer north;
    private Integer east;
    private Integer south;
    private Integer west;

    @Column(name = "items")
    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = JsonBConverter.class)
    private List<Item> items;

    @Column(name = "creatures")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode creatures;

    public Room() {
        this.north = 0;
        this.east = 0;
        this.south = 0;
        this.west = 0;
    }
}
