import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;

/**
 * Starts the game itself
 *
 * @author Dewan Mukto
 * @version 2024-10-10
 */
public class Game extends JPanel implements ActionListener, KeyListener
{
    private BufferedImage char1, char2, char3, char4, char5, char6, char7, char8;
    private Player player;
    private List<NPC> npcs;
    private Timer timer;
    
    public Game()
    {
        // Set the properties for the game panel
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.GREEN); // Set a background color for the game panel
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        
        try {
            // Loading up the image assets
            char1 = ImageIO.read(new File("Assets/1.png"));
            char2 = ImageIO.read(new File("Assets/2.png"));
            char3 = ImageIO.read(new File("Assets/3.png"));
            char4 = ImageIO.read(new File("Assets/4.png"));
            char5 = ImageIO.read(new File("Assets/5.png"));
            char6 = ImageIO.read(new File("Assets/6.png"));
            char7 = ImageIO.read(new File("Assets/7.png"));
            char8 = ImageIO.read(new File("Assets/8.png"));
        } catch (Exception error) {
            error.printStackTrace();
        }
        
        npcs = new ArrayList<>();
        // creating NPCs
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char2));
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char3));
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char4));
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char5));
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char6));
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char7));
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char8));
        
        // spawning the player at the center of the screen
        player = new Player((int)(800 - 78)/2,(int)(600 - 108)/2,char1);
        timer = new Timer(20, this);
        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        for(NPC npc : npcs) {
            npc.draw(g);
        }
        // Top-left screen overlay text
        g.setColor(Color.GRAY);
        g.drawString("Delta Telekom - Early Access v0.0.9",10,20);
    }
    
    @Override
    public void actionPerformed(ActionEvent a) {
        player.update();
        for (NPC npc: npcs) {
            npc.update();
        }
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent k) {
        player.keyPressed(k);
    }
    
    @Override
    public void keyReleased(KeyEvent k){
        player.keyReleased(k);
    }
    
    @Override
    public void keyTyped(KeyEvent k){}
}
