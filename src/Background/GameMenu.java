package Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JFrame {
    private final Image backgroundImage;

    private String currentCharacter = "carrot";

    public GameMenu() {
        // Load background image
        this.backgroundImage = new ImageIcon("src/vetgetwar.PNG").getImage();

        // Set up the frame
        setTitle("2DGame");
        setSize(800, 400);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a custom panel to paint the background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null); // Use absolute positioning

        // Create buttons

        JButton startButton = new JButton("Start");
        JButton playAsBanana = new JButton("Play as Banana");
        JButton playAsStrawberry = new JButton("Play as Strawberry");
        JButton playAsRaspberry = new JButton("Play as Raspberry ");

        // Set button bounds (x, y, width, height)
        int buttonWidth = 200;
        int buttonHeight = 30;
        int centerX = (Toolkit.getDefaultToolkit().getScreenSize().width - buttonWidth) / 2;
        int centerY = (Toolkit.getDefaultToolkit().getScreenSize().height) / 2;

        startButton.setBounds(centerX, centerY - 100, buttonWidth, buttonHeight);
        playAsBanana.setBounds(centerX, centerY - 40, buttonWidth, buttonHeight);
        playAsRaspberry.setBounds(centerX, centerY + 20, buttonWidth, buttonHeight);
        playAsStrawberry.setBounds(centerX, centerY + 80, buttonWidth, buttonHeight);

        // Add buttons to the panel
        backgroundPanel.add(startButton);
        backgroundPanel.add(playAsBanana);
        backgroundPanel.add(playAsRaspberry);
        backgroundPanel.add(playAsStrawberry);

        // Set up button actions (you can customize these as needed)

        startButton.addActionListener(this::actionPerformed);

        playAsBanana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    currentCharacter = "banana";
                JOptionPane.showMessageDialog(null, "Character changed to: " + currentCharacter);
            }
        });

        playAsRaspberry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCharacter = "raspberry";
                JOptionPane.showMessageDialog(null, "Character changed to: " + currentCharacter);
            }
        });
        playAsStrawberry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCharacter = "strawberry";
                JOptionPane.showMessageDialog(null, "Character changed to: " + currentCharacter);
            }
        });

        // Add the panel to the frame
        setContentPane(backgroundPanel);
    }

    public static void main(String[] args) {
        // Specify the full path to your image file
        String imagePath = "src/vetgetwar.PNG"; // Change this to your image path
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameMenu().setVisible(true);
            }
        });
    }

    public void showMenu() {
        // Show the game menu
        setVisible(true);
    }

    private void actionPerformed(ActionEvent e) {
        // Start the battle game
        new BattleGame(currentCharacter).setVisible(true);
        dispose(); // Close the menu window
    }
}
