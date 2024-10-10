import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 * Abstract class for characters in-game
 * 
 * @author Dewan Mukto
 * @version 2024-10-10
 */
public abstract class Character {
    protected int x, y;
    protected int speed = 2;
    protected BufferedImage spritesheet;
    protected int frame = 0;
    protected long lastFrameChangeTime;
    protected String direction;
    protected static final int FRAME_WIDTH = 78;
    protected static final int FRAME_HEIGHT = 108;


    public Character(int x, int y, BufferedImage spritesheet) {
        this.x = x;
        this.y = y;
        this.spritesheet = spritesheet;
    }

    /**
     * Renders specific frames from the spritesheet
     *
     * @param g graphical image provided, containing all the directional frames
     */
    public void draw(Graphics g) {
        int spriteX = (frame % 3) * FRAME_WIDTH; // according to frame width
        int spriteY = 0; // sprite facing south by default

        switch (direction) {
            case "SOUTH": spriteY = 0; break; // 1st row
            case "WEST": spriteY = 1 * 108; break; // 2nd row
            case "EAST": spriteY = 2 * 108; break; // 3rd row
            case "NORTH": spriteY = 3 * 108; break; // 4th row
        }

        // Actual rendering of the frame on-screen
        if (spriteX + FRAME_WIDTH <= spritesheet.getWidth() && spriteY + FRAME_HEIGHT <= spritesheet.getHeight()) {
            g.drawImage(spritesheet.getSubimage(spriteX, spriteY, FRAME_WIDTH, FRAME_HEIGHT), x, y, null);
        }
    }

    /**
     * Updates the character state, should be implemented in derived classes.
     */
    public abstract void update();

    protected void keepWithinBounds(int width, int height) {
        if (x < 0) x = 0;
        if (x > width - 78) x = width - 78;
        if (y < 0) y = 0;
        if (y > height - 108) y = height - 108;
    }
}
