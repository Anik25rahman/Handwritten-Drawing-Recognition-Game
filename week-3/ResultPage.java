import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResultPage extends JFrame {

    public ResultPage() {

        setTitle("Handwritten Recognition Game - Result Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("GAME RESULT", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // Result Information
        JLabel resultLabel = new JLabel("Recognition Successful!", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel scoreLabel = new JLabel("Final Score: 8 / 10", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        centerPanel.add(resultLabel);
        centerPanel.add(scoreLabel);

        // Buttons
        JButton playAgainButton = new JButton("Play Again");
        JButton homeButton = new JButton("Home");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playAgainButton);
        buttonPanel.add(homeButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // Play Again Button
        playAgainButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,
                    "Game Page will open here!");
            
            // new GamePage();
            // dispose();
        });

        // Home Button
        homeButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,
                    "Home Page will open here!");
            
            // new HomePage();
            // dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        ResultPage resultPage = new ResultPage();
    }
}