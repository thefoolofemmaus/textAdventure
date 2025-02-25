package com.textadventure.textProcessor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.textadventure.textProcessor.dto.TextRequest;
import com.textadventure.textProcessor.dto.TextResponse;
import com.textadventure.textProcessor.model.Room;
import com.textadventure.textProcessor.repository.RoomRepository;
import edu.stanford.nlp.ling.CoreLabel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TextProcessorServiceTests {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private TextProcessorService textProcessorService;

    @Test
    void sendTestMessage() {
        when(roomRepository.getReferenceById(1)).thenReturn(createRoom1());
        when(roomRepository.getReferenceById(2)).thenReturn(createRoom2());

        TextRequest textRequest = new TextRequest("Go north.", 1);
        //String message = "Hit the Troll with the sword.";
        textProcessorService.processMessage(textRequest);
    }

    @Test
    void processMessageTest_GoBadDirection() {
        when(roomRepository.getReferenceById(2)).thenReturn(createRoom2());
        TextRequest textRequest = new TextRequest("Go north.", 2);
        TextResponse response = textProcessorService.processMessage(textRequest);
        assertEquals(response.getInRoom(), 2);
        assertEquals(response.getMessage(), "You can't go north");
    }
    @Test
    void processMessageTest_NoVerb() {
        when(roomRepository.getReferenceById(2)).thenReturn(createRoom2());
        TextRequest textRequest = new TextRequest("Beautiful north.", 2);
        TextResponse response = textProcessorService.processMessage(textRequest);
        assertEquals(response.getInRoom(), 2);
        assertEquals(response.getMessage(), "I don't know what you mean: \"Beautiful north.\"");
    }
    @Test
    void processMessageTest_BadVerb() {
        when(roomRepository.getReferenceById(1)).thenReturn(createRoom1());
        TextRequest textRequest = new TextRequest("Destroy north.", 1);
        TextResponse response = textProcessorService.processMessage(textRequest);
        assertEquals(response.getInRoom(), 1);
        assertEquals(response.getMessage(), "I don't know how to destroy");
    }

    @Test
    void processMessageTest_GoNorth() {
        when(roomRepository.getReferenceById(1)).thenReturn(createRoom1());
        when(roomRepository.getReferenceById(2)).thenReturn(createRoom2());

        TextRequest textRequest = new TextRequest("Go north.", 1);
        TextResponse response = textProcessorService.processMessage(textRequest);
        assertEquals(response.getInRoom(), 2);
        assertEquals(response.getMessage(), "The room to the north");
    }

    @Test
    void createCoreLableList_Test() throws Exception{
        String testString = "Hit the Troll with the sword";
        Method createCoreLableListMethod = TextProcessorService.class.getDeclaredMethod("createCoreLableList", String.class);
        createCoreLableListMethod.setAccessible(true);
        List<CoreLabel> resultList = (List<CoreLabel>) createCoreLableListMethod.invoke(textProcessorService, testString);
        assertEquals(resultList.size(), 6);
    }

    @Test
    void getPropertyValue_Test_north() throws Exception{
        Method getPropertyValueMethod = TextProcessorService.class.getDeclaredMethod("getPropertyValue", Room.class, String.class);
        getPropertyValueMethod.setAccessible(true);
        int result = (int) getPropertyValueMethod.invoke(textProcessorService, createRoom1(), "north");
        assertEquals(result, 2);
    }

    private Room createRoom1() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.createObjectNode();
        return new Room(1, "Starting room", "The room where you start", 2, 0, 0, 0, jsonNode, null);
    }

    private Room createRoom2() {
        return new Room(2, "The room to the north", "Test room to the north", 0, 0, 1, 0, null, null);
    }
}
