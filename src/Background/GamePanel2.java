package Background;

import Personnage.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel2 extends JPanel {
    private final Map2 map;
    private final Character character;
    private final Image characterImage;
    private Timer enemyMoveTimer;
    private boolean lastAnswerCorrect = true; // Flag to track if the last answer was correct
    private int lastDirection = -1; // Variable to store the last movement direction

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
    private final java.util.List<GamePanel2.Question> questions;

    public GamePanel2(Map2 map, Character character) {
        this.map = map;
        this.character = character;
        this.characterImage = new ImageIcon("src/carrot.png").getImage(); // Update the path to your image
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
                        break;
                    case KeyEvent.VK_DOWN:
                        character.moveDown();
                        lastDirection = DOWN;
                        break;
                    case KeyEvent.VK_LEFT:
                        character.moveLeft();
                        lastDirection = LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        character.moveRight();
                        lastDirection = RIGHT;
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
        GamePanel2.Question selectedQuestion = questions.get(random.nextInt(questions.size()));

        // Show the question
        int response = JOptionPane.showOptionDialog(this, selectedQuestion.question, "Enemy Question",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, selectedQuestion.options, selectedQuestion.options[0]);

        if (response == selectedQuestion.correctAnswer) {
            JOptionPane.showMessageDialog(this, "Correct! You may pass.", "Enemy", JOptionPane.INFORMATION_MESSAGE);
            lastAnswerCorrect = true; // Set the flag to true if the answer is correct
            moveCharacterTwoStepsForward();
        } else {
            JOptionPane.showMessageDialog(this, "Wrong! You shall not pass.", "Enemy", JOptionPane.INFORMATION_MESSAGE);
            lastAnswerCorrect = false; // Set the flag to false if the answer is incorrect
            character.setX(1);
            character.setY(1);
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

    // Load questions into the list
    private java.util.List<GamePanel2.Question> loadQuestions() {
        java.util.List<GamePanel2.Question> questions = new ArrayList<>();
        questions.add(new GamePanel2.Question("Who developed the theory of general relativity?", new String[]{"A. Isaac Newton", "B. Albert Einstein", "C. Niels Bohr", "D. Galileo Galilei"}, 1));
        questions.add(new GamePanel2.Question("What is the most abundant gas in the Earth's atmosphere?", new String[]{"A. Oxygen", "B. Nitrogen", "C. Carbon Dioxide", "D. Hydrogen"}, 1));
        questions.add(new GamePanel2.Question("What is the primary component of the sun?", new String[]{"A. Oxygen", "B. Nitrogen", "C. Helium", "D. Hydrogen"}, 3));
        questions.add(new GamePanel2.Question("What is the study of the interactions between organisms and their environment called?", new String[]{"A. Biology", "B. Ecology", "C. Geology", "D. Chemistry"}, 1));
        questions.add(new GamePanel2.Question("Who is known as the father of genetics?", new String[]{"A. Charles Darwin", "B. Gregor Mendel", "C. James Watson", "D. Francis Crick"}, 1));
        questions.add(new GamePanel2.Question("What is the closest star to Earth?", new String[]{"A. Alpha Centauri", "B. Proxima Centauri", "C. Betelgeuse", "D. The Sun"}, 3));
        questions.add(new GamePanel2.Question("What is the chemical symbol for mercury?", new String[]{"A. Me", "B. Mg", "C. Hg", "D. Mr"}, 2));

        return questions;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.draw2(g);

        // Draw the main character image
        int tileSize = map.getTileSize();
        g.drawImage(characterImage, character.getX() * tileSize, character.getY() * tileSize, tileSize, tileSize, this);
    }
}
