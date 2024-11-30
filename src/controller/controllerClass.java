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

    public boolean slangWordExists(String slang) {
        for (String key : slangWordMap.keySet()) {
            if (key.equalsIgnoreCase(slang)) {
                return true;
            }
        }
        return false;
    }

    public String getMeaningBySlang(String slang) {
        for (String key : slangWordMap.keySet()) {
            if (key.equalsIgnoreCase(slang)) {
                return slangWordMap.get(key);
            }
        }
        return "Slang word not found";
    }

    public void addSlangWord(String slang, String meaning) {
        String exactKey = null;
        for (String key : slangWordMap.keySet()) {
            if (key.equalsIgnoreCase(slang)) {
                exactKey = key;
                break;
            }
        }

        if (exactKey != null) {
            slangWordMap.put(exactKey, meaning);
        } else {
            slangWordMap.put(slang, meaning);
        }
        
        service.saveSlangWordsToFile();
    }

    public boolean editSlangWord(String slang, String newMeaning) {
        for (String key : slangWordMap.keySet()) {
            if (key.equalsIgnoreCase(slang)) {
                slangWordMap.put(key, newMeaning);
                service.saveSlangWordsToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteSlangWord(String slang) {
        for (String key : slangWordMap.keySet()) {
            if (key.equalsIgnoreCase(slang)) {
                slangWordMap.remove(key);
                service.saveSlangWordsToFile();
                return true;
            }
        }
        return false;
    }

    public boolean resetSlangWords(){
        return service.resetSlangWords();
    }
}