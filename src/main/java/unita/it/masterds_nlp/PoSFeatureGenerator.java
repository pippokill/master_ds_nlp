/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unita.it.masterds_nlp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.featuregen.FeatureGeneratorAdapter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author pierpaolo
 */
public class PoSFeatureGenerator extends FeatureGeneratorAdapter {

    private final POSTaggerME tagger;

    private final Map<String, String> tokPosMap;

    /**
     *
     */
    public static final String POS_PREFIX = "p";

    /**
     *
     * @param postme
     */
    public PoSFeatureGenerator(POSTaggerME tagger) {
        this.tagger = tagger;
        tokPosMap = new HashMap<>();
    }

    /**
     *
     * @param features
     * @param tokens
     * @param index
     * @param preds
     */
    @Override
    public void createFeatures(List<String> features, String[] tokens, int index, String[] preds) {
        String[] postags = this.getPostags(tokens);
        features.add(POS_PREFIX + "=" + postags[index]);
    }

    private String[] getPostags(String[] tokens) {
        String text = StringUtils.join(tokens, " ");
        if (this.tokPosMap.containsKey(text)) {
            return this.tokPosMap.get(text).split(" ");
        } else {
            String[] postags = this.tagger.tag(tokens);
            this.tokPosMap.put(text, StringUtils.join(postags, " "));
            return postags;
        }
    }

}
