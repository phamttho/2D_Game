package Personnage;

import Background.Map;
import Background.Map2;
public class Character {
    private String name;
    private int health;
    private int power;
    private int x;
    private int y;
    private int prevX, prevY;
    private int mapWidth;
    private int mapHeight;
    private Object map; // Use Object to accept any map type

    public Character(String name, int mapWidth, int mapHeight, Object map) {
        this.name = name;
        this.health = 100; // Example health
        this.power = 10;   // Example power
        this.x = 1;        // Initial x position (inside the border)
        this.y = 1;        // Initial y position (inside the border)
        this.prevX = 1;
        this.prevY = 1;
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
        if (y > 0 && isWalkable(x, y - 1)) {
            y--;
        }
    }

    public void moveDown() {
        if (y < mapHeight - 1 && isWalkable(x, y + 1)) {
            y++;
        }
    }

    public void moveLeft() {
        if (x > 0 && isWalkable(x - 1, y)) {
            x--;
        }
    }

    public void moveRight() {
        if (x < mapWidth - 1 && isWalkable(x + 1, y)) {
            x++;
        }
    }

    private boolean isWalkable(int x, int y) {
        if (map instanceof Map) {
            return ((Map) map).isWalkable(x, y);
        } else if (map instanceof Map2) {
            return ((Map2) map).isWalkable(x, y);
        }
        return false;
    }

    public boolean isAtDoor() {
        if (map instanceof Map) {
            return ((Map) map).isDoor(x, y);
        } else if (map instanceof Map2) {
            return ((Map2) map).isDoor(x, y);
        }
        return false;
    }

    public boolean isTheWinner() {
        if (map instanceof Map) {
            return ((Map) map).isWinner(x, y);
        } else if (map instanceof Map2) {
            return ((Map2) map).isWinner(x, y);
        }
        return false;
    }

    public boolean isAtObject() {
        if (map instanceof Map) {
            return ((Map) map).isObject(x, y);
        } else if (map instanceof Map2) {
            return ((Map2) map).isObject(x, y);
        }
        return false;
    }
    // Example actions
    public void hit(Character enemy) {
        enemy.setHealth(enemy.getHealth() - this.power);
        System.out.println(name + " hits " + enemy.getName() + "!");
    }

    public void kick(Character enemy) {
        enemy.setHealth(enemy.getHealth() - this.power * 2);
        System.out.println(name + " kicks " + enemy.getName() + "!");
    }

    public void specialPower(Character enemy) {
        enemy.setHealth(enemy.getHealth() - this.power * 3);
        System.out.println(name + " uses special power on " + enemy.getName() + "!");
    }

}
