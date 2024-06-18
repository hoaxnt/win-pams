package com.winpams.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import com.winpams.user.ui.Register;

// TODO: THIS IS A SAMPLE CLASS, IT SHOULD BE REMOVED. REPLACE IT WITH THE ACTUAL LOGIN SCREEN

public class Login {
    JPanel panel;
    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private JLabel feedback;
    private JLabel hasAccount;

    public Login() {
        login.addActionListener(e -> login());
        username.addActionListener(e -> login());
        password.addActionListener(e -> login());

        hasAccount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                Main.navigator.show(new Register().panel);
            }

            public void mouseEntered(MouseEvent evt) {
                // Change the text to underline when the mouse enters the label
                Font font = hasAccount.getFont();

                Map attributes = font.getAttributes();

                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

                hasAccount.setFont(font.deriveFont(attributes));
            }

            public void mouseExited(MouseEvent evt) {
                // Change the text to normal when the mouse exits the label
                Font font = hasAccount.getFont();

                Map attributes = font.getAttributes();

                attributes.put(TextAttribute.UNDERLINE, -1);

                hasAccount.setFont(font.deriveFont(attributes));
            }
        });
    }

    private void login() {
        // Create a new panel to display the welcome message should be replaced with the actual application.
        JPanel test = new JPanel();
        JButton logout = new JButton("Logout");
        test.add(new JLabel("Welcome"));
        test.add(logout);
        logout.addActionListener(e -> Main.navigator.back());

        String user = username.getText();
        String pass = new String(password.getPassword());

        if (user.equals("admin") && pass.equals("admin")) {
            feedback.setText("Login successful");
            feedback.setVisible(true);

            username.setText("");
            password.setText("");

            Main.navigator.show(test);
        } else {
            feedback.setText("Login failed");
            feedback.setVisible(true);
        }
    }
}
