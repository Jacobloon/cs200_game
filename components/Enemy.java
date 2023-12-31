package components;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;

/**
 * This is the class to create enemy objects on the playing field
 * @author Jacob Odenthal
 * @version 12/10/2023
 */
public class Enemy {
    /**
     * Sprite of the enemy
     */
    private BufferedImage sprite;

    /**
     * X and Y location of the enemy
     */
    private int x;
    private int y;

    /**
     * Enemy hitbox size
     */
    private int width;
    private int height;

    /**
     * Speed of the enemy
     */
    private int speed;

    /**
     * Has the enemy been shot yet
     */
    private boolean alive;
    /**
     * How far away the enemy stays
     */
    private int bDist;

    private int buffer;

    private ArrayList<EnemyBullet> bullets;

    private int shotPause = 0;

    /**
     * Reference to the player
     */
    private Player player;

    /**
     * Constructor for the object
     */
    public Enemy(Player player) {
    	Random rand = new Random();
        this.x = 150 + rand.nextInt(200);
        this.y = 150 + rand.nextInt(200);
        this.width = 44;
        this.height = 64;
        this.speed = 3;
        this.bDist = 150;
        this.buffer = 20;
        this.alive = true;
        this.player = player;
        this.bullets = new ArrayList<EnemyBullet>();
        try {
            sprite = ImageIO.read(new File("sprites/meany.png"));
        }
        catch (IOException e) {
            System.out.println("Cannot load enemy sprite");
        }
    }

    /**
     * This draws the enemy on the screen, and is the method that gets called every tick
     * @param g Graphics panel
     */
    public void draw(Graphics g) {
        if (this.alive) {
            moveEnemy();
            shotPause++;
            g.setColor(Color.WHITE);
            g.drawImage(sprite, x, y, null);
        }
    }

    /**
     * Basic movement to follow player
     */
    public void moveEnemy() {
        int playerX = player.getX();
        int playerY = player.getY();
        // Regulates X movement
        if (playerX < this.x && this.x - playerX > bDist + buffer) { // Moves closer from the right
            this.x -= this.speed;
        }
        else if (playerX < this.x && this.x - playerX < bDist) { // Moves away to the right
            this.x += this.speed;
        }
        else if (playerX > this.x && playerX - this.x > bDist + buffer) { // Moves away to the left
            this.x += this.speed;
        }
        else if (playerX > this.x && playerX - this.x < bDist) { // Moves closer from the left
            this.x -= this.speed;
        }

        // Regulates Y movement
        if (playerY > this.y && playerY - this.y > buffer) { // Moves down
            this.y += this.speed;
        }
        else if (playerY < this.y){
            this.y -= this.speed;
        }
        else if (this.shotPause > 50) {
            if (playerX < this.x) {
                shootPlayer(0);
            }
            else {
                shootPlayer(2);
            }
        }
    }

    /**
     * Shoots in the given direction
     */
    public void shootPlayer(int direction) {
        EnemyBullet shot = new EnemyBullet(this, direction); 
        bullets.add(shot);
        this.shotPause = 0;
    }

    /**
     * 
     */
    public void getShot() {
        // TODO: Die upon being shot
        this.alive = false;
    }
    

    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return this.width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public ArrayList<EnemyBullet> getBullets () {
        return this.bullets;
    }
    public void setBullets(ArrayList<EnemyBullet> bullets) {
        this.bullets = bullets;
    }
}
