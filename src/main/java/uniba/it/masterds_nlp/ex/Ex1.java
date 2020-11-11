/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniba.it.masterds_nlp.ex;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import uniba.it.masterds_nlp.OpenNLP;
import uniba.it.masterds_nlp.Token;
import uniba.it.masterds_nlp.Utils;

/**
 *
 * @author pierpaolo
 */
public class Ex1 {

    /**
     * Build a dictionary from a text file
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String text = Utils.loadTextFromFile(new File(args[0]));
            Map<String, Integer> dict = new HashMap();
            List<List<Token>> process = OpenNLP.getInstance().processText(text);
            for (List<Token> s : process) {
                for (Token t : s) {
                    String key = t.getToken().toLowerCase();
                    Integer c = dict.get(key);
                    if (c == null) {
                        dict.put(key, 1);
                    } else {
                        dict.put(key, c + 1);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Ex1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
