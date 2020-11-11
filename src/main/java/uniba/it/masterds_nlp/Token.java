/*
 * Questo software è stato sviluppato dal gruppo di ricerca SWAP del Dipartimento di Informatica dell'Università degli Studi di Bari.
 * Tutti i diritti sul software appartengono esclusivamente al gruppo di ricerca SWAP.
 * Il software non può essere modificato e utilizzato per scopi di ricerca e/o industriali senza alcun permesso da parte del gruppo di ricerca SWAP.
 * Il software potrà essere utilizzato a scopi di ricerca scientifica previa autorizzazione o accordo scritto con il gruppo di ricerca SWAP.
 * 
 * Bari, Marzo 2014
 */
package uniba.it.masterds_nlp;

/**
 * Contiene le informazioni su una singola parola (Token)
 *
 * @author Pierpaolo Basile
 */
public class Token {

    private String token;
    private String lemma;
    private String postag;
    private String chunk;
    private String ner = "0";
    private int beginOffset = -1;
    private int endOffset = -1;
    private int dep_head;
    private String dep_rel;

    /**
     *
     * @param token
     */
    public Token(String token) {
        this.token = token;
    }

    /**
     *
     * @param token
     * @param beginOffset
     * @param endOffset
     */
    public Token(String token, int beginOffset, int endOffset) {
        this.token = token;
        this.beginOffset = beginOffset;
        this.endOffset = endOffset;
    }

    /**
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     */
    public String getLemma() {
        return lemma;
    }

    /**
     *
     * @param lemma
     */
    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    /**
     *
     * @return
     */
    public String getPostag() {
        return postag;
    }

    /**
     *
     * @param postag
     */
    public void setPostag(String postag) {
        this.postag = postag;
    }

    /**
     *
     * @return
     */
    public String getChunk() {
        return chunk;
    }

    /**
     *
     * @param chunk
     */
    public void setChunk(String chunk) {
        this.chunk = chunk;
    }

    /**
     *
     * @return
     */
    public String getNer() {
        return ner;
    }

    /**
     *
     * @param ner
     */
    public void setNer(String ner) {
        this.ner = ner;
    }

    /**
     *
     * @return
     */
    public int getBeginOffset() {
        return beginOffset;
    }

    /**
     *
     * @param beginOffset
     */
    public void setBeginOffset(int beginOffset) {
        this.beginOffset = beginOffset;
    }

    /**
     *
     * @return
     */
    public int getEndOffset() {
        return endOffset;
    }

    /**
     *
     * @param endOffset
     */
    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }

    /**
     *
     * @return
     */
    public int getDep_head() {
        return dep_head;
    }

    /**
     *
     * @param dep_head
     */
    public void setDep_head(int dep_head) {
        this.dep_head = dep_head;
    }

    /**
     *
     * @return
     */
    public String getDep_rel() {
        return dep_rel;
    }

    /**
     *
     * @param dep_rel
     */
    public void setDep_rel(String dep_rel) {
        this.dep_rel = dep_rel;
    }

    @Override
    public String toString() {
        return "Token{" + "token=" + token + ", lemma=" + lemma + ", postag=" + postag + ", chunk=" + chunk + ", ner=" + ner + ", beginOffset=" + beginOffset + ", endOffset=" + endOffset + '}';
    }
    
    

}
