import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {

    public HomePage() {

        setTitle("Handwritten Recognition Game");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel(
                "WELCOME TO HANDWRITTEN RECOGNITION GAME",
                SwingConstants.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 26));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 15, 15));

        JButton startButton = new JButton("START GAME");
        JButton instructionButton = new JButton("INSTRUCTIONS");
        JButton exitButton = new JButton("EXIT");

        buttonPanel.add(startButton);
        buttonPanel.add(instructionButton);
        buttonPanel.add(exitButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);

        // EVENTS
        startButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Game Page Open");
        });

        instructionButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Instruction Page Open");
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        HomePage homePage = new HomePage();
    }
}