import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 * The player character
 *
 * @author Dewan Mukto
 * @version 2024-10-10
 */
public class Player extends Character {
    private boolean up, down, left, right;
    public boolean isInteracting = false;

    public Player(int x, int y, BufferedImage spritesheet) {
        super(x, y, spritesheet);
        this.direction = "SOUTH";
        this.isInteracting = isInteracting;
    }

    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameChangeTime > 100) {
            if (up || down || left || right) { // change frames only when moving
                frame = (frame + 1) % 3; // cycle through frames in current row of spritesheet
            }
            lastFrameChangeTime = currentTime;
        }

        // Moving the player
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;

        // Keep the player within bounds
        keepWithinBounds(800, 600);
    }

    /**
     * Detecting which keyboard button has been pressed
     */
    public void keyPressed(KeyEvent k) {
        switch (k.getKeyCode()) {
            case KeyEvent.VK_UP: up = true; direction = "NORTH"; break;
            case KeyEvent.VK_W: up = true; direction = "NORTH"; break;
            case KeyEvent.VK_LEFT: left = true; direction = "WEST"; break;
            case KeyEvent.VK_A: left = true; direction = "WEST"; break;
            case KeyEvent.VK_DOWN: down = true; direction = "SOUTH"; break;
            case KeyEvent.VK_S: down = true; direction = "SOUTH"; break;
            case KeyEvent.VK_RIGHT: right = true; direction = "EAST"; break;
            case KeyEvent.VK_D: right = true; direction = "EAST"; break;
        }
    }

    /**
     * Detecting which keyboard button has been released
     */
    public void keyReleased(KeyEvent k) {
        switch (k.getKeyCode()) {
            case KeyEvent.VK_UP: up = false; break;
            case KeyEvent.VK_W: up = false; break;
            case KeyEvent.VK_LEFT: left = false; break;
            case KeyEvent.VK_A: left = false; break;
            case KeyEvent.VK_DOWN: down = false; break;
            case KeyEvent.VK_S: down = false; break;
            case KeyEvent.VK_RIGHT: right = false; break;
            case KeyEvent.VK_D: right = false; break;
        }
    }
}
