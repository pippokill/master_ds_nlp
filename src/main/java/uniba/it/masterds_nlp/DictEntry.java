/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uniba.it.masterds_nlp;

import java.util.Objects;

/**
 *
 * @author pierpaolo
 */
public class DictEntry implements Comparable<DictEntry> {
    
    private String word;
    
    private int count;

    public DictEntry(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.word);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DictEntry other = (DictEntry) obj;
        if (!Objects.equals(this.word, other.word)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DictEntry{" + "word=" + word + ", count=" + count + '}';
    }

    @Override
    public int compareTo(DictEntry o) {
        return Integer.compare(count, o.count);
    }
    
    
    
}
