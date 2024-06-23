package Background;

import Personnage.Character;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Map2 implements ImageObserver {
    private int width;
    private int height;
    private int tileSize;
    private Character mainCharacter;
    private int[][] map2; // 0: empty, 1: block, 2: door

    private final Image ImgStrawbery;
    private final Image BackgroundMap1;
    private final Image Bricks;

    public Map2(int[][] predefinedMap ,Character mainCharacter, int tileSize){
        this.width = predefinedMap.length;
        this.height = predefinedMap[0].length;
        this.tileSize = tileSize;
        this.map2 = predefinedMap;
        this.mainCharacter = mainCharacter;
        this.ImgStrawbery = new ImageIcon("src/cucumber.png").getImage(); // Update the path to your image
        this.BackgroundMap1 = new ImageIcon("src/grass.png").getImage();
        this.Bricks = new ImageIcon("src/bricks.png").getImage();
//        predefinedMap = new int[width][height];
    }

    public int getTileSize() {
        return tileSize;

    }

    public int[][] getMap() {
        return map2;}

    public Character getMainCharacter() {
        return mainCharacter;
    }

    public boolean isWalkable(int x, int y) {
        return map2[x][y] != 1;
    }

    public boolean isDoor(int x, int y) {
        return map2[x][y] == 2;
    }

    public boolean isObject(int x, int y) {
        return map2[x][y] != 3;}
    public boolean isWinner(int x, int y) {
        return map2[x][y] == 4;
    }

    public void draw2(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map2[i][j] == 1) {
                    g.drawImage(Bricks, i * tileSize, j * tileSize, tileSize, tileSize, this);
//                    g.setColor(Color.BLUE);
//                    g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                } else if (map2[i][j] == 2) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                }  else if (map2[i][j] == 3) {
                    g.drawImage(ImgStrawbery, i * tileSize, j * tileSize, tileSize, tileSize, this);
                }else if (map2[i][j] == 4) {
                    g.setColor(Color.RED);
                    g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                }else if (map2[i][j] == 0) {
                    g.drawImage(BackgroundMap1, i* tileSize, j * tileSize, tileSize, tileSize, this);
                }else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                }
            }
        }
    }



    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return true;
    }

    public int getWidth() {
        return width;  // Return the width of the map
    }

    public int getHeight() {
        return height;  // Return the height of the map
    }
}