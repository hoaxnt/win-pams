package com.winpams.user;


import com.winpams.core.ui.Navigator;

import javax.swing.*;

public class Main extends JFrame {
    static Navigator navigator;

    public static void main(String[] args) {
        JFrame frame = new JFrame("WinPAMS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            navigator = new Navigator(frame);
            navigator.show(new Login().panel);
        });
    }
}
