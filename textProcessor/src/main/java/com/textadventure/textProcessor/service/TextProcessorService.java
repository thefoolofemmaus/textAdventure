package com.textadventure.textProcessor.service;

import com.textadventure.textProcessor.dto.TextRequest;
import com.textadventure.textProcessor.dto.TextResponse;
import com.textadventure.textProcessor.model.Action;
import com.textadventure.textProcessor.model.Room;
import com.textadventure.textProcessor.pipeline.StanfordPipeline;
import com.textadventure.textProcessor.repository.RoomRepository;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
@NoArgsConstructor
public class TextProcessorService {

    private StanfordCoreNLP stanfordCoreNLP;
    private RoomRepository roomRepository;

    @Autowired
    public TextProcessorService(RoomRepository roomRepository) {
        this.stanfordCoreNLP = StanfordPipeline.getPipeline();
        this.roomRepository = roomRepository;
    }

    public TextResponse processMessage(TextRequest textRequest) {
        TextResponse response = new TextResponse();
        Action action = new Action();
        action.setStartingRoom(roomRepository.getReferenceById(textRequest.getCurrentRoom()));
        action.setEndingRoom(action.getStartingRoom());

        List<CoreLabel> coreLabelList = createCoreLableList(textRequest.getMessage());

        for(CoreLabel coreLabel : coreLabelList) {
            String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            switch (pos.toUpperCase()) {
                case "VB" :
                    action.setVerb(coreLabel.lemma());
                    break;
                case "RB" :
                    action.setAdverb(coreLabel.lemma());
                    break;
                case "NNP" :
                case "NN" :
                    action.setObject(coreLabel.lemma());
                    break;
            }
        }

        handleVerb(action);

        response.setInRoom(action.getEndingRoom().getId());
        response.setMessage(action.getEndingRoom().getTitle());

        return response;
    }

    private void handleVerb(Action action) {
        switch (action.getVerb()) {
            case "go" :
                int goingRoom = goToRoom(action.getStartingRoom(), action.getAdverb());
                action.setEndingRoom(roomRepository.getReferenceById(goingRoom));
                System.out.println("Going to " + goingRoom);
                break;
        }
    }

    private List<CoreLabel> createCoreLableList(String message) {
        CoreDocument coreDocument = new CoreDocument(message);
        stanfordCoreNLP.annotate(coreDocument);
        return coreDocument.tokens();
    }

    private int goToRoom(Room currentRoom, String direction) {
        return getPropertyValue(currentRoom, direction);
    }

    private int getPropertyValue(Room room, String propertyName) {
        try {
            Method method = Room.class.getMethod("get" + capitalize(propertyName));
            return (int) method.invoke(room);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
