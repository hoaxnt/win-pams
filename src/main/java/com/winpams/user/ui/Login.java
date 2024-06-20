package com.winpams.user.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;


import com.winpams.core.model.User;
import com.winpams.user.Main;

// TODO: THIS IS A SAMPLE CLASS, IT SHOULD BE REMOVED. REPLACE IT WITH THE ACTUAL LOGIN SCREEN

public class Login {
    public JPanel panel;
    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private JLabel feedback;
    private JLabel hasAccount;

    public Login() {
        login.addActionListener(this::login);
        username.addActionListener(this::login);
        password.addActionListener(this::login);
        hasAccount.addMouseListener(adapter);
    }

    private void login(ActionEvent evt) {
        String user = username.getText();
        String pass = new String(password.getPassword());

        try {
            User u = User.query.where("username", user).first();

            if (u == null) {
                feedback.setText("Invalid username.");
                return;
            }

            if (!u.verify(pass)) {
                String message = feedback.getText();
                feedback.setText(message.isEmpty() ? "Invalid password." : message.substring(0, message.indexOf('.')) + " or password.");
                return;
            }

            feedback.setText("Login successful.");

            Main.navigator.show(new Feed().panel);
        } catch (Exception e) {
            feedback.setText("An error occurred. Please try again later.");
        } finally {
            feedback.setVisible(true);
        }
    }

    MouseAdapter adapter = new MouseAdapter() {
        final Font font = hasAccount.getFont();
        final Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());

        public void mouseClicked(MouseEvent evt) {
            Main.navigator.show(new Register().panel);
        }

        public void mouseEntered(MouseEvent evt) {
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            hasAccount.setFont(font.deriveFont(attributes));
            hasAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        public void mouseExited(MouseEvent evt) {
            attributes.put(TextAttribute.UNDERLINE, -1);
            hasAccount.setFont(font.deriveFont(attributes));
            hasAccount.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    };
}
