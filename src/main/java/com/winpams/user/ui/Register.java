package com.winpams.user.ui;

import com.winpams.core.model.User;
import com.winpams.core.utils.Hasher;
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
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JTextField contact;
    private JTextField username;

    public Register() {
        register.addActionListener(this::register);
        password.addActionListener(this::register);
        confirm.addActionListener(this::register);
        firstName.addActionListener(this::register);
        lastName.addActionListener(this::register);
        email.addActionListener(this::register);
        contact.addActionListener(this::register);

        showPassword.addActionListener(evt -> toggleShow(evt, password));
        showConfirm.addActionListener(evt -> toggleShow(evt, confirm));

        hasAccount.addMouseListener(adapter);
    }

    private void register(ActionEvent evt) {
        StringBuilder message = new StringBuilder();
        User user = new User();

        String first = firstName.getText();
        String last = lastName.getText();
        String mail = email.getText();
        String phone = contact.getText();
        String usernameString = username.getText();
        String pass = new String(password.getPassword());
        String conf = new String(confirm.getPassword());

        boolean err = false;

        if (first.isEmpty() || last.isEmpty() || mail.isEmpty() || phone.isEmpty() || usernameString.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
            message.append("Please fill in all fields.\n");
            err = true;
        }

        String validPass = validatePassword();

        if (!validPass.isEmpty()) {
            message.append(validPass);
            err = true;
        }


        if (!mail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            message.append("Invalid email format.\n");
            err = true;
        }

        if (!phone.matches("\\d{10}")) {
            message.append("Invalid phone number format. It should contain exactly 10 digits.\n");
            err = true;
        }

        if (err) {
            feedback.setText(message.toString());
            feedback.setVisible(true);
            return;
        }

        user.givenName = first;
        user.lastName = last;
        user.email = mail;
        user.contactNumber = phone;
        user.username = usernameString;
        user.password = Hasher.hashPassword(pass);

        try {
            if (User.query.where("username", usernameString).first() != null) {
                message.append("Username already exists.\n");
                err = true;
            }

            if (User.query.where("email", mail).first() != null) {
                message.append("Email already exists.\n");
                err = true;
            }

            if (err) {
                feedback.setText(message.toString());
                feedback.setVisible(true);
                return;
            }

            user.save();
            feedback.setText("Registration successful.");

            boolean ok = JOptionPane.showConfirmDialog(
                    panel,
                    "Registration successful.",
                    "Success",
                    JOptionPane.DEFAULT_OPTION
            ) == JOptionPane.OK_OPTION;

            if (!ok) return;

            Main.navigator.show(new Login().panel);
        } catch (Exception e) {
            feedback.setText("An error occurred. Please try again later.");
            feedback.setVisible(true);
            e.printStackTrace(); // TODO: Add proper logging
        } finally {
            feedback.setVisible(true);
        }
    }

    private void toggleShow(ActionEvent evt, JPasswordField password) {
        if (password.getEchoChar() == 0)
            password.setEchoChar('•');
        else
            password.setEchoChar((char) 0);
    }

    private String validatePassword() {
        StringBuilder message = new StringBuilder();
        String password = new String(this.password.getPassword());
        String confirm = new String(this.confirm.getPassword());

        if (password.length() < 8)
            message.append("Password must be at least 8 characters long.\n");

        if (!password.matches(".*\\d.*"))
            message.append("Password must contain at least one digit.\n");

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~].*"))
            message.append("Password must contain at least one special character.\n");

        if (!password.matches(".*[A-Z].*"))
            message.append("Password must contain at least one uppercase letter.\n");

        if (!password.matches(".*[a-z].*"))
            message.append("Password must contain at least one lowercase letter.\n");

        if (!password.equals(confirm))
            message.append("Passwords do not match.\n");

        return message.toString();
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
