package Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Personnage.Character;
import Personnage.Enemy1;

public class GamePanel extends JPanel {
    private Map map;
    private Character character;
    private Image characterImage;



    public GamePanel(Map map, Character character) {
        this.map = map;
        this.character = character;
        this.characterImage = new ImageIcon("src/carrot.png").getImage(); // Update the path to your image


        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
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
                repaint();
            }
        });
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