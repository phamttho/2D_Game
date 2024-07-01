package Background;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Personnage.Character;

public class GamePanel extends JPanel {
    private final Map map;
    private final GameMenu gameMenu;
    private final Character character;
    private final Image characterImage;
    private final Image heartImage;
    private Timer enemyMoveTimer;
    private boolean lastAnswerCorrect = true; // Flag to track if the last answer was correct
    private int lastDirection = -1; // Variable to store the last movement direction
    private int remainingHearts = 5;

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

    // List to hold all questions
    private final List<Question> questions;

    public GamePanel(Map map, Character character) {
        this.map = map;
        this.character = character;
        this.characterImage = new ImageIcon("src/carrot.png").getImage(); // Update the path to your image
        this.heartImage = new ImageIcon("src/heart.png").getImage(); // Load the heart image
        this.gameMenu = new GameMenu("src/vetgetwar.PNG");
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

                if (character.checkCollision(map)) {
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
                // Game over logic here (e.g., reset the game or end it)
                JOptionPane.showMessageDialog(this, "Game over.", "Enemy", JOptionPane.INFORMATION_MESSAGE);
                gameMenu.showMenu();
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(GamePanel.this);
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
    private List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Who developed the theory of general relativity?", new String[]{"A. Isaac Newton", "B. Albert Einstein", "C. Niels Bohr", "D. Galileo Galilei"}, 1));
        questions.add(new Question("What is the most abundant gas in the Earth's atmosphere?", new String[]{"A. Oxygen", "B. Nitrogen", "C. Carbon Dioxide", "D. Hydrogen"}, 1));
        questions.add(new Question("What is the primary component of the sun?", new String[]{"A. Oxygen", "B. Nitrogen", "C. Helium", "D. Hydrogen"}, 3));
        questions.add(new Question("What is the smallest unit of life?", new String[]{"A. Atom", "B. Molecule", "C. Cell", "D. Organ"}, 2));
        questions.add(new Question("What is the speed of light?", new String[]{"A. 300,000 km/s", "B. 150,000 km/s", "C. 450,000 km/s", "D. 600,000 km/s"}, 0));
        questions.add(new Question("Who is known as the father of computer science?", new String[]{"A. Alan Turing", "B. Charles Babbage", "C. Bill Gates", "D. Steve Jobs"}, 0));
        questions.add(new Question("What is the powerhouse of the cell?", new String[]{"A. Nucleus", "B. Ribosome", "C. Mitochondria", "D. Endoplasmic Reticulum"}, 2));
        questions.add(new Question("What is the chemical symbol for gold?", new String[]{"A. Au", "B. Ag", "C. Gd", "D. Go"}, 0));
        questions.add(new Question("Who proposed the laws of motion?", new String[]{"A. Albert Einstein", "B. Isaac Newton", "C. Galileo Galilei", "D. James Clerk Maxwell"}, 1));
        questions.add(new Question("What is the largest planet in our solar system?", new String[]{"A. Earth", "B. Mars", "C. Jupiter", "D. Saturn"}, 2));
        questions.add(new Question("What is the boiling point of water at sea level?", new String[]{"A. 90°C", "B. 100°C", "C. 110°C", "D. 120°C"}, 1));
        questions.add(new Question("What does DNA stand for?", new String[]{"A. Deoxyribonucleic Acid", "B. Deoxyribogenetic Acid", "C. Deoxyribonitrogen Acid", "D. Deoxyribosugar Acid"}, 0));
        questions.add(new Question("What is the chemical formula for table salt?", new String[]{"A. NaCl", "B. KCl", "C. NaF", "D. KBr"}, 0));
        questions.add(new Question("Who invented the World Wide Web?", new String[]{"A. Bill Gates", "B. Steve Jobs", "C. Tim Berners-Lee", "D. Mark Zuckerberg"}, 2));
        questions.add(new Question("What is the primary function of red blood cells?", new String[]{"A. Fight infections", "B. Transport oxygen", "C. Clot blood", "D. Produce antibodies"}, 1));
        questions.add(new Question("What is the first element on the periodic table?", new String[]{"A. Helium", "B. Oxygen", "C. Lithium", "D. Hydrogen"}, 3));
        questions.add(new Question("What is the unit of electrical resistance?", new String[]{"A. Volt", "B. Ampere", "C. Ohm", "D. Watt"}, 2));
        questions.add(new Question("What is the main ingredient in traditional computer chips?", new String[]{"A. Carbon", "B. Silicon", "C. Aluminum", "D. Iron"}, 1));
        questions.add(new Question("What is the term for animals that can live both on land and in water?", new String[]{"A. Mammals", "B. Amphibians", "C. Reptiles", "D. Birds"}, 1));
        questions.add(new Question("Who discovered penicillin?", new String[]{"A. Marie Curie", "B. Alexander Fleming", "C. Louis Pasteur", "D. Joseph Lister"}, 1));
        questions.add(new Question("What is the strongest known material in the universe?", new String[]{"A. Diamond", "B. Graphene", "C. Steel", "D. Titanium"}, 1));
        questions.add(new Question("What planet is known as the Red Planet?", new String[]{"A. Venus", "B. Mars", "C. Jupiter", "D. Saturn"}, 1));
        questions.add(new Question("What type of bond involves the sharing of electron pairs between atoms?", new String[]{"A. Ionic bond", "B. Covalent bond", "C. Metallic bond", "D. Hydrogen bond"}, 1));
        questions.add(new Question("Who developed the first successful polio vaccine?", new String[]{"A. Louis Pasteur", "B. Jonas Salk", "C. Alexander Fleming", "D. Edward Jenner"}, 1));
        questions.add(new Question("What is the center of an atom called?", new String[]{"A. Electron", "B. Proton", "C. Neutron", "D. Nucleus"}, 3));
        questions.add(new Question("What gas do plants absorb from the atmosphere?", new String[]{"A. Oxygen", "B. Nitrogen", "C. Carbon Dioxide", "D. Hydrogen"}, 2));
        questions.add(new Question("What is the largest organ in the human body?", new String[]{"A. Liver", "B. Skin", "C. Heart", "D. Lungs"}, 1));
        questions.add(new Question("What programming language is primarily used for Android app development?", new String[]{"A. Swift", "B. Java", "C. Python", "D. C#"}, 1));
        questions.add(new Question("What particle carries a negative charge?", new String[]{"A. Proton", "B. Neutron", "C. Electron", "D. Photon"}, 2));
        questions.add(new Question("What is the study of mushrooms called?", new String[]{"A. Botany", "B. Mycology", "C. Zoology", "D. Ecology"}, 1));
        questions.add(new Question("What does HTTP stand for?", new String[]{"A. HyperText Transfer Protocol", "B. HyperText Transmission Protocol", "C. HyperText Transfer Program", "D. HyperText Transmission Program"}, 0));
        questions.add(new Question("What is the powerhouse of the cell?", new String[]{"A. Nucleus", "B. Ribosome", "C. Mitochondria", "D. Endoplasmic Reticulum"}, 2));
        questions.add(new Question("What is the chemical symbol for potassium?", new String[]{"A. P", "B. K", "C. Po", "D. Pt"}, 1));
        questions.add(new Question("What planet is known for its rings?", new String[]{"A. Jupiter", "B. Uranus", "C. Saturn", "D. Neptune"}, 2));
        questions.add(new Question("What is the name of the first artificial Earth satellite?", new String[]{"A. Apollo 11", "B. Voyager", "C. Sputnik 1", "D. Hubble"}, 2));
        questions.add(new Question("What is the most common type of star in the Milky Way?", new String[]{"A. Red dwarf", "B. White dwarf", "C. Neutron star", "D. Yellow dwarf"}, 0));
        questions.add(new Question("What does CPU stand for?", new String[]{"A. Central Processing Unit", "B. Central Programming Unit", "C. Central Performance Unit", "D. Central Power Unit"}, 0));
        questions.add(new Question("What is the hardest natural substance on Earth?", new String[]{"A. Gold", "B. Iron", "C. Diamond", "D. Platinum"}, 2));
        questions.add(new Question("What is the name of the largest moon of Saturn?", new String[]{"A. Europa", "B. Ganymede", "C. Titan", "D. Callisto"}, 2));
        questions.add(new Question("What is the study of plants called?", new String[]{"A. Zoology", "B. Botany", "C. Ecology", "D. Mycology"}, 1));
        questions.add(new Question("What is the chemical formula for water?", new String[]{"A. H2O", "B. H2O2", "C. HO", "D. H2O3"}, 0));
        questions.add(new Question("What programming language is known for its use in web development?", new String[]{"A. Java", "B. C++", "C. JavaScript", "D. Python"}, 2));
        questions.add(new Question("What is the most abundant element in the universe?", new String[]{"A. Oxygen", "B. Carbon", "C. Hydrogen", "D. Nitrogen"}, 2));
        questions.add(new Question("What is the study of the interactions between organisms and their environment called?", new String[]{"A. Biology", "B. Ecology", "C. Geology", "D. Chemistry"}, 1));
        questions.add(new Question("Who is known as the father of genetics?", new String[]{"A. Charles Darwin", "B. Gregor Mendel", "C. James Watson", "D. Francis Crick"}, 1));
        questions.add(new Question("What is the closest star to Earth?", new String[]{"A. Alpha Centauri", "B. Proxima Centauri", "C. Betelgeuse", "D. The Sun"}, 3));
        questions.add(new Question("What is the chemical symbol for mercury?", new String[]{"A. Me", "B. Mg", "C. Hg", "D. Mr"}, 2));

        return questions;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.draw(g);

        // Draw the main character image
        int tileSize = map.getTileSize();
        g.drawImage(characterImage, character.getX() * tileSize, character.getY() * tileSize, tileSize, tileSize, this);

        // Draw the hearts
        for (int i = 0; i < remainingHearts; i++) {
            g.drawImage(heartImage, i * 20, 0, 20, 20, this); // Draw hearts with a small gap between them
        }
    }
}
