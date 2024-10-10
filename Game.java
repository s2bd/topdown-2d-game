import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.io.File;
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
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

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
    private NPC currentNPC;
    private Timer timer;
    private boolean showInteraction = false;
    private double closestDistance = Double.MAX_VALUE;
    private MessagePanel messagePanel;
    
    public Game()
    {
        // Set the properties for the game panel
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.GREEN); // Set a background color for the game panel
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        initializeDialogues();
        
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
        // spawning the first NPC
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char5, "Lenr"));
        
        // spawning the player at the center of the screen
        player = new Player((int)(800 - 78)/2,(int)(600 - 108)/2,char1);
        timer = new Timer(20, this);
        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        showInteraction = false;
        currentNPC = null;
        
        for(NPC npc : npcs) {
            npc.draw(g);
            if (isPlayerNearNPC(npc)) {
                double distance = getDistance(player.getX(), player.getY(), npc.getX(), npc.getY());
                if(distance < closestDistance){
                    closestDistance = distance;
                    showInteraction = true;
                    currentNPC = npc;
                }
                npc.showSpeechBubble();
            } else {
                npc.hideSpeechBubble();
            }
        }
        
        if (showInteraction) {
            // Draw 'E' interaction circle
            g.setColor(Color.BLUE);
            g.fillOval(player.getX() - 20, player.getY() - 20, 40, 40);
            g.setColor(Color.WHITE);
            g.drawString("E", player.getX() - 5, player.getY() + 5);
        }
        // Top-left screen overlay text
        g.setColor(Color.GRAY);
        g.drawString("Delta Telekom - Early Access v0.0.9",10,20);
    }
    
    private boolean isPlayerNearNPC(NPC npc) {
        Rectangle playerBounds = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        Rectangle npcBounds = new Rectangle(npc.getX(), npc.getY(), npc.getWidth(), npc.getHeight());
        return playerBounds.intersects(npcBounds);
    }
    
    private double getDistance(int x1, int y1, int x2, int y2){
        return Math.hypot(x1 - x2, y1 - y2); // coordinate geometry straight line
    }
    
    private boolean talked_to_lenr = false;
    private Map<String, DialogueNode> dialogueTrees;
    
    private void initializeDialogues() {
        dialogueTrees = new HashMap<>();
        // Lenr
        DialogueNode lenrNode3 = new DialogueNode("Lenr", "Oh, nice to meet ya, Murkot! Hope ya have a pleasant stay here.", null, null);
        DialogueNode lenrNode4 = new DialogueNode("Lenr", "Hmm, even if ya don't answer, it's okay.", null, null);
        DialogueNode lenrNode2 = new DialogueNode("Lenr", "Ah, you must be new here! What's your name?", Arrays.asList("Hi, my name is Murkot.", "(Remain silent)"), Arrays.asList(lenrNode3, lenrNode4));
        DialogueNode lenrNode1 = new DialogueNode("Lenr", "Hi there, welcome to Internet Protocol Over Telekom", Arrays.asList("Hello!"), Arrays.asList(lenrNode2));

        // Link nodes based on choices
        lenrNode1.setNextNodes(Arrays.asList(lenrNode2));
        lenrNode2.setNextNodes(Arrays.asList(lenrNode3, lenrNode4));
        dialogueTrees.put("Lenr", lenrNode1);
    }
    
    private DialogueNode currentDialogueNode;
    /**
     * Begins verbal interaction with NPCs
     *
     * @param npc the current NPC the player is talking to
     */
    private void startDialogue(NPC npc){
        messagePanel = new MessagePanel(currentNPC, currentDialogueNode);
        currentDialogueNode = dialogueTrees.get(npc.getName());
        showDialogueNode(currentDialogueNode);
        npc.setInteracting(true);
    }
    
    public void handleChoice(NPC npc, String selectedChoice){
        // proceed to next nodes or quit dialogue interaction
    }
    
    private void showDialogueNode(DialogueNode node){
        // idk
    }
    
    private void proceedDialogue(DialogueNode nextNode){
        if(nextNode == null){
            endDialogue(currentNPC, messagePanel);
        } else {
            currentDialogueNode = nextNode;
            messagePanel.updateNode(currentDialogueNode);
        }
    }
    
    private void endDialogue(NPC npc, MessagePanel dialoguePanel){
        npc.setInteracting(false); // NPC continues doing their thing
        remove(dialoguePanel);
        dialoguePanel = null;
        revalidate();
        repaint();
    }
    
    private void spawnOtherNPCs(){
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char2, "Yyy"));
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char3, "Csoki"));
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char6, "Hekky"));
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char7, "Lines"));
        npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), char8, "Pannoniae"));
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
        
        // If "E" is pressed and the player is near an NPC
        if (k.getKeyCode() == KeyEvent.VK_E && showInteraction && currentNPC != null) {
            showInteraction = false;  // Stop showing "E" prompt
            startDialogue(currentNPC);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent k){
        player.keyReleased(k);
    }
    
    @Override
    public void keyTyped(KeyEvent k){}
}
