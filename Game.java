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
    private DialogueNode lenrNode1, lenrNode2, lenrNode3, lenrNode4;
    private DialogueNode hekkyNode1, hekkyNode2, hekkyNode3, hekkyNode4, hekkyNode5, hekkyNode6;
    private DialogueNode pannoniaeNode1;
    private DialogueNode linesNode1;
    private DialogueNode csokiNode1, csokiNode2, csokiNode3;
    private DialogueNode yyyNode1, yyyNode2;
    
    public Game()
    {
        // Set the properties for the game panel
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.GREEN); // Set a initial background color for the game panel
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
    
    private BufferedImage fractalBackground;
    
    /**
     * Renders all graphical elements in the playable area
     *
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Generate static square tile background
        if (fractalBackground == null) {
            fractalBackground = createFractalBackground(getWidth(), getHeight());
        }
        g.drawImage(fractalBackground, 0, 0, null);
        
        player.draw(g);
        if(!player.isInteracting){
            showInteraction = false;
            currentNPC = null;
            closestDistance = Double.MAX_VALUE;
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
        } else if (currentNPC != null) {
            currentNPC.draw(g);
        }
        
        if (showInteraction) {
            // Draw 'E' interaction circle
            g.setColor(Color.BLUE);
            g.fillOval(player.getX(), player.getY() - 20, 40, 40);
            g.setColor(Color.WHITE);
            g.drawString("E", player.getX() + 17, player.getY() + 5);
        }
        // Top-left screen overlay text
        g.setColor(Color.GRAY);
        g.drawString("Delta Telekom - Early Access v0.1.4",10,20);
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
        lenrNode3 = new DialogueNode("Lenr", "Oh, nice to meet ya, Murkot! Hope ya have a pleasant stay here.", Arrays.asList(""), null);
        lenrNode4 = new DialogueNode("Lenr", "Hmm, even if ya don't answer, it's okay.", Arrays.asList(""), null);
        lenrNode2 = new DialogueNode("Lenr", "Ah, you must be new here! What's your name?", Arrays.asList("Hi, my name is Murkot.", "(Remain silent)"), Arrays.asList(lenrNode3, lenrNode4));
        lenrNode1 = new DialogueNode("Lenr", "Hi there, welcome to Internet Protocol Over Telekom", Arrays.asList("Hello!"), Arrays.asList(lenrNode2));
        // Link nodes based on choices
        lenrNode1.setNextNodes(Arrays.asList(lenrNode2));
        lenrNode2.setNextNodes(Arrays.asList(lenrNode3, lenrNode4));
        dialogueTrees.put("Lenr", lenrNode1);
        
        // Hekky
        hekkyNode6 = new DialogueNode("Hekky", "Well... I made them! :P You can try them out on VRChat if you've got the time.", Arrays.asList(""), null);
        hekkyNode5 = new DialogueNode("Hekky", "Ah, yes! Looks like you know more about me than I do about you! XD", Arrays.asList(""), null);
        hekkyNode4 = new DialogueNode("Hekky", "Not bad, how about yours?", Arrays.asList("Not bad, how about yours?", "Awful!"), Arrays.asList(hekkyNode5, hekkyNode6));
        hekkyNode3 = new DialogueNode("Hekky", "Ah, I'm good. I'm good! Did you know about Augmented Hip? And Hyblocker shaders?", Arrays.asList("Yes, you are Hekky the Hyblocker dev!", "No, I have not"), Arrays.asList(hekkyNode5, hekkyNode6));
        hekkyNode2 = new DialogueNode("Hekky", "Hewwo, Murkot! How's your day going?", Arrays.asList("Not bad, how about yours?", "Awful!"), Arrays.asList(hekkyNode3, hekkyNode4));
        hekkyNode1 = new DialogueNode("Hekky", "Hey there!", Arrays.asList(""), Arrays.asList(hekkyNode2));
        // Link nodes based on choices
        hekkyNode1.setNextNodes(Arrays.asList(hekkyNode2));
        hekkyNode2.setNextNodes(Arrays.asList(hekkyNode3, hekkyNode4));
        dialogueTrees.put("Hekky", hekkyNode1);
        
        // Pannoniae
        pannoniaeNode1 = new DialogueNode("Pannoniae", "Oi bruv, where'd ya come from? Haven't seen the likes of ye before. Just be careful of Csoki, aye. Blud's gone nuts thinking about unprimed walls!", Arrays.asList(""), null);
        // Link nodes based on choices
        dialogueTrees.put("Pannoniae", pannoniaeNode1);
        
        // Lines
        linesNode1 = new DialogueNode("Lines", "Whatever you do... Do NOT talk to Csoki!", Arrays.asList(""), null);
        // Link nodes based on choices
        dialogueTrees.put("Lines", linesNode1);
        
        // Csoki
        csokiNode3 = new DialogueNode("Csoki", "Darrrrrr", Arrays.asList(""), null);
        csokiNode2 = new DialogueNode("Csoki", "\"\"\"\"\"\"", Arrays.asList("Eh?"), Arrays.asList(csokiNode3));
        csokiNode1 = new DialogueNode("Csoki", "$$$$$$", Arrays.asList("Huh?"), Arrays.asList(csokiNode2));
        // Link nodes based on choices
        csokiNode1.setNextNodes(Arrays.asList(csokiNode2));
        dialogueTrees.put("Csoki", csokiNode1);
        
        // Yyy
        yyyNode2 = new DialogueNode("Yyy", "Hmm, 'kay then. Stay positive, eat protein, and remain stealthy.", Arrays.asList(""), null);
        yyyNode1 = new DialogueNode("Yyy", "Yeah, you're that guy who makes weird apps and stuff, right?", Arrays.asList("What? Really?", "Whoa... really?"), Arrays.asList(yyyNode2, yyyNode2));
        // Link nodes based on choices
        yyyNode1.setNextNodes(Arrays.asList(yyyNode2, yyyNode2));
        dialogueTrees.put("Yyy", yyyNode1);
    }
    
    private DialogueNode currentDialogueNode = lenrNode1;
    /**
     * Begins verbal interaction with NPCs
     *
     * @param npc the current NPC the player is talking to
     */
    private void startDialogue(NPC npc){
        currentDialogueNode = dialogueTrees.get(npc.getName());
        if(currentDialogueNode != null){
            showDialogueNode(currentDialogueNode);
            messagePanel = new MessagePanel(this, currentNPC, currentDialogueNode);
            messagePanel.setPreferredSize(new Dimension(600,150));
            messagePanel.setBounds(100,400,600,150);
            add(messagePanel);
            revalidate();
            repaint();
            npc.setInteracting(true);
            player.isInteracting = true;
        } else {
            System.out.println("Dialogue not found for NPC: " + npc.getName());
        }
    }
    
    private void showDialogueNode(DialogueNode node){
        if(messagePanel != null){
            messagePanel.updateNode(node);
        }
    }
    
    // private void proceedDialogue(DialogueNode nextNode){
        // System.out.println("Proceeding to next node: " + nextNode);
        // if(currentDialogueNode == lenrNode3 || currentDialogueNode == lenrNode4){
            // spawnOtherNPCs();
        // }
        // if(nextNode == null){
            // endDialogue(currentNPC, messagePanel);
        // } else {
            // currentDialogueNode = nextNode;
            // messagePanel.updateNode(currentDialogueNode);
        // }
    // }
    
    public void endDialogue(NPC npc, MessagePanel dialoguePanel){
        npc.setInteracting(false); // NPC continues doing their thing
        remove(dialoguePanel);
        messagePanel = null;
        revalidate();
        repaint();
        player.isInteracting = false;
    }
    
    public void spawnOtherNPCs(){
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
    
    // Method to generate a fractal background
    private BufferedImage createFractalBackground(int width, int height) {
        BufferedImage fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = fractalImage.getGraphics();
        
        // Example fractal pattern using a simple tile grid
        for (int x = 0; x < width; x += 20) {
            for (int y = 0; y < height; y += 20) {
                // Choose colors based on some fractal logic
                Color color = (x / 20 % 2 == y / 20 % 2) ? Color.LIGHT_GRAY : Color.DARK_GRAY;
                g.setColor(color);
                g.fillRect(x, y, 20, 20);
            }
        }
        
        g.dispose();
        return fractalImage;
    }
}
