import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.List;
import java.util.Arrays;

/**
 * The non-player characters (NPCs)
 *
 * @author Dewan Mukto
 * @version 2024-10-10
 */
public class NPC extends Character {
    private String name;
    private Random random = new Random();
    private List<String> dialogues;
    private String currentDialogue;
    private boolean isInteracting = false;
    private boolean speechBubbleVisible;
    private long speechBubbleStartTime;

    public NPC(int x, int y, BufferedImage spritesheet, String name) {
        super(x, y, spritesheet);
        this.name = name;
        setDialogues(name);
        changeDirection();
    }
    
    public void setInteracting(boolean interacting) {
        this.isInteracting = interacting;
    }

    @Override
    public void update() {
        if(isInteracting){
            return; // do nothing while talking with player
        }
        
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
        
        // Hide speech bubble after 5 seconds
        if (speechBubbleVisible && System.currentTimeMillis() - speechBubbleStartTime > 5000) {
            speechBubbleVisible = false;
        }
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
    
    private void setDialogues(String name){
        switch (name){
            case "Lenr":
                dialogues = Arrays.asList("Huh?", "Welcome to Telekom!", "Need something?");
                break;
            case "Hekky":
                dialogues = Arrays.asList("Hihi!", "Hi :3", "Shaders are cool");
                break;
            case "Pannoniae":
                dialogues = Arrays.asList("Meow :3", "'Hiya!", "How r u?");
                break;
            case "Lines":
                dialogues = Arrays.asList("Nya~", "UwU", "Good day, innit?");
                break;
            case "Csoki":
                dialogues = Arrays.asList("#@!%#$%", "!#!@#", "^&$^#", "GET AWAY!");
                break;
            case "Yyy":
                dialogues = Arrays.asList(" ... ", " ... ");
                break;
        }
    }
    
    public void showSpeechBubble(){
        if(!speechBubbleVisible){
            speechBubbleVisible = true;
            currentDialogue = dialogues.get(random.nextInt(dialogues.size()));
            speechBubbleStartTime = System.currentTimeMillis();
        }
    }
    
    public void hideSpeechBubble(){
        speechBubbleVisible = false;
    }
    
    public void draw(Graphics g){
        super.draw(g);
        if (speechBubbleVisible) {
            g.setColor(Color.WHITE);
            g.fillRoundRect(x, y - 40, 100, 20, 10, 10); // basic speech bubble
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString(currentDialogue, x + 5, y - 25);
        }
    }
    
    public String getName(){
        return name;
    }
}
