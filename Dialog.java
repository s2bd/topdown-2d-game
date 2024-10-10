import java.awt.Graphics;
import java.awt.Color;

/**
 * Overhead speech bubbles
 *
 * @author Dewan Mukto
 * @version 2024-10-10
 */
public class Dialog
{
    long lastSpeakTime;
    boolean isSpeaking = false;

    public void drawSpeechBubble(Graphics g, int x, int y, String phrase)
    {
        // creating a rectangular speech bubble over the character
        g.setColor(Color.WHITE);
        g.fillRect(x, y - 40, 100, 30); // above the character's y-axis
        g.setColor(Color.BLACK);
        g.drawRect(x, y - 40, 100, 30);
        g.drawString(phrase, x + 5, y - 20);
    }
    
    public void update(NPC npc){
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastSpeakTime > 3000){
          isSpeaking = false;
          lastSpeakTime = currentTime;
        } else {
          isSpeaking = true;
        }
    }
}
