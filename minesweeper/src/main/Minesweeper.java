package main;

import javax.swing.*;
import java.awt.*;

public class Minesweeper extends JFrame {
    private JLabel statusLabel;

    public Minesweeper() {
        initUI();
    }

    private void initUI() {
        statusLabel = new JLabel("");
        add(statusLabel, BorderLayout.SOUTH);

        add(new Board(statusLabel));

        setResizable(false);
        pack();

        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Minesweeper ex = new Minesweeper();
            ex.setVisible(true);
        });
    }
}
