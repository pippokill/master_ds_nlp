/*
 * Questo software è stato sviluppato dal gruppo di ricerca SWAP del Dipartimento di Informatica dell'Università degli Studi di Bari.
 * Tutti i diritti sul software appartengono esclusivamente al gruppo di ricerca SWAP.
 * Il software non può essere modificato e utilizzato per scopi di ricerca e/o industriali senza alcun permesso da parte del gruppo di ricerca SWAP.
 * Il software potrà essere utilizzato a scopi di ricerca scientifica previa autorizzazione o accordo scritto con il gruppo di ricerca SWAP.
 * 
 * Bari, Marzo 2014
 */
package uniba.it.masterds_nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

/**
 * Implementa la pipeline di NLP
 *
 * @author pierpaolo
 */
public class OpenNLP {

    private static OpenNLP instance;

    private final String opennlpDir;

    private boolean init = false;

    private SentenceDetectorME sentenceDetector;

    private Tokenizer tokenizer;

    private POSTaggerME tagger;

    private ChunkerME chunker;

    /**
     * Restituisce una nuova istanza della pipeline di NLP
     *
     * @param opennlpdir La pipeline di NLP
     * @return
     */
    public synchronized static OpenNLP getInstance(String opennlpdir) {
        if (instance == null) {
            instance = new OpenNLP(opennlpdir);
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public synchronized static OpenNLP getInstance() {
        return getInstance("resources/opennlp_en/");
    }

    /**
     * Individua le frasi in un testo
     *
     * @param text Il testo
     * @return L'array di frasi
     * @throws Exception
     */
    public String[] sentenceDetection(String text) throws Exception {
        if (!init) {
            init();
        }
        return sentenceDetector.sentDetect(text);
    }

    /**
     * Tokenizza un testo
     *
     * @param text Il testo
     * @return L'array di token
     * @throws Exception
     */
    public String[] tokenize(String text) throws Exception {
        if (!init) {
            init();
        }
        return tokenizer.tokenize(text);
    }

    /**
     *
     * @param text
     * @return
     * @throws Exception
     */
    public Span[] tokenizeWithSpan(String text) throws Exception {
        if (!init) {
            init();
        }
        return tokenizer.tokenizePos(text);
    }

    /**
     *
     * @param text
     * @return
     * @throws Exception
     */
    public List<Token> tokenizeWithOffset(String text) throws Exception {
        if (!init) {
            init();
        }
        Span[] tokenizePos = tokenizer.tokenizePos(text);
        List<Token> list = new ArrayList<>();
        for (Span span : tokenizePos) {
            list.add(new Token(span.getCoveredText(text).toString(), span.getStart(), span.getEnd()));
        }
        return list;
    }

    /**
     * Effettua il pos-tag di un array di parole
     *
     * @param tokens Le parole
     * @return L'array di pos-tag
     * @throws Exception
     */
    public String[] postag(String[] tokens) throws Exception {
        if (!init) {
            init();
        }
        return tagger.tag(tokens);
    }

    /**
     *
     * @param tokens
     * @throws Exception
     */
    public void postag(List<Token> tokens) throws Exception {
        if (!init) {
            init();
        }
        String[] tags = tagger.tag(Utils.tokens2array(tokens));
        for (int i = 0; i < tags.length; i++) {
            tokens.get(i).setPostag(tags[i]);
        }
    }

    /**
     * Esegue il chunking di un array di token
     *
     * @param tokens L'array di token
     * @param posTags L'array di pos-tag
     * @return L'array dei chunks estratti
     * @throws Exception
     */
    public String[] chunk(String[] tokens, String[] posTags) throws Exception {
        if (!init) {
            init();
        }
        return chunker.chunk(tokens, posTags);
    }

    /**
     *
     * @param tokens
     * @throws Exception
     */
    public void chunk(List<Token> tokens) throws Exception {
        if (!init) {
            init();
        }
        String[] chunks = chunker.chunk(Utils.tokens2array(tokens), Utils.postags2array(tokens));
        for (int i = 0; i < chunks.length; i++) {
            tokens.get(i).setChunk(chunks[i]);
        }
    }

    /**
     *
     * @param tokens
     * @param postags
     * @return
     * @throws Exception
     */
    public Map<String, Span[]> ner(String[] tokens, String[] postags) throws Exception {
        if (!init) {
            init();
        }
        return nerTagger.ner(tokens, postags);
    }

    private void initSentencDetector() throws Exception {
        InputStream modelIn = new FileInputStream(opennlpDir + "/en-sent.bin");
        SentenceModel model = new SentenceModel(modelIn);
        sentenceDetector = new SentenceDetectorME(model);
    }

    private void initTokenizer() throws Exception {
        InputStream modelIn = new FileInputStream(opennlpDir + "/en-token.bin");
        TokenizerModel model = new TokenizerModel(modelIn);
        tokenizer = new TokenizerME(model);
    }

    private void initPosTagger() throws Exception {
        InputStream modelIn = new FileInputStream(opennlpDir + "/en-pos-maxent.bin");
        POSModel model = new POSModel(modelIn);
        tagger = new POSTaggerME(model);
    }

    private void initChunker() throws Exception {
        InputStream modelIn = new FileInputStream(opennlpDir + "/en-chunker.bin");
        ChunkerModel model = new ChunkerModel(modelIn);
        chunker = new ChunkerME(model);
    }

    private NERTagger nerTagger;

    private void initNER() throws Exception {
        nerTagger = new NERTagger();
        nerTagger.addNameFinder("LOC", new File(opennlpDir + "/en-ner-location.bin"));
        nerTagger.addNameFinder("PER", new File(opennlpDir + "/en-ner-person.bin"));
        nerTagger.addNameFinder("ORG", new File(opennlpDir + "/en-ner-organization.bin"));
        nerTagger.addNameFinder("DATE", new File(opennlpDir + "/en-ner-date.bin"));
    }

    private void init() throws Exception {
        initSentencDetector();
        initTokenizer();
        initPosTagger();
        initChunker();
        initNER();
        init = true;
    }

    private OpenNLP(String opennlpDir) {
        this.opennlpDir = opennlpDir;
        try {
            init();
        } catch (Exception ex) {
            Logger.getLogger(OpenNLP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processa un testo e restituisce le frasi individuate con i relativi token
     * {@link Token}
     *
     * @param text Il testo da processare
     * @return Le frasi con i token {@link Token}
     * @throws Exception
     */
    public List<List<Token>> processText(String text) throws Exception {
        List<List<Token>> sentences = new ArrayList<>();
        String[] sentenceDetection = sentenceDetection(text);
        for (String sentence : sentenceDetection) {
            List<Token> list = new ArrayList<>();
            Span[] spans = tokenizeWithSpan(sentence);
            String[] tokens = new String[spans.length];
            for (int i = 0; i < spans.length; i++) {
                tokens[i] = spans[i].getCoveredText(sentence).toString();
            }
            String[] postags = postag(tokens);
            String[] chunks = chunk(tokens, postags);
            int p = 1;
            for (int i = 0; i < tokens.length; i++) {
                Token tobj = new Token(tokens[i], spans[i].getStart(), spans[i].getEnd());
                tobj.setBeginOffset(spans[i].getStart());
                tobj.setEndOffset(spans[i].getEnd());
                tobj.setPostag(postags[i]);
                tobj.setChunk(chunks[i]);
                list.add(tobj);
                p++;
            }
            Map<String, Span[]> nerMap = ner(tokens, postags);
            for (String btag : nerMap.keySet()) {
                Span[] spansNer = nerMap.get(btag);
                for (Span span : spansNer) {
                    list.get(span.getStart()).setNer("B-" + span.getType());
                    for (int k = span.getStart() + 1; k < span.getEnd(); k++) {
                        list.get(k).setNer("I");
                    }
                }
            }
            sentences.add(list);
        }
        return sentences;
    }

}
