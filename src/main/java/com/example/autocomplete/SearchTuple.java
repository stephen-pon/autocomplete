package com.example.autocomplete;

public class SearchTuple {

    public String word;
    public TrieNode node;

    public SearchTuple(String word, TrieNode node) {
        this.word = word;
        this.node = node;
    }

}
