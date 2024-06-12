package Personnage;

public class Enemy1 {
    private String name;
    private int health;
    private int power;

    public Enemy1(String name, int health, int power) {
        this.name = name;
        this.health = health;
        this.power = power;
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

    // Example actions
    public void attack(Character character) {
        System.out.println(name + " attacks " + character.getName() + "!");
        character.setHealth(character.getHealth() - power);
    }
}
