package Personnage;

import Background.Map;
public class Character {
    private String name;
    private int health;
    private int power;
    private int x;
    private int y;
    private int mapWidth;
    private int mapHeight;
    private Map map;

    public Character(String name, int mapWidth, int mapHeight, Map map) {
        this.name = name;
        this.health = 100; // Example health
        this.power = 10;   // Example power
        this.x = 1;        // Initial x position (inside the border)
        this.y = 1;        // Initial y position (inside the border)
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveUp() {
        if (y > 0 && map.isWalkable(x, y - 1)) {
            y--;
        }
    }

    public void moveDown() {
        if (y < mapHeight - 1 && map.isWalkable(x, y + 1)) {
            y++;
        }
    }

    public void moveLeft() {
        if (x > 0 && map.isWalkable(x - 1, y)) {
            x--;
        }
    }

    public void moveRight() {
        if (x < mapWidth - 1 && map.isWalkable(x + 1, y)) {
            x++;
        }
    }

    public boolean isAtDoor() {
        return map.isDoor(x, y);
    }

    // Example actions
    public void hit() {
        System.out.println(name + " hits!");
    }

    public void kick() {
        System.out.println(name + " kicks!");
    }

    public void specialPower() {
        System.out.println(name + " uses special power!");
    }
}
