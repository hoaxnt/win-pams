package com.winpams.admin;

import javax.swing.*;
import java.awt.*;

public class AdminPanel {
    public JPanel panel;
    private JButton filter;
    private JList<Users> usersList;
    private JButton newButton;
    private JButton edit;
    private JLabel title;
    DefaultListModel<Users> model = new DefaultListModel<>();

    public AdminPanel()
    {
        title.setFont(new Font("Arial", Font.BOLD, 24));
        usersList.setFont(new Font("Arial", Font.PLAIN, 22));
        usersList.setModel(model);
        model.addElement(new Users("Jose", "Rizal", "jriz@cvsu.edu.ph", "09876543210"));
        model.addElement(new Users("Emilio", "Aguinaldo", "ea@cvsu.edu.ph", "09988765432"));
        model.addElement(new Users("Maria", "Garcia", "maria@example.com", "1234567890"));
        model.addElement(new Users("John", "Smith", "john@example.com", "9876543210"));
        model.addElement(new Users("Alice", "Johnson", "alice@example.com", "5551234567"));

//        usersList.getSelectionModel().addListSelectionListener(e -> {
//            Users u = usersList.getSelectedValue();
//            info.setText("First Name: " + u.firstName + System.lineSeparator() + "Last Name: " + u.lastName);
//        });
    }

    static class Users
    {
        String firstName;
        String lastName;
        String email;
        String contact;

        public Users(String firstName, String lastName, String email, String contact)
        {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.contact = contact;
        }

        public String getFirstName()
        {
            return this.firstName;
        }

        public String getLastName()
        {
            return this.lastName;
        }

        public String getEmail()
        {
            return this.email;
        }

        public String getContact()
        {
            return this.contact;
        }

        public String toString()
        {
            return this.firstName + " ::: "+ this.lastName + " ::: "+  this.email + " ::: "+ this.contact;
        }
    }
}
