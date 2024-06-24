package com.winpams.user.ui;

import javax.swing.*;
import java.awt.*;

public class MainFeed {

    private JButton menu;
    private JButton profile;
    public JPanel panel;
    private JLabel feedTitle;
    private JScrollPane scroll;
    public JPanel itemsPanel;
    public JPanel miniPanel = new JPanel();
//    int resetRow, visibleCount;

    public MainFeed()
    {

        feedTitle.setFont(new Font("Arial", Font.BOLD, 22));
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        miniPanel.setBackground(Color.RED);
        miniPanel.add(new JButton("panel1"));
        miniPanel.setSize(new Dimension(100,100));
        // TODO: Inaayos ko yung size nung panel pero kahit magset ako ng dimension nagfifill parin sya :/


        String[] dogNames = new String[]{
                "Buddy","Max","Luna","Bailey","Rocky",
                "Daisy","Charlie","Bailey","Luna","Max",
                "Daisy","Rocky","Charlie","Bailey","Luna"};

//        for (int l=1; l <= dogNames.length; l++)
//        {
//            resetRow++;
//            if (resetRow == 5)
//            {
//                resetRow = 0;
//                itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
//            }
//            if (resetRow < 5 && l == dogNames.length)
//            {
//                resetRow = 0;
//                itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
//            }
//
//        }

        for (String dog : dogNames)
        {
            itemsPanel.add(new JButton(dog));
        }
        itemsPanel.add(miniPanel);

    }
}
