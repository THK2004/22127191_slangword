package gui;

import java.util.ArrayList;
import java.util.Arrays;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import controller.controllerClass;

public class guiClass extends JPanel{
    private JList<String> slangWordList;
    private ArrayList<String> cachedSlangWordList;
    private DefaultListModel<String> slangWordListModel;
    private controllerClass controller;

    private JTextField searchByName;
    private JTextField searchByDesc;
    private JTextArea contentTextArea;

    public guiClass(){
        controller = new controllerClass();
        cachedSlangWordList = new ArrayList<String>(Arrays.asList(controller.getSlangWordList()));

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
        String searchHistory[] = {"abc", "def", "ghi", "jkl", "mno"};
        JList<String> searchHistoryList = new JList<>(searchHistory);

        JScrollPane historyScrollPane = new JScrollPane(searchHistoryList);
        historyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        historyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        midLeft.add(searchHistoryLabel, BorderLayout.PAGE_START);
        midLeft.add(historyScrollPane, BorderLayout.CENTER);

        //botLeft
        JPanel botLeft = new JPanel(new BorderLayout()); 
        JLabel slangWordLabel = new JLabel("Slang words list");

        slangWordListModel = new DefaultListModel<>();
        getSlangWordList();
        slangWordList = new JList<>(slangWordListModel);

        JScrollPane slangWordScrollPane = new JScrollPane(slangWordList);
        slangWordScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        slangWordScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        botLeft.add(slangWordLabel, BorderLayout.PAGE_START);
        botLeft.add(slangWordScrollPane, BorderLayout.CENTER);

        //topMid
        JPanel topMid = new JPanel();
        topMid.setLayout(new BoxLayout(topMid, BoxLayout.Y_AXIS));

        JLabel onThisDayLabel = new JLabel("On This Day Slang Word: Example");
        JLabel meaningLabel = new JLabel("Meaning: Example");
        JButton randomWordButton = new JButton("Show Random Word");
        JButton startQuizButton = new JButton("Start Quiz");

        onThisDayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        meaningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        randomWordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startQuizButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        topMid.add(onThisDayLabel);
        topMid.add(meaningLabel);
        topMid.add(Box.createRigidArea(new Dimension(0, 10)));
        topMid.add(randomWordButton);
        topMid.add(Box.createRigidArea(new Dimension(0, 5)));
        topMid.add(startQuizButton);

        //topRight
        JPanel topRight = new JPanel(new BorderLayout()); // Use BorderLayout for flexibility

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5)); // 2x2 grid for buttons with spacing
        
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton resetButton = new JButton("Reset");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);

        topRight.add(buttonPanel, BorderLayout.CENTER);
        topRight.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
    
        if (result.equals("Slang not found")) {
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

    private void getSlangWordList() {
        for (String slang : cachedSlangWordList) {
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
                    slangWordListModel.addAll(cachedSlangWordList);
                } else {
                    for (String slang : cachedSlangWordList) {
                        if (slang.toLowerCase().startsWith(query)) {
                            slangWordListModel.addElement(slang);
                        }
                    }
                }
            }
        });
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
