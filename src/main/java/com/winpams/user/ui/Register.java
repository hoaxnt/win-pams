package com.winpams.user.ui;

import com.winpams.user.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class Register {
    JPanel panel;
    private JButton register;
    private JPasswordField password;
    private JPasswordField confirm;
    private JLabel feedback;
    private JButton showPassword;
    private JButton showConfirm;
    private JLabel hasAccount;

    public Register() {
        register.addActionListener(this::register);
        password.addActionListener(this::register);
        confirm.addActionListener(this::register);

        showPassword.addActionListener(evt -> toggleShow(evt, password));
        showConfirm.addActionListener(evt -> toggleShow(evt, confirm));

        hasAccount.addMouseListener(adapter);
    }

    private void register(ActionEvent evt) {
        // TODO: Implement registration logic
    }

    private void toggleShow(ActionEvent evt, JPasswordField password) {
        if (password.getEchoChar() == 0)
            password.setEchoChar('•');
        else
            password.setEchoChar((char) 0);
    }

    MouseAdapter adapter = new MouseAdapter() {
        final Font font = hasAccount.getFont();
        final Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());

        public void mouseClicked(MouseEvent evt) {
            Main.navigator.show(new Login().panel);
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
