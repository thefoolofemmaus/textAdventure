package com.textadventure.textProcessor.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.textadventure.textProcessor.model.Item;
import com.textadventure.textProcessor.model.Room;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DataSeeder(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.createObjectNode();
        Item item = new Item("Lantern", "A green lantern", true, true);
        Room startingRoom = new Room(null, "Starting Room", "The room where you start",
                2, 3, 0, 0,
                Arrays.asList(item), jsonNode);
        roomRepository.save(startingRoom);
        Room northRoom = new Room(null, "North room", "The room to the North", 0, 0, 1, 0, new ArrayList<>(), jsonNode);
        roomRepository.save(northRoom);
        Room eastRoom = new Room(null, "East room", "The room to the East", 0, 0, 1, 0, new ArrayList<>(), jsonNode);
        roomRepository.save(eastRoom);
    }
}