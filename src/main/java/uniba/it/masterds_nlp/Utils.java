/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniba.it.masterds_nlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author pierpaolo
 */
public class Utils {

    /**
     * Load a string hashset given an input file, one string per line
     *
     * @param file Input file
     * @return String hashset
     * @throws IOException
     */
    public static Set<String> loadFileInSet(File file) throws IOException {
        Set<String> set = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.ready()) {
            set.add(reader.readLine().toLowerCase().trim());
        }
        reader.close();
        return set;
    }

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String loadTextFromFile(File file) throws IOException {
        return loadTextFromFile(file, 0);
    }

    /**
     *
     * @param file
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String loadTextFromFile(File file, String encoding) throws IOException {
        return loadTextFromFile(file, encoding, 0);
    }

    /**
     *
     * @param file
     * @param lineToSkip
     * @return
     * @throws IOException
     */
    public static String loadTextFromFile(File file, int lineToSkip) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        if (lineToSkip > 0) {
            int l = 0;
            while (l < lineToSkip && reader.ready()) {
                reader.readLine();
                l++;
            }
        }
        while (reader.ready()) {
            sb.append(reader.readLine());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param file
     * @param encoding
     * @param lineToSkip
     * @return
     * @throws IOException
     */
    public static String loadTextFromFile(File file, String encoding, int lineToSkip) throws IOException {
        return loadTextFromFile(file, encoding, lineToSkip, false);
    }

    /**
     *
     * @param file
     * @param encoding
     * @param lineToSkip
     * @param toLowerCase
     * @return
     * @throws IOException
     */
    public static String loadTextFromFile(File file, String encoding, int lineToSkip, boolean toLowerCase) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        if (lineToSkip > 0) {
            int l = 0;
            while (l < lineToSkip && reader.ready()) {
                reader.readLine();
                l++;
            }
        }
        while (reader.ready()) {
            if (toLowerCase) {
                sb.append(reader.readLine().toLowerCase());
            } else {
                sb.append(reader.readLine());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param tokens
     * @return
     */
    public static String[] tokens2array(List<Token> tokens) {
        String[] s = new String[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            s[0] = tokens.get(i).getToken();
        }
        return s;
    }

    /**
     *
     * @param tokens
     * @return
     */
    public static String[] postags2array(List<Token> tokens) {
        String[] s = new String[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            s[0] = tokens.get(i).getPostag();
        }
        return s;
    }

}
