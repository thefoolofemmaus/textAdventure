package com.textadventure.textProcessor.service;

import com.textadventure.textProcessor.dto.TextRequest;
import com.textadventure.textProcessor.model.Room;
import com.textadventure.textProcessor.repository.RoomRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TextProcessorServiceTests {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private TextProcessorService textProcessorService;

    @BeforeEach
    void setUp() {
        Room mockRoom = new Room();
        mockRoom.setId(1);
        mockRoom.setTitle("Starting Room");
        when(roomRepository.getReferenceById(1)).thenReturn(mockRoom);
    }

    @Test
    void sendTestMessage() {
        TextRequest textRequest = new TextRequest("Go north.", 1);
        //String message = "Hit the Troll with the sword.";
        textProcessorService.processMessage(textRequest);
    }
}
