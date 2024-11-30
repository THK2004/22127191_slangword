package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import controller.controllerClass;

public class guiClass extends JPanel{
    private JList<String> slangWordList;
    private ArrayList<String> cachedSlangWordKeyList;
    private DefaultListModel<String> slangWordListModel;
    private JList<String> searchHistoryList; 
    private DefaultListModel<String> searchHistoryModel;
    
    private controllerClass controller;

    private JTextField searchByName;
    private JTextField searchByDesc;
    private JTextArea contentTextArea;

    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton resetButton;

    private JLabel randomWordLabel;
    private JTextArea meaningTextArea;
    private JButton randomWordButton;
    private JButton startQuiz1Button;
    private JButton startQuiz2Button;

    public guiClass(){
        controller = new controllerClass();
        cachedSlangWordKeyList = new ArrayList<String>(Arrays.asList(controller.getSlangWordKeyList()));

        setLayout(new GridBagLayout());

        //topLeft
        JPanel topLeft = new JPanel();
        topLeft.setLayout(new BoxLayout(topLeft, BoxLayout.Y_AXIS));

        JLabel searchByNameLabel = new JLabel("Search by name: ");
        searchByName = new JTextField(15);
        setupSearchByNameListener();
        JLabel searchByDescLabel = new JLabel("Search by description: ");
        searchByDesc = new JTextField(15);

        searchByName.addActionListener(this::handleSearchByName);
        searchByDesc.addActionListener(this::handleSearchByDescription);

        topLeft.add(searchByNameLabel);
        topLeft.add(searchByName);
        topLeft.add(searchByDescLabel);
        topLeft.add(searchByDesc);

        //Mid left
        JPanel midLeft = new JPanel(new BorderLayout()); 
        
        JLabel searchHistoryLabel = new JLabel("Search history");

        searchHistoryModel = new DefaultListModel<>();
        searchHistoryList = new JList<>(searchHistoryModel);
        setupSearchHistoryListeners();

        
        JScrollPane historyScrollPane = new JScrollPane(searchHistoryList);
        historyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        historyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        midLeft.add(searchHistoryLabel, BorderLayout.PAGE_START);
        midLeft.add(historyScrollPane, BorderLayout.CENTER);

        //botLeft
        JPanel botLeft = new JPanel(new BorderLayout()); 
        JLabel slangWordLabel = new JLabel("Slang words list");

        slangWordListModel = new DefaultListModel<>();
        addSlangWordKeyListToModel();
        slangWordList = new JList<>(slangWordListModel);
        setupSlangWordListListener();

        JScrollPane slangWordScrollPane = new JScrollPane(slangWordList);
        slangWordScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        slangWordScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        botLeft.add(slangWordLabel, BorderLayout.PAGE_START);
        botLeft.add(slangWordScrollPane, BorderLayout.CENTER);

        //topMid
        JPanel topMid = new JPanel();
        topMid.setLayout(new BoxLayout(topMid, BoxLayout.Y_AXIS));

        randomWordLabel = new JLabel();
        meaningTextArea = new JTextArea();
        meaningTextArea.setLineWrap(true);
        meaningTextArea.setEditable(false);
        meaningTextArea.setOpaque(false);
        meaningTextArea.setFocusable(false);
        meaningTextArea.setMaximumSize(new Dimension(300, 100));

        randomWordButton = new JButton("Show Random Word");
        setupRandomWordFeature();

        randomWordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        meaningTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        randomWordButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        topMid.add(randomWordLabel);
        topMid.add(meaningTextArea);
        topMid.add(Box.createVerticalGlue());
        topMid.add(randomWordButton);

        //topRight
        JPanel topRight = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        resetButton = new JButton("Reset");
        startQuiz1Button = new JButton("Quiz 1");
        startQuiz2Button = new JButton("Quiz 2");

        setupTopRightButtonListeners();

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(startQuiz1Button);
        buttonPanel.add(startQuiz2Button);

        topRight.add(buttonPanel, BorderLayout.CENTER);
        topRight.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        //Content
        JPanel content = new JPanel(new BorderLayout());
        JLabel word = new JLabel("Message: ");
        contentTextArea = new JTextArea();
        contentTextArea.setEditable(false);

        JScrollPane contentTextScrollPane = new JScrollPane(contentTextArea);
        contentTextScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        content.add(word, BorderLayout.PAGE_START);
        content.add(contentTextScrollPane, BorderLayout.CENTER);

        //Add all the panels to this panel
        addComponent(this, topLeft, 0, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(0, 0, 5, 5));
        addComponent(this, midLeft, 0, 1, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(5, 0, 5, 5));
        addComponent(this, botLeft, 0, 2, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(5, 0, 0, 5));
        addComponent(this, topMid, 1, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(0, 5, 5, 5));
        addComponent(this, topRight, 2, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(0, 5, 5, 0));
        addComponent(this, content, 1, 1, 2, 2, 200, 200, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(5, 5, 0, 0));
    }

    private void handleSearchByName(ActionEvent e) {
        String slang = searchByName.getText().trim();
        String result = controller.searchByName(slang);
    
        if (result.isEmpty()) {
            contentTextArea.setText("No results found for slang: " + slang);
        } else {
            contentTextArea.setText(result);
        }
    }

    private void handleSearchByDescription(ActionEvent e) {
        String keyword = searchByDesc.getText().trim();
        ArrayList<String> results = controller.searchByDescription(keyword);
    
        if (results.isEmpty()) {
            contentTextArea.setText("No slang words found containing the keyword: " + keyword);
        } else {
            String resultMessage = "";
            for (String result : results) {
                resultMessage = resultMessage + result + "\n";
            }
            contentTextArea.setText(resultMessage);
        }
    }

    private void addComponent(JPanel panel, JComponent component, int gridx, int gridy, int gridwidth, int gridheight, int ipadx, int ipady, int fill, int anchor, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.ipadx = ipadx;
        gbc.ipady = ipady;
        gbc.fill = fill;
        gbc.anchor = anchor;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = insets;
        panel.add(component, gbc);
    }

    private void addSlangWordKeyListToModel() {
        for (String slang : cachedSlangWordKeyList) {
            slangWordListModel.addElement(slang);
        }
    }

    private void setupSearchByNameListener() {
        searchByName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterSlangWords();
            }
    
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterSlangWords();
            }
    
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterSlangWords();
            }
    
            private void filterSlangWords() {
                String query = searchByName.getText().toLowerCase();
                slangWordListModel.clear();
    
                if (query.isEmpty()) {
                    slangWordListModel.addAll(cachedSlangWordKeyList);
                } else {
                    for (String slang : cachedSlangWordKeyList) {
                        if (slang.toLowerCase().startsWith(query)) {
                            slangWordListModel.addElement(slang);
                        }
                    }
                }
            }
        });
    }
    
    private void addSearchHistory(String searchTerm) {
        if (!searchHistoryModel.contains(searchTerm)) {
            searchHistoryModel.add(0, searchTerm);
        }
        
        if (searchHistoryModel.size() > 50) {
            searchHistoryModel.remove(searchHistoryModel.size() - 1);
        }
    }

    private void setupSearchHistoryListeners() {
        searchByName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchText = searchByName.getText().trim();

                    if (!searchText.isEmpty() && controller.searchByName(searchText) != null) {
                        addSearchHistory(searchText);
                    }
                }
            }
        });

        searchByDesc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchText = searchByDesc.getText().trim();

                    ArrayList<String> results = controller.searchByDescription(searchText);
                    if (!searchText.isEmpty() && !results.isEmpty()) {
                        addSearchHistory(searchText);
                    }
                }
            }
        });

        // Double-click listener for history list
        searchHistoryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedHistory = searchHistoryList.getSelectedValue();
                    if (selectedHistory != null) {
                        String result = controller.searchByName(selectedHistory);
                        if (result.equals("Slang not found")) {
                            ArrayList<String> results = controller.searchByDescription(selectedHistory);
                            if (results.isEmpty()) {
                                contentTextArea.setText("No results found for: " + selectedHistory);
                            } else {
                                String resultMessage = "";
                                for (String res : results) {
                                    resultMessage = resultMessage + res + "\n";
                                }
                                contentTextArea.setText(resultMessage);
                            }
                        } else {
                            contentTextArea.setText(result);
                        }
                    }
                }
            }
        });
    }

    private void setupSlangWordListListener() {
        slangWordList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedSlang = slangWordList.getSelectedValue();
                    if (selectedSlang != null) {
                        String result = controller.searchByName(selectedSlang);
                        if (result.isEmpty()) {
                            contentTextArea.setText("No details found for slang: " + selectedSlang);
                        } else {
                            contentTextArea.setText(result);
                        }
                    }
                }
            }
        });
    }

    private void refreshSlangWordList() {
        slangWordListModel.clear();
        cachedSlangWordKeyList = new ArrayList<String>(Arrays.asList(controller.getSlangWordKeyList()));
        slangWordListModel.addAll(cachedSlangWordKeyList);
    }

    private void refreshText() {
        searchByName.setText(null);
        searchByDesc.setText(null);
        contentTextArea.setText(null);
    }

    private void setupTopRightButtonListeners() {
        // Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slang = JOptionPane.showInputDialog(guiClass.this, "Enter slang word:");
                if (slang != null && !slang.isEmpty()) {
                    String existingMeaning = controller.getMeaningBySlang(slang.trim());
        
                    if (!existingMeaning.equals("Slang word not found")) {
                        String[] options = {"Overwrite", "Add Meaning", "Cancel"};
                        int option = JOptionPane.showOptionDialog(
                                guiClass.this,
                                "Slang word already exists. Do you want to overwrite it or add a second meaning?",
                                "Slang exists",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                options,
                                options[0]
                        );
        
                        if (option == 0) { // Overwrite
                            String meaning = JOptionPane.showInputDialog(guiClass.this, "Enter new meaning:");
                            if (meaning != null && !meaning.isEmpty()) {
                                controller.addSlangWord(slang.trim(), meaning.trim());
                                JOptionPane.showMessageDialog(guiClass.this, "Slang word updated successfully!");
                            }
                        } else if (option == 1) {  // Add meaning
                            String meaning = JOptionPane.showInputDialog(guiClass.this, "Enter the meaning:");
                            if (meaning != null && !meaning.isEmpty()) {
                                String updatedMeaning = existingMeaning + " | " + meaning.trim();
                                controller.addSlangWord(slang.trim(), updatedMeaning);
                                JOptionPane.showMessageDialog(guiClass.this, "More meaning added successfully!");
                            }
                        }
                    } else {
                        String meaning = JOptionPane.showInputDialog(guiClass.this, "Enter meaning:");
                        if (meaning != null && !meaning.isEmpty()) {
                            controller.addSlangWord(slang.trim(), meaning.trim());
                            JOptionPane.showMessageDialog(guiClass.this, "Slang word added successfully!");
                        }
                    }
        
                    refreshText();
                    refreshSlangWordList();
                }
            }
        });
        
        // Edit button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slang = JOptionPane.showInputDialog(guiClass.this, "Enter slang word to edit:");
                if (slang != null && !slang.isEmpty()) {
                    if (!controller.slangWordExists(slang.trim())) {
                        JOptionPane.showMessageDialog(guiClass.this, "Slang word not found!");
                        return;
                    }

                    String currentMeaning = controller.getMeaningBySlang(slang.trim());
                    String meaning = JOptionPane.showInputDialog(guiClass.this, 
                        "Current meaning: " + currentMeaning + "\nEnter new meaning:");
                    if (meaning != null && !meaning.isEmpty()) {
                        if (controller.editSlangWord(slang.trim(), meaning.trim())) {
                            JOptionPane.showMessageDialog(guiClass.this, "Slang word updated successfully!");
                            refreshText();
                        }
                    }
                }
            }
        });
    
        // Delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String slang = JOptionPane.showInputDialog(guiClass.this, "Enter slang word to delete:");
                if (slang != null && !slang.isEmpty()) {
                    if (controller.deleteSlangWord(slang.trim())) {
                        JOptionPane.showMessageDialog(guiClass.this, "Slang word deleted successfully!");
                        refreshText();
                        refreshSlangWordList();
                    } else {
                        JOptionPane.showMessageDialog(guiClass.this, "Slang word not found!");
                    }
                }
            }
        });
    
        // Reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(guiClass.this, "Are you sure you want to reset?", "Reset", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (controller.resetSlangWords()) {
                        JOptionPane.showMessageDialog(guiClass.this, "Slang words reset successfully!");
                        refreshText();
                        refreshSlangWordList();
                    } else {
                        JOptionPane.showMessageDialog(guiClass.this, "Error resetting slang words.");
                    }
                }
            }
        });
    
        // Quiz 1 button
        startQuiz1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomWord = controller.getARandomWord();
                String[] parts = randomWord.split("->");
                String slangWord = parts[0].trim();
                String correctDefinition = parts[1].trim();
        
                ArrayList<String> options = new ArrayList<String>(controller.getRandomDefinitions(correctDefinition));
                options.add(correctDefinition);
                Collections.shuffle(options);
        
                String[] optionArray = options.toArray(new String[0]);
        
                // Show the options in a dialog with 4 buttons
                int userAnswerIndex = JOptionPane.showOptionDialog(
                    guiClass.this,
                    "What is the meaning of: " + slangWord,
                    "Quiz 1",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    optionArray,
                    optionArray[0]
                );
        
                if (userAnswerIndex != -1) {
                    String userAnswer = optionArray[userAnswerIndex];
                    if (userAnswer.equals(correctDefinition)) {
                        JOptionPane.showMessageDialog(guiClass.this, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(guiClass.this, "Wrong! The correct answer is: " + correctDefinition, "Result", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        // Quiz 2 button
        startQuiz2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomWord = controller.getARandomWord();
                String[] parts = randomWord.split("->");
                String correctSlang = parts[0].trim();
                String definition = parts[1].trim();
        
                ArrayList<String> options = new ArrayList<String>(controller.getRandomSlangWords(correctSlang));
                options.add(correctSlang);
                Collections.shuffle(options);
        
                String[] optionArray = options.toArray(new String[0]);
        
                // Show the options in a dialog with 4 buttons
                int userAnswerIndex = JOptionPane.showOptionDialog(
                    guiClass.this,
                    "Which slang word means: " + definition,
                    "Quiz 2",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    optionArray,
                    optionArray[0]
                );
        
                if (userAnswerIndex != -1) {
                    String userAnswer = optionArray[userAnswerIndex];
                    if (userAnswer.equals(correctSlang)) {
                        JOptionPane.showMessageDialog(guiClass.this, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(guiClass.this, "Wrong! The correct answer is: " + correctSlang, "Result", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void setupRandomWordFeature() {
        displayRandomWord();
    
        randomWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayRandomWord();
            }
        });
    }
    
    private void displayRandomWord() {
        String randomWord = controller.getARandomWord();
    
        if (randomWord.contains("->")) {
            String[] parts = randomWord.split("->");
            String slang = parts[0].trim();
            String meaning = parts[1].trim();

            randomWordLabel.setText("Random: " + slang);
            meaningTextArea.setText("Meaning: " + meaning);
        } else {
            randomWordLabel.setText("Random:");
            meaningTextArea.setText("Meaning: " + randomWord);
        }
    }



    public static void createAndShowGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();

        frame.setTitle("Slang Word Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        guiClass newContentPane = new guiClass();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        //frame.setSize(800, 500);
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);
    }
}
