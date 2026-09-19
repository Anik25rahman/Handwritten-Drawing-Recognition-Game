package com.mycompany;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GamePage extends JFrame {

    // ==============================
    // GAME VARIABLES
    // ==============================

    private int timeLeft = 30;
    private int score = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    private char currentCharacter;

    // A-Z + 0-9 = 36 characters
    private final char[] characters = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    // ==============================
    // GUI COMPONENTS
    // ==============================

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JLabel targetLabel;
    private JLabel timerLabel;
    private JLabel scoreLabel;
    private JLabel feedbackLabel;

    private JLabel finalScoreLabel;
    private JLabel finalCorrectLabel;
    private JLabel finalWrongLabel;

    private DrawingCanvas drawingCanvas;

    private Timer gameTimer;

    // ==============================
    // RECOGNITION SETTINGS
    // ==============================

    private static final int NORMALIZED_SIZE = 64;

    /*
     * Minimum score required for recognition.
     */
    private static final double MIN_RECOGNITION_SCORE = 0.48;

    /*
     * Difference between best and second-best
     * character score.
     */
    private static final double MIN_SCORE_MARGIN = 0.015;

    /*
     * Character templates are stored here.
     */
    private final Map<Character, List<BufferedImage>> templateCache
            = new HashMap<Character, List<BufferedImage>>();

    // ==============================
    // CONSTRUCTOR
    // ==============================

    public GamePage() {

        setTitle("Handwritten Recognition Game");

        setSize(900, 600);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(false);

        cardLayout = new CardLayout();

        mainPanel = new JPanel(cardLayout);

        // Create all pages
        mainPanel.add(
                createHomePage(),
                "HOME"
        );

        mainPanel.add(
                createInstructionPage(),
                "INSTRUCTIONS"
        );

        mainPanel.add(
                createGamePage(),
                "GAME"
        );

        mainPanel.add(
                createResultPage(),
                "RESULT"
        );

        add(mainPanel);

        showPage("HOME");
    }

    // ============================================================
    // HOME PAGE
    // ============================================================

    private JPanel createHomePage() {

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(Color.WHITE);

        JLabel title =
                new JLabel(
                        "WELCOME TO HANDWRITTEN RECOGNITION GAME",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        60,
                        10,
                        40,
                        10
                )
        );

        panel.add(
                title,
                BorderLayout.NORTH
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(Color.WHITE);

        JButton startButton =
                new JButton("START GAME");

        JButton instructionButton =
                new JButton("INSTRUCTIONS");

        JButton exitButton =
                new JButton("EXIT");

        startButton.setPreferredSize(
                new Dimension(200, 50)
        );

        instructionButton.setPreferredSize(
                new Dimension(200, 50)
        );

        exitButton.setPreferredSize(
                new Dimension(200, 50)
        );

        startButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        instructionButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        exitButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        // START GAME
        startButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        startNewGame();
                    }
                }
        );

        // INSTRUCTIONS
        instructionButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        showPage("INSTRUCTIONS");
                    }
                }
        );

        // EXIT
        exitButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        System.exit(0);
                    }
                }
        );

        buttonPanel.add(startButton);
        buttonPanel.add(instructionButton);
        buttonPanel.add(exitButton);

        panel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        return panel;
    }

    // ============================================================
    // INSTRUCTION PAGE
    // ============================================================

    private JPanel createInstructionPage() {

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(Color.WHITE);

        JLabel title =
                new JLabel(
                        "HOW TO PLAY",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        10,
                        20,
                        10
                )
        );

        panel.add(
                title,
                BorderLayout.NORTH
        );

        JTextArea instructions =
                new JTextArea();

        instructions.setEditable(false);

        instructions.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );

        instructions.setBackground(
                Color.WHITE
        );

        instructions.setLineWrap(true);

        instructions.setWrapStyleWord(true);

        instructions.setText(
                "1. Click START GAME to begin.\n\n"
                + "2. A random letter or number will appear.\n\n"
                + "3. Draw the displayed character using your mouse.\n\n"
                + "4. Click CLEAR to erase your drawing.\n\n"
                + "5. Click SUBMIT to check your drawing.\n\n"
                + "6. A correct answer gives +10 points.\n\n"
                + "7. An incorrect answer is counted as wrong.\n\n"
                + "8. You have 30 seconds to play.\n\n"
                + "9. The final score, correct answers and "
                + "wrong answers will be displayed."
        );

        instructions.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        80,
                        10,
                        80
                )
        );

        panel.add(
                instructions,
                BorderLayout.CENTER
        );

        JButton backButton =
                new JButton("BACK");

        backButton.setPreferredSize(
                new Dimension(120, 40)
        );

        backButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        showPage("HOME");
                    }
                }
        );

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setBackground(
                Color.WHITE
        );

        bottomPanel.add(backButton);

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        return panel;
    }

    // ============================================================
    // GAME PAGE
    // ============================================================

    private JPanel createGamePage() {

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(Color.WHITE);

        // --------------------------
        // TOP PANEL
        // --------------------------

        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.setBackground(
                Color.WHITE
        );

        targetLabel =
                new JLabel(
                        "Character: -",
                        SwingConstants.CENTER
                );

        targetLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        timerLabel =
                new JLabel(
                        "Time: 30",
                        SwingConstants.CENTER
                );

        timerLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        scoreLabel =
                new JLabel(
                        "Score: 0",
                        SwingConstants.CENTER
                );

        scoreLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        topPanel.add(
                targetLabel,
                BorderLayout.CENTER
        );

        topPanel.add(
                timerLabel,
                BorderLayout.WEST
        );

        topPanel.add(
                scoreLabel,
                BorderLayout.EAST
        );

        panel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // --------------------------
        // DRAWING CANVAS
        // --------------------------

        drawingCanvas =
                new DrawingCanvas();

        drawingCanvas.setPreferredSize(
                new Dimension(
                        700,
                        350
                )
        );

        JPanel canvasContainer =
                new JPanel(
                        new GridBagLayout()
                );

        canvasContainer.setBackground(
                Color.WHITE
        );

        canvasContainer.add(
                drawingCanvas
        );

        panel.add(
                canvasContainer,
                BorderLayout.CENTER
        );

        // --------------------------
        // FEEDBACK
        // --------------------------

        feedbackLabel =
                new JLabel(
                        "Draw the character and click SUBMIT.",
                        SwingConstants.CENTER
                );

        feedbackLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        // --------------------------
        // BUTTONS
        // --------------------------

        JButton clearButton =
                new JButton("CLEAR");

        JButton submitButton =
                new JButton("SUBMIT");

        clearButton.setPreferredSize(
                new Dimension(
                        130,
                        45
                )
        );

        submitButton.setPreferredSize(
                new Dimension(
                        130,
                        45
                )
        );

        clearButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        submitButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        // CLEAR
        clearButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        if (timeLeft <= 0) {
                            return;
                        }

                        drawingCanvas.clearCanvas();

                        feedbackLabel.setText(
                                "Canvas cleared. Draw again."
                        );
                    }
                }
        );

        // SUBMIT
        submitButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        submitDrawing();
                    }
                }
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(
                Color.WHITE
        );

        buttonPanel.add(clearButton);
        buttonPanel.add(submitButton);

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setBackground(
                Color.WHITE
        );

        bottomPanel.add(
                feedbackLabel,
                BorderLayout.NORTH
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        return panel;
    }

    // ============================================================
    // RESULT PAGE
    // ============================================================

    private JPanel createResultPage() {

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(
                Color.WHITE
        );

        JLabel title =
                new JLabel(
                        "GAME RESULT",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        50,
                        10,
                        40,
                        10
                )
        );

        panel.add(
                title,
                BorderLayout.NORTH
        );

        JPanel resultPanel =
                new JPanel();

        resultPanel.setBackground(
                Color.WHITE
        );

        resultPanel.setLayout(
                new BoxLayout(
                        resultPanel,
                        BoxLayout.Y_AXIS
                )
        );

        finalScoreLabel =
                new JLabel(
                        "Final Score: 0"
                );

        finalCorrectLabel =
                new JLabel(
                        "Correct Answers: 0"
                );

        finalWrongLabel =
                new JLabel(
                        "Wrong Answers: 0"
                );

        Font resultFont =
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                );

        finalScoreLabel.setFont(
                resultFont
        );

        finalCorrectLabel.setFont(
                resultFont
        );

        finalWrongLabel.setFont(
                resultFont
        );

        finalScoreLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        finalCorrectLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        finalWrongLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        resultPanel.add(
                finalScoreLabel
        );

        resultPanel.add(
                Box.createVerticalStrut(20)
        );

        resultPanel.add(
                finalCorrectLabel
        );

        resultPanel.add(
                Box.createVerticalStrut(20)
        );

        resultPanel.add(
                finalWrongLabel
        );

        panel.add(
                resultPanel,
                BorderLayout.CENTER
        );

        JButton playAgainButton =
                new JButton("PLAY AGAIN");

        playAgainButton.setPreferredSize(
                new Dimension(
                        160,
                        45
                )
        );

        playAgainButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        playAgainButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        startNewGame();
                    }
                }
        );

        JButton homeButton =
                new JButton("HOME");

        homeButton.setPreferredSize(
                new Dimension(
                        120,
                        45
                )
        );

        homeButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        homeButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        showPage("HOME");
                    }
                }
        );

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setBackground(
                Color.WHITE
        );

        bottomPanel.add(
                playAgainButton
        );

        bottomPanel.add(
                homeButton
        );

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        return panel;
    }

    // ============================================================
    // START NEW GAME
    // ============================================================

    private void startNewGame() {

        if (gameTimer != null) {
            gameTimer.stop();
        }

        timeLeft = 30;

        score = 0;

        correctAnswers = 0;

        wrongAnswers = 0;

        scoreLabel.setText(
                "Score: 0"
        );

        timerLabel.setText(
                "Time: 30"
        );

        feedbackLabel.setText(
                "Draw the character and click SUBMIT."
        );

        showPage("GAME");

        drawingCanvas.clearCanvas();

        nextRound();

        startTimer();
    }

    // ============================================================
    // NEXT ROUND
    // ============================================================

    private void nextRound() {

        /*
         * Random class is properly imported
         * at the top of the program.
         */
        Random random =
                new Random();

        currentCharacter =
                characters[
                        random.nextInt(
                                characters.length
                        )
                ];

        targetLabel.setText(
                "Character: "
                + currentCharacter
        );

        drawingCanvas.clearCanvas();

        feedbackLabel.setText(
                "Draw: "
                + currentCharacter
        );
    }

    // ============================================================
    // TIMER
    // ============================================================

    private void startTimer() {

        gameTimer =
                new Timer(
                        1000,
                        new ActionListener() {

                            @Override
                            public void actionPerformed(
                                    ActionEvent e
                            ) {

                                timeLeft--;

                                timerLabel.setText(
                                        "Time: "
                                        + timeLeft
                                );

                                if (timeLeft <= 0) {

                                    gameTimer.stop();

                                    showResult();
                                }
                            }
                        }
                );

        gameTimer.start();
    }

    // ============================================================
    // SHOW RESULT
    // ============================================================

    private void showResult() {

        finalScoreLabel.setText(
                "Final Score: "
                + score
        );

        finalCorrectLabel.setText(
                "Correct Answers: "
                + correctAnswers
        );

        finalWrongLabel.setText(
                "Wrong Answers: "
                + wrongAnswers
        );

        showPage("RESULT");
    }

    // ============================================================
    // SUBMIT DRAWING
    // ============================================================

    private void submitDrawing() {

        if (timeLeft <= 0) {
            return;
        }

        if (!drawingCanvas.hasDrawing()) {

            feedbackLabel.setText(
                    "Please draw something first."
            );

            return;
        }

        BufferedImage image =
                drawingCanvas.getCanvasImage();

        /*
         * The drawing is compared with
         * all 36 characters.
         */
        RecognitionResult result =
                recognizeCharacter(
                        image,
                        currentCharacter
                );

        if (result.correct) {

            score += 10;

            correctAnswers++;

            scoreLabel.setText(
                    "Score: "
                    + score
            );

            feedbackLabel.setText(
                    "CORRECT! +10"
                    + " | Detected: "
                    + result.detectedCharacter
            );

            /*
             * Wait 500 milliseconds so that
             * CORRECT message can be seen.
             */
            Timer nextRoundTimer =
                    new Timer(
                            500,
                            new ActionListener() {

                                @Override
                                public void actionPerformed(
                                        ActionEvent e
                                ) {

                                    Timer timer =
                                            (Timer) e.getSource();

                                    timer.stop();

                                    if (timeLeft > 0) {

                                        nextRound();
                                    }
                                }
                            }
                    );

            nextRoundTimer.setRepeats(false);

            nextRoundTimer.start();

        } else {

            wrongAnswers++;

            feedbackLabel.setText(
                    "WRONG! Detected: "
                    + result.detectedCharacter
                    + " | Target: "
                    + currentCharacter
            );
        }
    }

    // ============================================================
    // RECOGNITION RESULT
    // ============================================================

    private static class RecognitionResult {

        boolean correct;

        char detectedCharacter;

        double bestScore;

        double secondBestScore;

        RecognitionResult(
                boolean correct,
                char detectedCharacter,
                double bestScore,
                double secondBestScore
        ) {

            this.correct =
                    correct;

            this.detectedCharacter =
                    detectedCharacter;

            this.bestScore =
                    bestScore;

            this.secondBestScore =
                    secondBestScore;
        }
    }

    // ============================================================
    // RECOGNIZE CHARACTER
    // ============================================================

    private RecognitionResult recognizeCharacter(
            BufferedImage image,
            char target
    ) {

        BufferedImage userDrawing =
                normalizeDrawing(image);

        if (userDrawing == null) {

            return new RecognitionResult(
                    false,
                    '?',
                    0.0,
                    0.0
            );
        }

        char bestCharacter = '?';

        double bestCharacterScore = -1.0;

        double secondBestCharacterScore = -1.0;

        /*
         * Compare user drawing with ALL 36
         * characters.
         */
        for (char character : characters) {

            List<BufferedImage> templates =
                    getTemplates(character);

            double characterBestScore = -1.0;

            /*
             * Compare with all templates
             * of this character.
             */
            for (BufferedImage template :
                    templates) {

                double comparisonScore =
                        compareImages(
                                userDrawing,
                                template
                        );

                if (comparisonScore
                        > characterBestScore) {

                    characterBestScore =
                            comparisonScore;
                }
            }

            /*
             * Find best and second-best
             * characters.
             */
            if (characterBestScore
                    > bestCharacterScore) {

                secondBestCharacterScore =
                        bestCharacterScore;

                bestCharacterScore =
                        characterBestScore;

                bestCharacter =
                        character;

            } else if (
                    characterBestScore
                    > secondBestCharacterScore
            ) {

                secondBestCharacterScore =
                        characterBestScore;
            }
        }

        boolean isCorrect =
                bestCharacter == target
                && bestCharacterScore
                >= MIN_RECOGNITION_SCORE
                && (
                    bestCharacterScore
                    - secondBestCharacterScore
                )
                >= MIN_SCORE_MARGIN;

        return new RecognitionResult(
                isCorrect,
                bestCharacter,
                bestCharacterScore,
                secondBestCharacterScore
        );
    }

    // ============================================================
    // GET CHARACTER TEMPLATES
    // ============================================================

    private List<BufferedImage> getTemplates(
            char character
    ) {

        if (templateCache.containsKey(
                character
        )) {

            return templateCache.get(
                    character
            );
        }

        List<BufferedImage> templates =
                new ArrayList<BufferedImage>();

        /*
         * Multiple fonts are used so the system
         * can handle different handwriting styles.
         */
        Font[] fonts = {

            new Font(
                    "Arial",
                    Font.BOLD,
                    72
            ),

            new Font(
                    "Arial",
                    Font.PLAIN,
                    72
            ),

            new Font(
                    "SansSerif",
                    Font.BOLD,
                    72
            ),

            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    72
            ),

            new Font(
                    "Serif",
                    Font.BOLD,
                    72
            ),

            new Font(
                    "Serif",
                    Font.PLAIN,
                    72
            ),

            new Font(
                    "Monospaced",
                    Font.BOLD,
                    72
            ),

            new Font(
                    "Monospaced",
                    Font.PLAIN,
                    72
            )
        };

        for (Font font : fonts) {

            BufferedImage template =
                    createCharacterTemplate(
                            character,
                            font
                    );

            if (template != null) {

                templates.add(
                        template
                );
            }
        }

        templateCache.put(
                character,
                templates
        );

        return templates;
    }

    // ============================================================
    // CREATE CHARACTER TEMPLATE
    // ============================================================

    private BufferedImage createCharacterTemplate(
            char character,
            Font font
    ) {

        int size = 140;

        BufferedImage image =
                new BufferedImage(
                        size,
                        size,
                        BufferedImage.TYPE_INT_RGB
                );

        Graphics2D g =
                image.createGraphics();

        // White background
        g.setColor(Color.WHITE);

        g.fillRect(
                0,
                0,
                size,
                size
        );

        // Black text
        g.setColor(Color.BLACK);

        g.setFont(font);

        /*
         * Turn off text anti-aliasing.
         */
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF
        );

        FontMetrics metrics =
                g.getFontMetrics();

        String text =
                String.valueOf(
                        character
                );

        int textWidth =
                metrics.stringWidth(
                        text
                );

        int textHeight =
                metrics.getAscent();

        int x =
                (size - textWidth)
                / 2;

        int y =
                (size - textHeight)
                / 2
                + metrics.getAscent();

        g.drawString(
                text,
                x,
                y
        );

        g.dispose();

        return normalizeDrawing(
                image
        );
    }

    // ============================================================
    // NORMALIZE DRAWING
    // ============================================================

    private BufferedImage normalizeDrawing(
            BufferedImage source
    ) {

        if (source == null) {
            return null;
        }

        int width =
                source.getWidth();

        int height =
                source.getHeight();

        int minX = width;
        int minY = height;

        int maxX = -1;
        int maxY = -1;

        /*
         * Find the bounding box of
         * the drawing.
         */
        for (int y = 0;
                y < height;
                y++) {

            for (int x = 0;
                    x < width;
                    x++) {

                if (isBlack(
                        source.getRGB(
                                x,
                                y
                        )
                )) {

                    if (x < minX) {
                        minX = x;
                    }

                    if (y < minY) {
                        minY = y;
                    }

                    if (x > maxX) {
                        maxX = x;
                    }

                    if (y > maxY) {
                        maxY = y;
                    }
                }
            }
        }

        /*
         * No drawing.
         */
        if (maxX == -1
                || maxY == -1) {

            return null;
        }

        int boxWidth =
                maxX - minX + 1;

        int boxHeight =
                maxY - minY + 1;

        /*
         * Reject extremely tiny marks.
         */
        if (boxWidth < 3
                || boxHeight < 3) {

            return null;
        }

        BufferedImage normalized =
                new BufferedImage(
                        NORMALIZED_SIZE,
                        NORMALIZED_SIZE,
                        BufferedImage.TYPE_INT_RGB
                );

        Graphics2D g =
                normalized.createGraphics();

        // White background
        g.setColor(Color.WHITE);

        g.fillRect(
                0,
                0,
                NORMALIZED_SIZE,
                NORMALIZED_SIZE
        );

        int padding = 7;

        int available =
                NORMALIZED_SIZE
                - (padding * 2);

        /*
         * Preserve aspect ratio.
         */
        double scaleX =
                (double) available
                / boxWidth;

        double scaleY =
                (double) available
                / boxHeight;

        double scale =
                Math.min(
                        scaleX,
                        scaleY
                );

        int newWidth =
                Math.max(
                        1,
                        (int) Math.round(
                                boxWidth
                                * scale
                        )
                );

        int newHeight =
                Math.max(
                        1,
                        (int) Math.round(
                                boxHeight
                                * scale
                        )
                );

        int destinationX =
                (
                    NORMALIZED_SIZE
                    - newWidth
                ) / 2;

        int destinationY =
                (
                    NORMALIZED_SIZE
                    - newHeight
                ) / 2;

        g.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        );

        g.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED
        );

        /*
         * Copy cropped character into
         * normalized image.
         */
        g.drawImage(
                source,
                destinationX,
                destinationY,
                destinationX + newWidth,
                destinationY + newHeight,
                minX,
                minY,
                maxX + 1,
                maxY + 1,
                null
        );

        g.dispose();

        return normalized;
    }

    // ============================================================
    // CHECK BLACK PIXEL
    // ============================================================

    private boolean isBlack(
            int rgb
    ) {

        int red =
                (rgb >> 16) & 0xFF;

        int green =
                (rgb >> 8) & 0xFF;

        int blue =
                rgb & 0xFF;

        int brightness =
                (
                    red
                    + green
                    + blue
                ) / 3;

        return brightness < 180;
    }

    // ============================================================
    // COMPARE TWO IMAGES
    // ============================================================

    private double compareImages(
            BufferedImage user,
            BufferedImage template
    ) {

        boolean[][] userPixels =
                createPixelArray(
                        user
                );

        boolean[][] templatePixels =
                createPixelArray(
                        template
                );

        int userCount =
                countBlackPixels(
                        userPixels
                );

        int templateCount =
                countBlackPixels(
                        templatePixels
                );

        if (userCount == 0
                || templateCount == 0) {

            return 0.0;
        }

        // --------------------------------
        // PIXEL OVERLAP
        // --------------------------------

        int intersection = 0;

        for (int y = 0;
                y < NORMALIZED_SIZE;
                y++) {

            for (int x = 0;
                    x < NORMALIZED_SIZE;
                    x++) {

                if (
                    userPixels[y][x]
                    && templatePixels[y][x]
                ) {

                    intersection++;
                }
            }
        }

        double overlapScore =
                (
                    2.0
                    * intersection
                )
                /
                (
                    userCount
                    + templateCount
                );

        // --------------------------------
        // DISTANCE SCORE
        // --------------------------------

        int[][] userDistance =
                createDistanceMap(
                        userPixels
                );

        int[][] templateDistance =
                createDistanceMap(
                        templatePixels
                );

        double userToTemplate =
                averageDistance(
                        userPixels,
                        templateDistance
                );

        double templateToUser =
                averageDistance(
                        templatePixels,
                        userDistance
                );

        double averageDistance =
                (
                    userToTemplate
                    + templateToUser
                ) / 2.0;

        double distanceScore =
                Math.exp(
                        -averageDistance
                        / 8.0
                );

        // --------------------------------
        // PROJECTION SCORE
        // --------------------------------

        double projectionScore =
                calculateProjectionScore(
                        userPixels,
                        templatePixels,
                        userCount,
                        templateCount
                );

        // --------------------------------
        // DENSITY SCORE
        // --------------------------------

        double userDensity =
                (double) userCount
                /
                (
                    NORMALIZED_SIZE
                    * NORMALIZED_SIZE
                );

        double templateDensity =
                (double) templateCount
                /
                (
                    NORMALIZED_SIZE
                    * NORMALIZED_SIZE
                );

        double densityDifference =
                Math.abs(
                        userDensity
                        - templateDensity
                );

        double maxDensity =
                Math.max(
                        userDensity,
                        templateDensity
                );

        double densityScore;

        if (maxDensity == 0) {

            densityScore = 0.0;

        } else {

            densityScore =
                    1.0
                    -
                    (
                        densityDifference
                        / maxDensity
                    );
        }

        if (densityScore < 0) {
            densityScore = 0;
        }

        // --------------------------------
        // FINAL SCORE
        // --------------------------------

        double finalScore =
                (
                    distanceScore
                    * 0.45
                )
                +
                (
                    overlapScore
                    * 0.30
                )
                +
                (
                    projectionScore
                    * 0.15
                )
                +
                (
                    densityScore
                    * 0.10
                );

        if (finalScore < 0) {
            finalScore = 0;
        }

        if (finalScore > 1) {
            finalScore = 1;
        }

        return finalScore;
    }

    // ============================================================
    // CREATE PIXEL ARRAY
    // ============================================================

    private boolean[][] createPixelArray(
            BufferedImage image
    ) {

        boolean[][] pixels =
                new boolean[
                        NORMALIZED_SIZE
                ][
                        NORMALIZED_SIZE
                ];

        for (int y = 0;
                y < NORMALIZED_SIZE;
                y++) {

            for (int x = 0;
                    x < NORMALIZED_SIZE;
                    x++) {

                pixels[y][x] =
                        isBlack(
                                image.getRGB(
                                        x,
                                        y
                                )
                        );
            }
        }

        return pixels;
    }

    // ============================================================
    // COUNT BLACK PIXELS
    // ============================================================

    private int countBlackPixels(
            boolean[][] pixels
    ) {

        int count = 0;

        for (int y = 0;
                y < NORMALIZED_SIZE;
                y++) {

            for (int x = 0;
                    x < NORMALIZED_SIZE;
                    x++) {

                if (pixels[y][x]) {

                    count++;
                }
            }
        }

        return count;
    }

    // ============================================================
    // CREATE DISTANCE MAP
    // ============================================================

    private int[][] createDistanceMap(
            boolean[][] pixels
    ) {

        int[][] distance =
                new int[
                        NORMALIZED_SIZE
                ][
                        NORMALIZED_SIZE
                ];

        int INF = 10000;

        // Initial values
        for (int y = 0;
                y < NORMALIZED_SIZE;
                y++) {

            for (int x = 0;
                    x < NORMALIZED_SIZE;
                    x++) {

                if (pixels[y][x]) {

                    distance[y][x] = 0;

                } else {

                    distance[y][x] = INF;
                }
            }
        }

        // Forward pass
        for (int y = 0;
                y < NORMALIZED_SIZE;
                y++) {

            for (int x = 0;
                    x < NORMALIZED_SIZE;
                    x++) {

                if (x > 0) {

                    distance[y][x] =
                            Math.min(
                                    distance[y][x],
                                    distance[y][x - 1]
                                    + 1
                            );
                }

                if (y > 0) {

                    distance[y][x] =
                            Math.min(
                                    distance[y][x],
                                    distance[y - 1][x]
                                    + 1
                            );
                }
            }
        }

        // Backward pass
        for (int y =
                NORMALIZED_SIZE - 1;
                y >= 0;
                y--) {

            for (int x =
                    NORMALIZED_SIZE - 1;
                    x >= 0;
                    x--) {

                if (
                    x
                    < NORMALIZED_SIZE - 1
                ) {

                    distance[y][x] =
                            Math.min(
                                    distance[y][x],
                                    distance[y][x + 1]
                                    + 1
                            );
                }

                if (
                    y
                    < NORMALIZED_SIZE - 1
                ) {

                    distance[y][x] =
                            Math.min(
                                    distance[y][x],
                                    distance[y + 1][x]
                                    + 1
                            );
                }
            }
        }

        return distance;
    }

    // ============================================================
    // AVERAGE DISTANCE
    // ============================================================

    private double averageDistance(
            boolean[][] points,
            int[][] distanceMap
    ) {

        double total = 0;

        int count = 0;

        for (int y = 0;
                y < NORMALIZED_SIZE;
                y++) {

            for (int x = 0;
                    x < NORMALIZED_SIZE;
                    x++) {

                if (points[y][x]) {

                    total +=
                            distanceMap[y][x];

                    count++;
                }
            }
        }

        if (count == 0) {

            return NORMALIZED_SIZE;
        }

        return total / count;
    }

    // ============================================================
    // PROJECTION SCORE
    // ============================================================

    private double calculateProjectionScore(
            boolean[][] user,
            boolean[][] template,
            int userCount,
            int templateCount
    ) {

        double[] userRows =
                new double[
                        NORMALIZED_SIZE
                ];

        double[] templateRows =
                new double[
                        NORMALIZED_SIZE
                ];

        double[] userColumns =
                new double[
                        NORMALIZED_SIZE
                ];

        double[] templateColumns =
                new double[
                        NORMALIZED_SIZE
                ];

        for (int y = 0;
                y < NORMALIZED_SIZE;
                y++) {

            for (int x = 0;
                    x < NORMALIZED_SIZE;
                    x++) {

                if (user[y][x]) {

                    userRows[y]++;
                    userColumns[x]++;
                }

                if (template[y][x]) {

                    templateRows[y]++;
                    templateColumns[x]++;
                }
            }
        }

        // Normalize
        for (int i = 0;
                i < NORMALIZED_SIZE;
                i++) {

            userRows[i] /=
                    userCount;

            templateRows[i] /=
                    templateCount;

            userColumns[i] /=
                    userCount;

            templateColumns[i] /=
                    templateCount;
        }

        double rowDifference = 0;

        double columnDifference = 0;

        for (int i = 0;
                i < NORMALIZED_SIZE;
                i++) {

            rowDifference +=
                    Math.abs(
                            userRows[i]
                            - templateRows[i]
                    );

            columnDifference +=
                    Math.abs(
                            userColumns[i]
                            - templateColumns[i]
                    );
        }

        rowDifference /= 2.0;

        columnDifference /= 2.0;

        double difference =
                (
                    rowDifference
                    + columnDifference
                ) / 2.0;

        double score =
                1.0 - difference;

        if (score < 0) {
            score = 0;
        }

        if (score > 1) {
            score = 1;
        }

        return score;
    }

    // ============================================================
    // SHOW PAGE
    // ============================================================

    private void showPage(
            String page
    ) {

        cardLayout.show(
                mainPanel,
                page
        );
    }

    // ============================================================
    // DRAWING CANVAS
    // ============================================================

    private class DrawingCanvas
            extends JPanel
            implements MouseListener,
                       MouseMotionListener {

        private BufferedImage canvas;

        private int lastX;
        private int lastY;

        private boolean drawing = false;

        public DrawingCanvas() {

            setBackground(
                    Color.WHITE
            );

            setBorder(
                    BorderFactory.createLineBorder(
                            Color.BLACK,
                            2
                    )
            );

            addMouseListener(this);

            addMouseMotionListener(this);
        }

        // --------------------------------------------------------
        // INITIALIZE CANVAS
        // --------------------------------------------------------

        private void initializeCanvas() {

            int width =
                    getWidth();

            int height =
                    getHeight();

            if (width <= 0) {
                width = 700;
            }

            if (height <= 0) {
                height = 350;
            }

            if (
                canvas == null
                || canvas.getWidth() != width
                || canvas.getHeight() != height
            ) {

                canvas =
                        new BufferedImage(
                                width,
                                height,
                                BufferedImage.TYPE_INT_RGB
                        );

                Graphics2D g =
                        canvas.createGraphics();

                g.setColor(
                        Color.WHITE
                );

                g.fillRect(
                        0,
                        0,
                        width,
                        height
                );

                g.dispose();
            }
        }

        // --------------------------------------------------------
        // PAINT
        // --------------------------------------------------------

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            initializeCanvas();

            g.drawImage(
                    canvas,
                    0,
                    0,
                    null
            );
        }

        // --------------------------------------------------------
        // CLEAR CANVAS
        // --------------------------------------------------------

        public void clearCanvas() {

            initializeCanvas();

            Graphics2D g =
                    canvas.createGraphics();

            g.setColor(
                    Color.WHITE
            );

            g.fillRect(
                    0,
                    0,
                    canvas.getWidth(),
                    canvas.getHeight()
            );

            g.dispose();

            repaint();
        }

        // --------------------------------------------------------
        // HAS DRAWING
        // --------------------------------------------------------

        public boolean hasDrawing() {

            initializeCanvas();

            for (int y = 0;
                    y < canvas.getHeight();
                    y++) {

                for (int x = 0;
                        x < canvas.getWidth();
                        x++) {

                    if (
                        isBlack(
                                canvas.getRGB(
                                        x,
                                        y
                                )
                        )
                    ) {

                        return true;
                    }
                }
            }

            return false;
        }

        // --------------------------------------------------------
        // GET CANVAS IMAGE
        // --------------------------------------------------------

        public BufferedImage getCanvasImage() {

            initializeCanvas();

            BufferedImage copy =
                    new BufferedImage(
                            canvas.getWidth(),
                            canvas.getHeight(),
                            BufferedImage.TYPE_INT_RGB
                    );

            Graphics2D g =
                    copy.createGraphics();

            g.drawImage(
                    canvas,
                    0,
                    0,
                    null
            );

            g.dispose();

            return copy;
        }

        // --------------------------------------------------------
        // MOUSE PRESSED
        // --------------------------------------------------------

        @Override
        public void mousePressed(
                MouseEvent e
        ) {

            if (timeLeft <= 0) {
                return;
            }

            initializeCanvas();

            drawing = true;

            lastX = e.getX();

            lastY = e.getY();

            Graphics2D g =
                    canvas.createGraphics();

            g.setColor(
                    Color.BLACK
            );

            g.setStroke(
                    new BasicStroke(
                            7,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            /*
             * Small dot at starting point.
             */
            g.fillOval(
                    lastX - 3,
                    lastY - 3,
                    7,
                    7
            );

            g.dispose();

            repaint();
        }

        // --------------------------------------------------------
        // MOUSE DRAGGED
        // --------------------------------------------------------

        @Override
        public void mouseDragged(
                MouseEvent e
        ) {

            if (
                !drawing
                || timeLeft <= 0
            ) {

                return;
            }

            initializeCanvas();

            int currentX =
                    e.getX();

            int currentY =
                    e.getY();

            Graphics2D g =
                    canvas.createGraphics();

            g.setColor(
                    Color.BLACK
            );

            g.setStroke(
                    new BasicStroke(
                            7,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            g.drawLine(
                    lastX,
                    lastY,
                    currentX,
                    currentY
            );

            g.dispose();

            lastX = currentX;

            lastY = currentY;

            repaint();
        }

        // --------------------------------------------------------
        // MOUSE RELEASED
        // --------------------------------------------------------

        @Override
        public void mouseReleased(
                MouseEvent e
        ) {

            drawing = false;
        }

        // --------------------------------------------------------
        // UNUSED MOUSE METHODS
        // --------------------------------------------------------

        @Override
        public void mouseClicked(
                MouseEvent e
        ) {
        }

        @Override
        public void mouseEntered(
                MouseEvent e
        ) {
        }

        @Override
        public void mouseExited(
                MouseEvent e
        ) {
        }

        @Override
        public void mouseMoved(
                MouseEvent e
        ) {
        }
    }

    // ============================================================
    // MAIN METHOD
    // ============================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                new Runnable() {

                    @Override
                    public void run() {

                        GamePage game =
                                new GamePage();

                        game.setVisible(true);
                    }
                }
        );
    }
}