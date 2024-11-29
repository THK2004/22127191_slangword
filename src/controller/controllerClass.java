package controller;

import service.serviceClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class controllerClass {
    private serviceClass service;
    HashMap<String, String> slangWordMap;

    public controllerClass() {
        service = new serviceClass();
        slangWordMap = service.getSlangWordMap();
    }

    public String[] getSlangWordList() {
        return service.getSlangWordList();
    }

    public String searchByName(String slang) {
        for (Map.Entry<String, String> entry : slangWordMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(slang)) {
                return entry.getKey() + " -> " + entry.getValue();
            }
        }
        return "Slang not found";
    }

    public ArrayList<String> searchByDescription(String keyword) {
        ArrayList<String> results = new ArrayList<String>();

        for (Map.Entry<String, String> entry : slangWordMap.entrySet()) {
            if (entry.getValue().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(entry.getKey() + " -> " + entry.getValue());
            }
        }

        return results;
    }
}