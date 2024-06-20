package com.winpams.user;


import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.winpams.core.ui.Navigator;
import com.winpams.user.ui.Login;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static Navigator navigator;

    public static void main(String[] args) {
        JFrame frame = new JFrame("WinPAMS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            FlatMacDarkLaf.setup();
            
            navigator = new Navigator(frame);
            navigator.show(new Login().panel);
        });
    }
}
