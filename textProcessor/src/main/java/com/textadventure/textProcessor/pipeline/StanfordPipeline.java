package com.textadventure.textProcessor.pipeline;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class StanfordPipeline {
    private static StanfordCoreNLP stanfordCoreNLP;
    private static Properties properties;
    private static String propertiesName = "tokenize, ssplit, pos, lemma";

    private StanfordPipeline () {}

    static {
        properties = new Properties();
        properties.setProperty("annotators", propertiesName);
    }

    public static StanfordCoreNLP getPipeline() {
        if (stanfordCoreNLP == null) {
            stanfordCoreNLP = new StanfordCoreNLP(properties);
        }

        return stanfordCoreNLP;
    }

}
