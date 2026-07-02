import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePage extends JFrame {

    public GamePage() {

        setTitle("Handwritten Recognition Game - Game Page");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("HANDWRITTEN RECOGNITION GAME", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Character Display
        JLabel characterLabel = new JLabel("Write Character: A", JLabel.CENTER);
        characterLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Drawing Area Placeholder
        JPanel drawingPanel = new JPanel();
        drawingPanel.setBorder(BorderFactory.createTitledBorder("Drawing Area"));
        drawingPanel.setPreferredSize(new Dimension(400, 250));

        JLabel drawingLabel = new JLabel("Canvas will be implemented in Week 4");
        drawingPanel.add(drawingLabel);

        // Score Label
        JLabel scoreLabel = new JLabel("Score: 0");

        // Buttons
        JButton clearButton = new JButton("Clear");
        JButton submitButton = new JButton("Submit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(submitButton);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(titleLabel);
        topPanel.add(characterLabel);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(scoreLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(drawingPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Clear Button Action
        clearButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,
                    "Drawing area cleared!");
        });

        // Submit Button Action
        submitButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,
                    "Result Page will open here!");
            
            // new ResultPage();
            // dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        GamePage GamePage = new GamePage(); /*gamePage*/
    }
}