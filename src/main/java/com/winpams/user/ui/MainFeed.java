package com.winpams.user.ui;

import javax.swing.*;
import java.awt.*;

public class MainFeed {
    private JButton menu;
    private JButton profile;

    public JPanel panel;
    private JList<String> feed;
    private JList<String> feed2;
    private JLabel catTitle;
    private JLabel dogTitle;
    DefaultListModel<String> model = new DefaultListModel<>();
    DefaultListModel<String> model2 = new DefaultListModel<>();
    public MainFeed()
    {
        dogTitle.setFont(new Font("Arial", Font.BOLD, 22));
        catTitle.setFont(new Font("Arial", Font.BOLD, 22));
        feed.setModel(model);
        feed.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        feed.setCellRenderer(new MainFeed.DogLabelRenderer());

        model.addElement("Dog 1");
        model.addElement("Dog 2");
        model.addElement("Dog 3");
        model.addElement("Dog 4");
        model.addElement("Dog 5");
        model.addElement("Dog 6");
        model.addElement("Dog 7");
        model.addElement("Dog 8");

        feed2.setModel(model2);
        feed2.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        feed2.setCellRenderer(new MainFeed.CatLabelRenderer());

        model2.addElement("Cat 1");
        model2.addElement("Cat 2");
        model2.addElement("Cat 3");
        model2.addElement("Cat 4");
        model2.addElement("Cat 5");
        model2.addElement("Cat 6");
        model2.addElement("Cat 7");
        model2.addElement("Cat 8");
    }

    static class DogLabelRenderer extends DefaultListCellRenderer
    {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            // Customize the label properties here (e.g., set text, icon, etc.)
            label.setIcon(UIManager.getIcon("OptionPane.informationIcon")); // Example: Set an icon
            label.setFont(new Font("Arial", Font.BOLD, 24));
            return label;
        }
    }

    static class CatLabelRenderer extends DefaultListCellRenderer
    {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            // Customize the label properties here (e.g., set text, icon, etc.)
            label.setIcon(UIManager.getIcon("OptionPane.informationIcon")); // Example: Set an icon
            label.setFont(new Font("Arial", Font.BOLD, 24));
            label.setBounds(0,0,300,100);
            return label;
        }
    }
}
