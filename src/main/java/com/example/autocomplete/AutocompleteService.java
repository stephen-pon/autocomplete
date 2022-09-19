package com.example.autocomplete;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class AutocompleteService {

    private static TrieNode index = new TrieNode(null);

    public String populateIndex(MultipartFile file) throws FileNotFoundException {
        String line;
        try {
            InputStream is = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                loadWordIntoIndex(line.toLowerCase());
            }
            return "Processing complete";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadWordIntoIndex(String word) {
        int i = 0;
        TrieNode currNode = index;
        while (i < word.length()) {
            Character curr = word.charAt(i);
            if (!currNode.containsChild(curr)) {
                currNode.insertChild(curr);
            }
            currNode = currNode.getChild(curr);
            i += 1;
        }
        currNode.insertChild(null);
    }

    public ArrayList<String> getSuggestions(String query, int amount) {
        ArrayList<String> output = new ArrayList<>();
        TrieNode searchRoot = getSearchRoot(query);
        if (searchRoot == null) {
            return output;
        }

        Queue searchQueue = new LinkedList<SearchTuple>();
        searchChildren(searchRoot, output, searchQueue, query);

        while (!searchQueue.isEmpty() && output.size() < amount) {
            SearchTuple curr = (SearchTuple) searchQueue.remove();
            TrieNode node = curr.node;
            String word = curr.word;
            searchChildren(node, output, searchQueue, word);
        }

        return output;
    }

    private TrieNode getSearchRoot(String query) {
        int i = 0;
        TrieNode currNode = index;
        while (i < query.length()) {
            Character curr = query.charAt(i);
            if (!currNode.containsChild(curr)) {
                return null;
            } else {
                currNode = currNode.getChild(curr);
                i += 1;
            }
        }
        return currNode;
    }

    private void searchChildren(TrieNode node, ArrayList output, Queue searchQueue, String currWord) {
        for (TrieNode n : node.children.values()) {
            if (n.val == null) {
                output.add(currWord);
            } else {
                searchQueue.add(new SearchTuple(currWord + n.val, n));
            }
        }
    }

}
