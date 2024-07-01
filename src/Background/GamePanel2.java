package Background;

import Personnage.Character;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel2 extends JPanel {
    private final Map2 map;
    private final GameMenu gameMenu;
    private final Character character;
    private final Image characterImage;
    private final Image heartImage;
    private Timer enemyMoveTimer;
    private boolean lastAnswerCorrect = true; // Flag to track if the last answer was correct
    private int lastDirection = -1; // Variable to store the last movement direction
    private int remainingHearts = 3;

    // Directions constants
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    // Class to hold questions and answers
    private static class Question {
        String question;
        String[] options;
        int correctAnswer;

        Question(String question, String[] options, int correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }
    private final java.util.List<Question> questions;

    public GamePanel2(Map2 map, Character character, String characterName) {
        this.map = map;
        this.character = character;
        this.characterImage = new ImageIcon(String.format("src/%s.png",characterName)).getImage(); // Update the path to your image
        this.gameMenu = new GameMenu("src/vetgetwar.PNG");
        this.heartImage = new ImageIcon("src/heart.png").getImage(); // Load the heart image
        this.questions = loadQuestions();
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int oldX = character.getX();
                int oldY = character.getY();

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        character.moveUp();
                        lastDirection = UP;
                        playSound("src/Sound/sound_1.wav");
                        break;
                    case KeyEvent.VK_DOWN:
                        character.moveDown();
                        lastDirection = DOWN;
                        playSound("src/Sound/sound_1.wav");
                        break;
                    case KeyEvent.VK_LEFT:
                        character.moveLeft();
                        lastDirection = LEFT;
                        playSound("src/Sound/sound_1.wav");
                        break;
                    case KeyEvent.VK_RIGHT:
                        character.moveRight();
                        lastDirection = RIGHT;
                        playSound("src/Sound/sound_1.wav");
                        break;
                }


                if (character.checkCollision2(map)) {
                    character.setX(oldX);
                    character.setY(oldY);
                    showEnemyQuestion();
                }
                repaint();
            }
        });
    }
    public void setTimer(Timer timer) {
        this.enemyMoveTimer = timer;
    }

    private void showEnemyQuestion() {
        // Stop enemy movement timer
        enemyMoveTimer.stop();

        // Show initial message
        JOptionPane.showMessageDialog(this, "Haha, let's see how good you are, answer my question if you want to pass.", "Enemy", JOptionPane.INFORMATION_MESSAGE);

        // Randomly select a question
        Random random = new Random();
        Question selectedQuestion = questions.get(random.nextInt(questions.size()));

        // Show the question
        int response = JOptionPane.showOptionDialog(this, selectedQuestion.question, "Enemy Question",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, selectedQuestion.options, selectedQuestion.options[0]);

        if (response == selectedQuestion.correctAnswer) {
            JOptionPane.showMessageDialog(this, "Correct! You may pass.", "Enemy", JOptionPane.INFORMATION_MESSAGE);
            lastAnswerCorrect = true; // Set the flag to true if the answer is correct
            playSound("src/Sound/correct-83487.wav");
            moveCharacterTwoStepsForward();
        } else {
            JOptionPane.showMessageDialog(this, "Wrong! You shall not pass.", "Enemy", JOptionPane.INFORMATION_MESSAGE);
            lastAnswerCorrect = false; // Set the flag to false if the answer is incorrect
            playSound("src/Sound/wrong-buzzer-6268.wav");
            character.setX(1);
            character.setY(1);
            remainingHearts--; // Decrease the number of remaining hearts
            if (remainingHearts <= 0) {
                JOptionPane.showMessageDialog(this, "Game over.", "Enemy", JOptionPane.INFORMATION_MESSAGE);
                gameMenu.showMenu();
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(GamePanel2.this);
                topFrame.dispose();
            }
        }

        // Resume enemy movement timer
        enemyMoveTimer.start();
    }

    private void moveCharacterTwoStepsForward() {
        switch (lastDirection) {
            case UP:
                character.setY(character.getY() - 2);
                break;
            case DOWN:
                character.setY(character.getY() + 2);
                break;
            case LEFT:
                character.setX(character.getX() - 2);
                break;
            case RIGHT:
                character.setX(character.getX() + 2);
                break;
        }

        // Ensure the character doesn't move out of bounds or into an unwalkable area
        if (character.getX() < 0 || character.getX() >= map.getWidth() || character.getY() < 0 || character.getY() >= map.getHeight() || !map.isWalkable(character.getX(), character.getY())) {
            // Revert to previous position if out of bounds or unwalkable
            character.setX(character.getX() + (lastDirection == LEFT ? 2 : lastDirection == RIGHT ? -2 : 0));
            character.setY(character.getY() + (lastDirection == UP ? 2 : lastDirection == DOWN ? -2 : 0));
        }
    }
    // Method to play sound effects
    private void playSound(String soundFile) {
        try {
            File file = new File(soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    // Load questions into the list
    private java.util.List<Question> loadQuestions() {
        java.util.List<Question> questions = new ArrayList<>();
        questions.add(new Question("Who developed the theory of general relativity?", new String[]{"A. Isaac Newton", "B. Albert Einstein", "C. Niels Bohr", "D. Galileo Galilei"}, 1));
        questions.add(new Question("What is the most abundant gas in the Earth's atmosphere?", new String[]{"A. Oxygen", "B. Nitrogen", "C. Carbon Dioxide", "D. Hydrogen"}, 1));
        questions.add(new Question("What is the primary component of the sun?", new String[]{"A. Oxygen", "B. Nitrogen", "C. Helium", "D. Hydrogen"}, 3));
        questions.add(new Question("What is the study of the interactions between organisms and their environment called?", new String[]{"A. Biology", "B. Ecology", "C. Geology", "D. Chemistry"}, 1));
        questions.add(new Question("Who is known as the father of genetics?", new String[]{"A. Charles Darwin", "B. Gregor Mendel", "C. James Watson", "D. Francis Crick"}, 1));
        questions.add(new Question("What is the closest star to Earth?", new String[]{"A. Alpha Centauri", "B. Proxima Centauri", "C. Betelgeuse", "D. The Sun"}, 3));
        questions.add(new Question("What is the chemical symbol for mercury?", new String[]{"A. Me", "B. Mg", "C. Hg", "D. Mr"}, 2));

        return questions;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.draw2(g);

        // Draw the main character image
        int tileSize = map.getTileSize();
        g.drawImage(characterImage, character.getX() * tileSize, character.getY() * tileSize, tileSize, tileSize, this);

        // Draw the hearts
        for (int i = 0; i < remainingHearts; i++) {
            g.drawImage(heartImage, i * 20, 0, 20, 20, this); // Draw hearts with a small gap between them
        }
    }
}
