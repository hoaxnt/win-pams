package com.winpams.core.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Stack;

public class Navigator {
    private final Stack<JPanel> history = new Stack<>();
    private final Stack<JPanel> forward = new Stack<>();
    private final JFrame main;

    public Navigator(JFrame main) {
        this.main = main;
    }

    public void show(JPanel panel) {
        if (!history.isEmpty()) forward.clear();

        history.push(panel);
        main.setContentPane(panel);
        main.revalidate();
        main.repaint();
    }

    public void back() {
        if (history.isEmpty()) return;

        forward.push(history.pop());

        if (history.isEmpty()) return;

        JPanel panel = history.peek();
        main.setContentPane(panel);
        main.revalidate();
        main.repaint();
    }

    public void clear() {
        history.clear();
        forward.clear();
    }

    public void forward() {
        if (forward.isEmpty()) return;

        JPanel panel = forward.pop();
        history.push(panel);
        main.setContentPane(panel);
        main.revalidate();
        main.repaint();
    }
}
