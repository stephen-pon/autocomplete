package com.example.autocomplete;

import java.util.HashMap;

public class TrieNode {

    public Character val;
    public HashMap<Character, TrieNode> children;

    public TrieNode(Character val) {
        this.val = val;
        this.children = new HashMap<>();
    }

    public void insertChild(Character c) {
        this.children.put(c, new TrieNode(c));
    }

    public boolean containsChild(Character c) {
        return this.children.containsKey(c);
    }

    public TrieNode getChild(Character c) {
        return this.children.get(c);
    }

}
