/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unita.it.masterds_nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NormalizedNamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author pierpaolo
 */
public class StanfordNLP {

    private static final Logger LOG = Logger.getLogger(StanfordNLP.class.getName());

    private static StanfordNLP instance;

    private final StanfordCoreNLP pipeline;

    /**
     *
     */
    public StanfordNLP() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        pipeline = new StanfordCoreNLP(props);
    }

    /**
     *
     * @return
     */
    public synchronized static StanfordNLP getInstance() {
        if (instance == null) {
            instance = new StanfordNLP();
        }
        return instance;
    }

    /**
     *
     * @param text
     * @return
     * @throws java.lang.Exception
     */
    public List<List<Token>> processText(String text) throws Exception {
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        List<List<Token>> results = new ArrayList<>();
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            List<Token> s = new ArrayList();
            int p = 1;
            //SemanticGraph dependencies = sentence.get(BasicDependenciesAnnotation.class);
            for (CoreLabel label : sentence.get(TokensAnnotation.class)) {
                String word = label.get(TextAnnotation.class);
                String pos = label.get(PartOfSpeechAnnotation.class);
                Token t = new Token(word, label.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class), label.get(CoreAnnotations.CharacterOffsetEndAnnotation.class));
                t.setPostag(pos);
                t.setNer(label.get(NamedEntityTagAnnotation.class));
                t.setLemma(label.get(CoreAnnotations.LemmaAnnotation.class));
                t.setBeginOffset(label.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class));
                t.setEndOffset(label.get(CoreAnnotations.CharacterOffsetEndAnnotation.class));
                s.add(t);
                p++;
            }
            results.add(s);
        }
        return results;
    }

}
