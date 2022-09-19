package com.example.autocomplete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;

@RestController
@RequestMapping("/")
public class AutocompleteController {

    private static AutocompleteService autocompleteService;

    @Autowired
    public AutocompleteController(AutocompleteService autocompleteService) {
        this.autocompleteService = autocompleteService;
    }

    @GetMapping(value = "/")
    public ArrayList<String> suggestAutocomplete(@RequestParam String q, @RequestParam int amt) {
        return autocompleteService.getSuggestions(q, amt);
    }

    // Note: In real life I'd require a token to authenticate that a valid user is calling this endpoint
    @PostMapping(value = "loadFile")
    public String loadFile(@RequestParam MultipartFile file) throws FileNotFoundException {
        return autocompleteService.populateIndex(file);
    }

}
