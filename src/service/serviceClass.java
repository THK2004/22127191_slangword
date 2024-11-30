package service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class serviceClass {
    private HashMap<String, String> slangWordMap;
    private String original = "src/public/slang.txt";
    private String modified = "src/public/slang_modified.txt";

    public serviceClass() {
        if (slangWordMap == null){
            slangWordMap = new HashMap<>();
            loadSlangWords(modified);
        }
    }

    private void loadSlangWords(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine();
            int count = 1;
            int errorCount = 0;
            ArrayList<String> duplicateKeys = new ArrayList<String>();
            String line;
            
            while ((line = br.readLine()) != null) {
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
            br.close();

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

    public boolean saveSlangWordsToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/public/slang_modified.txt"));
            bw.write("Slag`Meaning\n");
            for (Map.Entry<String, String> entry : slangWordMap.entrySet()) {
                bw.write(entry.getKey() + "`" + entry.getValue() + "\n");
            }
            bw.flush();
            bw.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error saving slang words to file: " + e.getMessage());
            return false;
        }
    }

    public boolean resetSlangWords() {
        try {
            FileInputStream fis = new FileInputStream(original);
            FileOutputStream fos = new FileOutputStream(modified);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fis.close();
            fos.flush();
            fos.close();
        }catch (IOException e) {
            System.out.println("Error resetting slang words: " + e.getMessage());
            return false;
        }

        slangWordMap.clear();
        loadSlangWords("src/public/slang_modified.txt");
        return true;
    }
}
