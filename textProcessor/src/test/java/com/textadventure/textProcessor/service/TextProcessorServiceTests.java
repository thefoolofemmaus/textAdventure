package com.textadventure.textProcessor.service;

import com.textadventure.textProcessor.dto.TextRequest;
import com.textadventure.textProcessor.model.Room;
import com.textadventure.textProcessor.repository.RoomRepository;
import edu.stanford.nlp.ling.CoreLabel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
    void createCoreLableList_Test() throws Exception{
        String testString = "Hit the Troll with the sword";
        Method createCoreLableListMethod = TextProcessorService.class.getDeclaredMethod("createCoreLableList", String.class);
        createCoreLableListMethod.setAccessible(true);
        List<CoreLabel> resultList = (List<CoreLabel>) createCoreLableListMethod.invoke(textProcessorService, testString);
        assertEquals(resultList.size(), 6);
    }

    private Room createRoom1() {
        return new Room(1, "Starting room", "The room where you start", 2, null, null, null);
    }

    private Room createRoom2() {
        return new Room(2, "The room to the north", "Test room to the north", null, null, 1, null);
    }
}
