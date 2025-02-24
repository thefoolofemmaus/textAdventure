package com.textadventure.textProcessor.service;

import com.textadventure.textProcessor.dto.TextRequest;
import com.textadventure.textProcessor.model.Room;
import com.textadventure.textProcessor.pipeline.StanfordPipeline;
import com.textadventure.textProcessor.repository.RoomRepository;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class TextProcessorService {

    private StanfordCoreNLP stanfordCoreNLP;
    private RoomRepository roomRepository;

    @Autowired
    public TextProcessorService(RoomRepository roomRepository) {
        this.stanfordCoreNLP = StanfordPipeline.getPipeline(); // Initialize once in constructor
        this.roomRepository = roomRepository;
    }

    public void processMessage(TextRequest textRequest) {
        if(stanfordCoreNLP == null) {
            stanfordCoreNLP = StanfordPipeline.getPipeline();
        }
        Room currentRoom = roomRepository.getReferenceById(textRequest.getCurrentRoom());
        CoreDocument coreDocument = new CoreDocument(textRequest.getMessage());
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreLabel> coreLabelList = coreDocument.tokens();

        String verb = "";
        String object = "";
        String adverb = "";

        for(CoreLabel coreLabel : coreLabelList) {
            String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            switch (pos.toUpperCase()) {
                case "VB" :
                    verb = coreLabel.lemma();
                    break;
                case "RB" :
                    adverb = coreLabel.lemma();
                    break;
                case "NNP" :
                case "NN" :
                    object = coreLabel.lemma();
                    break;
            }
        }

        System.out.println("verb - " + verb);
        System.out.println("object - " + object);
        System.out.println("adverb - " + adverb);
    }
}
