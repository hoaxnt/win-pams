package com.winpams.user.ui;

import javax.swing.*;
import java.awt.*;

public class MainFeed {

    private JButton menu;
    private JButton profile;
    public JPanel panel;
    private JLabel feedTitle;
    private JScrollPane scroll;
    private JList<String> feed;
    DefaultListModel<String> model = new DefaultListModel<>();
    private int resetRow;
    int visibleCount;

    public MainFeed()
    {
        String[] dogNames = new String[]{
                "Buddy","Max","Luna","Bailey","Rocky",
                "Daisy","Charlie","Bailey","Luna","Max",
                "Daisy","Rocky","Charlie","Bailey","Luna"};

        feedTitle.setFont(new Font("Arial", Font.BOLD, 22));
        feed.setModel(model);
        feed.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        feed.setCellRenderer(new MainFeed.ItemRenderer());

        for (int l=1; l <= dogNames.length; l++)
        {
            resetRow++;
            if (resetRow == 6)
            {
                resetRow = 0;
                visibleCount++;
            }
            if (resetRow < 5 && l == dogNames.length)
            {
                resetRow = 0;
                visibleCount++;
            }
            feed.setVisibleRowCount(visibleCount);
        }
        for (String dog : dogNames) {
            model.addElement(dog);
        }
    }

    static class ItemRenderer extends DefaultListCellRenderer
    {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            JLabel item = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            // Customize the label properties here (e.g., set text, icon, etc.)
            item.setIcon(UIManager.getIcon("OptionPane.informationIcon")); // Example: Set an icon
            item.setFont(new Font("Arial", Font.BOLD, 18));
            item.setPreferredSize(new Dimension(200, 200));
            item.setHorizontalAlignment(SwingConstants.CENTER);
            item.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            return item;
        }
    }
}
