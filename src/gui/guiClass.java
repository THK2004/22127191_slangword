package gui;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;

public class guiClass extends JPanel{
    public guiClass(){
        setLayout(new GridBagLayout());
        JButton topLeft = new JButton("topleft");
        JButton midLeft = new JButton("midleft");
        JButton botLeft = new JButton("botleft");
        JButton topMid = new JButton("topMid");
        JButton topRight = new JButton("topRight");
        JButton remain = new JButton("remain");

        addComponent(this, topLeft, 0, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(0, 0, 5, 5));
        addComponent(this, midLeft, 0, 1, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(5, 0, 5, 5));
        addComponent(this, botLeft, 0, 2, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(5, 0, 0, 5));
        addComponent(this, topMid, 1, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(0, 5, 5, 5));
        addComponent(this, topRight, 2, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(0, 5, 5, 0));
        addComponent(this, remain, 1, 1, 2, 2, 200, 200, GridBagConstraints.BOTH, GridBagConstraints.CENTER, new Insets(5, 5, 0, 0));
    }

    public static void createAndShowGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();

        frame.setTitle("Slang Word Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        guiClass newContentPane = new guiClass();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);
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
}
