package Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Personnage.Character;


public class GamePanel extends JPanel {
    private Map map;
    private final Character character;
    private final Image characterImage;
    private Timer enemyMoveTime;



    public GamePanel(Map map, Character character,Timer enemyMoveTime) {
        this.map = map;
        this.character = character;
        this.characterImage = new ImageIcon("src/carrot.png").getImage(); // Update the path to your image
        this.enemyMoveTime = enemyMoveTime;
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int oldX = character.getX();
                int oldY = character.getY();


                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        character.moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                        character.moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        character.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        character.moveRight();
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

    private void showEnemyQuestion() {
        // Stop enemy movement timer
        enemyMoveTime.stop();

        // Show initial message
        JOptionPane.showMessageDialog(this, "Haha, let's see how good you are, answer my question if you want to pass.", "Enemy", JOptionPane.INFORMATION_MESSAGE);

        // Show the question
        String question = "Who came up with the theory of relativity?";
        String[] options = {"A. Isaac Newton", "B. Nikola Tesla", "C. Albert Einstein", "D. Galileo Galilei"};
        int correctAnswer = 2; // Index of the correct answer (C. Albert Einstein)

        int response = JOptionPane.showOptionDialog(this, question, "Enemy Question",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (response == correctAnswer) {
            JOptionPane.showMessageDialog(this, "Correct! You may pass.", "Enemy", JOptionPane.INFORMATION_MESSAGE);
            // Enemy lets the player pass, resume enemy movement
        } else {
            JOptionPane.showMessageDialog(this, "Wrong! You shall not pass.", "Enemy", JOptionPane.INFORMATION_MESSAGE);
            // Player stays at the same position
        }

        // Resume enemy movement timer
        enemyMoveTime.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.draw(g);

        // Draw the main character image
        int tileSize = map.getTileSize();
        g.drawImage(characterImage, character.getX() * tileSize, character.getY() * tileSize, tileSize, tileSize, this);
    }


}