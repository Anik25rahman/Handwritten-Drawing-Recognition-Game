import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InstructionPage extends JFrame {

    public InstructionPage() {

        setTitle("Handwritten Recognition Game - Instructions");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("HANDWRITTEN RECOGNITION GAME", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel instructionLabel = new JLabel("Instructions", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea instructionArea = new JTextArea();
        instructionArea.setEditable(false);
        instructionArea.setFont(new Font("Arial", Font.PLAIN, 14));

        instructionArea.setText("""
                                1. Read the displayed character carefully.
                                
                                2. Draw the same character in the drawing area.
                                
                                3. Click the Submit button after completing the drawing.
                                
                                4. The system will recognize your handwriting.
                                
                                5. You will receive points for correct answers.
                                
                                6. Complete all rounds to finish the game.""");

        JScrollPane scrollPane = new JScrollPane(instructionArea);

        JButton startButton = new JButton("Start Game");

        startButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,
                    "Game Page will open here!");
            
            // new GamePage();
            // dispose();
        });

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(titleLabel);
        topPanel.add(instructionLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        InstructionPage instructionPage = new InstructionPage();
    }
}