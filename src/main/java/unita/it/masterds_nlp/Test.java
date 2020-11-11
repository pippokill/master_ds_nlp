/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unita.it.masterds_nlp;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pierpaolo
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String text = "US President-elect Joe Biden announces the teams he will use to ensure a smooth transition in January.";
            List<List<Token>> sentences = OpenNLP.getInstance().processText(text);
            for (List<Token> sentence : sentences) {
                for (Token token : sentence) {
                    System.out.println(token);
                }
                System.out.println("==================================================================");
            }
            
            sentences = StanfordNLP.getInstance().processText(text);
            for (List<Token> sentence : sentences) {
                for (Token token : sentence) {
                    System.out.println(token);
                }
                System.out.println("==================================================================");
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
