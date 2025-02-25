package com.textadventure.textProcessor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.textadventure.textProcessor.dto.TextRequest;
import com.textadventure.textProcessor.dto.TextResponse;
import com.textadventure.textProcessor.model.Action;
import com.textadventure.textProcessor.model.Item;
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
import java.util.Arrays;
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
        response.setInRoom(textRequest.getCurrentRoom());

        Action action = new Action();
        action.setStartingRoom(roomRepository.getReferenceById(textRequest.getCurrentRoom()));
        action.setEndingRoom(action.getStartingRoom());

        List<CoreLabel> coreLabelList = createCoreLableList(textRequest.getMessage());
        parseCoreLabelList(action, coreLabelList);

        if(null != action.getVerb()) {
            handleVerb(action);
            if(action.isSuccess()) {
                response.setInRoom(action.getEndingRoom().getId());
                response.setMessage(action.getEndingRoom().getTitle());
            } else {
                response.setMessage(action.getMessage());
            }

        } else {
            response.setMessage("I don't know what you mean: \"" + textRequest.getMessage() + "\"");
        }

        return response;
    }

    private void parseCoreLabelList(Action action, List<CoreLabel> coreLabelList) {
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
    }

    private void handleVerb(Action action) {
        switch (action.getVerb()) {
            case "go" :
                handleVerbGo(action);
                break;
            case "look" :
                handleVerbLook(action);
                break;
            default :
                action.setSuccess(false);
                action.setMessage("I don't know how to " + action.getVerb());
                break;
        }
    }

    private void handleVerbLook(Action action) {
        if (action.getAdverb().equals("around")) {
            action.setSuccess(true);
            action.setMessage(action.getStartingRoom().getDescription());
        } else {

        }
    }

    private void handleVerbGo(Action action) {
        int goingRoom = goToRoom(action.getStartingRoom(), action.getAdverb());
        if (0 == goingRoom) {
            action.setMessage("You can't go " + action.getAdverb());
            action.setSuccess(false);
        } else  {
            action.setEndingRoom(roomRepository.getReferenceById(goingRoom));
            action.setSuccess(true);
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
