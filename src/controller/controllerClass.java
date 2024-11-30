package controller;

import service.serviceClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class controllerClass {
    private serviceClass service;
    HashMap<String, String> slangWordMap;

    public controllerClass() {
        service = new serviceClass();
        slangWordMap = service.getSlangWordMap();
    }

    public String[] getSlangWordKeyList() {
        return service.getSlangWordKeyList();
    }

    public String searchByName(String slang) {
        for (Map.Entry<String, String> entry : slangWordMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(slang)) {
                return entry.getKey() + " -> " + entry.getValue();
            }
        }
        return "";
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

    public String getARandomWord() {
        String[] slangWords = service.getSlangWordKeyList();
        
        Random random = new Random();
        int randomIndex = random.nextInt(slangWords.length);
    
        String randomSlang = slangWords[randomIndex];
        String randomMeaning = slangWordMap.get(randomSlang);
    
        return randomSlang + " -> " + randomMeaning;
    }

    public List<String> getRandomDefinitions(String correctDefinition) {
        List<String> definitions = new ArrayList<String>(slangWordMap.values());
        definitions.remove(correctDefinition);
        Collections.shuffle(definitions);

        // Return 3 random incorrect definitions
        return definitions.subList(0, 3); 
    }

    public List<String> getRandomSlangWords(String correctSlang) {
        List<String> slangWords = new ArrayList<String>(slangWordMap.keySet());
        slangWords.remove(correctSlang);
        Collections.shuffle(slangWords);

        // Return 3 random incorrect slang words
        return slangWords.subList(0, 3); 
    }
}