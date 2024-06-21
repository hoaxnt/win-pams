package com.winpams.user;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.winpams.core.ui.Navigator;
import com.winpams.user.ui.*;
import com.winpams.data.Database;
import com.winpams.core.Config;
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static Navigator navigator;

    public static void main(String[] args) throws Exception {
        Database.connect(Config.get("DB_URL"), Config.get("DB_USER"), Config.get("DB_PASSWORD"));

        JFrame frame = new JFrame("WinPAMS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            FlatMacLightLaf.setup();
            navigator = new Navigator(frame);
            navigator.show(new Login().panel);
        });
    }
}
