package Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JFrame {
    private final Image backgroundImage;

    public GameMenu(String imagePath) {
        // Load background image
        backgroundImage = new ImageIcon(imagePath).getImage();

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
        JButton introductionButton = new JButton("Introduction");
        JButton howToPlayButton = new JButton("How to Play");
        JButton startButton = new JButton("Start");
        JButton settingsButton = new JButton("Settings");

        // Set button bounds (x, y, width, height)
        int buttonWidth = 200;
        int buttonHeight = 30;
        int centerX = (Toolkit.getDefaultToolkit().getScreenSize().width - buttonWidth) / 2;
        int centerY = (Toolkit.getDefaultToolkit().getScreenSize().height) / 2;

        introductionButton.setBounds(centerX, centerY - 100, buttonWidth, buttonHeight);
        howToPlayButton.setBounds(centerX, centerY - 40, buttonWidth, buttonHeight);
        startButton.setBounds(centerX, centerY + 20, buttonWidth, buttonHeight);
        settingsButton.setBounds(centerX, centerY + 80, buttonWidth, buttonHeight);

        // Add buttons to the panel
        backgroundPanel.add(introductionButton);
        backgroundPanel.add(howToPlayButton);
        backgroundPanel.add(startButton);
        backgroundPanel.add(settingsButton);

        // Set up button actions (you can customize these as needed)
        introductionButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Introduction Page"));

        howToPlayButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "How to Play Page"));

        startButton.addActionListener(this::actionPerformed);

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Settings Page");
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
                new GameMenu(imagePath).setVisible(true);
            }
        });
    }

    public void showMenu() {
        // Show the game menu
        setVisible(true);
    }

    private void actionPerformed(ActionEvent e) {
        // Start the battle game
        new BattleGame().setVisible(true);
        dispose(); // Close the menu window
    }
}
