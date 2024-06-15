package com.winpams.user;

import javax.swing.*;

// TODO: THIS IS A SAMPLE CLASS, IT SHOULD BE REMOVED. REPLACE IT WITH THE ACTUAL LOGIN SCREEN

public class Login {
    JPanel panel;
    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private JLabel feedback;

    public Login() {
        login.addActionListener(e -> login());
        username.addActionListener(e -> login());
        password.addActionListener(e -> login());
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
