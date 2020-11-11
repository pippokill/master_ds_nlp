/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unita.it.masterds_nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 *
 * @author pierpaolo
 */
public class NERTagger {

    private static final Map<String, NameFinderME> map = new HashMap<>();

    /**
     *
     * @param key
     * @param finder
     */
    public void addNameFinder(String key, NameFinderME finder) {
        map.put(key, finder);
    }

    /**
     *
     * @param key
     * @param modelFile
     * @throws Exception
     */
    public void addNameFinder(String key, File modelFile) throws Exception {
        InputStream modelIn = new FileInputStream(modelFile);
        TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
        NameFinderME nameFinder = new NameFinderME(model);
        addNameFinder(key, nameFinder);
    }

    /**
     *
     * @param tokens
     * @param postags
     * @return
     * @throws Exception
     */
    public Map<String, Span[]> ner(String[] tokens, String[] postags) throws Exception {
        Map<String, Span[]> results = new HashMap<>();
        for (String key : map.keySet()) {
            Span nameSpans[] = map.get(key).find(tokens, getPosFeatures(postags));
            results.put(key, nameSpans);
        }
        return results;
    }

    private String[][] getPosFeatures(String[] postags) {
        String[][] features = new String[postags.length][1];
        for (int i = 0; i < postags.length; i++) {
            features[i][0] = PoSFeatureGenerator.POS_PREFIX + "=" + postags[i];
        }
        return features;
    }

}
