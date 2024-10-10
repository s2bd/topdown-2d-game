import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.Graphics;

/**
 * The non-player characters (NPCs)
 *
 * @author Dewan Mukto
 * @version 2024-10-10
 */
public class NPC extends Character {
    private Random random = new Random();

    public NPC(int x, int y, BufferedImage spritesheet) {
        super(x, y, spritesheet);
        changeDirection();
    }

    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameChangeTime > 100) {
            frame = (frame + 1) % 3; // cycle through frames in current row of spritesheet
            lastFrameChangeTime = currentTime;
        }

        // Moving the NPC
        switch (direction) {
            case "NORTH": y -= speed; break;
            case "SOUTH": y += speed; break;
            case "WEST": x -= speed; break;
            case "EAST": x += speed; break;
        }

        // Temporary behavior: randomly change direction every few frames
        if (random.nextInt(100) < 5) {
            changeDirection();
        }

        // Keep the NPC within bounds
        keepWithinBounds(800, 600);
    }

    private void changeDirection() {
        int dir = random.nextInt(4);
        switch (dir) {
            case 0: direction = "NORTH"; break;
            case 1: direction = "SOUTH"; break;
            case 2: direction = "WEST"; break;
            case 3: direction = "EAST"; break;
        }
    }
}