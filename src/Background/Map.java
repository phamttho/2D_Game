package Background;

import Personnage.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Map implements ImageObserver {
    private int width;
    private int height;
    private int tileSize;
    private Character mainCharacter;
    private int[][] map; // 0: empty, 1: block, 2: door


    private final Image ImgStrawbery;
    private final Image BackgroundMap1;
    private final Image Bricks;
    private final Image Door1;

    public Map(int[][] predefinedMap ,Character mainCharacter, int tileSize) {
        this.width = predefinedMap.length;
        this.height = predefinedMap[0].length;
        this.tileSize = tileSize;
        this.map = predefinedMap;
        this.mainCharacter = mainCharacter;
        this.ImgStrawbery = new ImageIcon("src/strawbery.png").getImage(); // Update the path to your image
        this.BackgroundMap1 = new ImageIcon("src/MapBackground.png").getImage();
        this.Bricks = new ImageIcon("src/bricks.png").getImage();
        this.Door1 = new ImageIcon("src/door1.png").getImage();
    }


    public int getTileSize() {
        return tileSize;
    }

    public int[][] getMap() {
        return map;
    }

    public boolean isWalkable(int x, int y) {
        return map[x][y] != 1;
    }
    public boolean isWalkableEnemy(int x, int y) {
        return map[x][y] != 1 && map[x][y] != 2 && map[x][y] != 3 ;
    }

    public boolean isDoor(int x, int y) {
        return map[x][y] == 2;
    }


    public boolean isWinner(int x, int y) {
        return map[x][y] == 4;
    }



    public void draw(Graphics g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] == 1) {
                    g.drawImage(Bricks, i * tileSize, j * tileSize, tileSize, tileSize, this);
//                    g.setColor(Color.BLUE);
//                    g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                } else if (map[i][j] == 2) {
                    g.drawImage(Door1, i * tileSize, j * tileSize, tileSize, tileSize, this);
                } else if (map[i][j] == 3) {
                    g.drawImage(ImgStrawbery, i * tileSize, j * tileSize, tileSize, tileSize, this);
                } else if (map[i][j] == 4) {
                    g.setColor(Color.RED);
                    g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
                }else if (map[i][j] == 0) {
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