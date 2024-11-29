package service;

import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

public class serviceClass {
    private HashMap<String, String> slangWordMap;

    public serviceClass() {
        if (slangWordMap == null){
            slangWordMap = new HashMap<>();
            loadSlangWords("src/public/slang_modified.txt");
        }
    }

    private void loadSlangWords(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();
            int count = 1;
            int errorCount = 0;
            ArrayList<String> duplicateKeys = new ArrayList<String>();
            String line;
            
            while ((line = reader.readLine()) != null) {
                count++;
                String[] parts = line.split("`", 2);
                
                if (parts.length == 2) {
                    String slang = parts[0].trim();
                    String meaning = parts[1].trim();
                    
                    if (slangWordMap.containsKey(slang)) {
                        duplicateKeys.add(slang);
                    } else {
                        slangWordMap.put(slang, meaning);
                    }
                } else {
                    errorCount++;
                    System.out.println("Error format in slang word file at line: " + count);
                }
            }
            reader.close();

            // Report findings
            int size = slangWordMap.size();
            System.out.println("Total unique slang words: " + size);
            System.out.println("Malformed lines: " + errorCount);
            System.out.println("Duplicate keys: " + duplicateKeys.size());

            // If duplicates were found, report them
            if (!duplicateKeys.isEmpty()) {
                System.out.println("Duplicate slang words found:");
                for (String duplicate : duplicateKeys) {
                    System.out.print(duplicate + " ");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading slang file: " + e.getMessage());
        }
    }

    public HashMap<String, String> getSlangWordMap() {
        return slangWordMap;
    }

    public String[] getSlangWordList() {
        return slangWordMap.keySet().toArray(new String[0]);
    }

    /*
    public String searchSlangWord(String slang) {
        return slangWordMap.getOrDefault(slang, "Slang not found");
    }

    public void addOrUpdateSlangWord(String slang, String meaning) {
        slangWordMap.put(slang, meaning);
    }

    public void deleteSlangWord(String slang) {
        slangWordMap.remove(slang);
    } 
    */
}
