package com.textadventure.textProcessor.controller;

import com.textadventure.textProcessor.model.Room;
import com.textadventure.textProcessor.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class RoomController {

    private final RoomRepository roomRepository;

    @GetMapping(path = "/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Integer id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/room")
    public void putANewRoomIn(@RequestBody Room room) {
        roomRepository.save(room);
    }

}
