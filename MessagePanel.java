import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.util.List;
import java.util.ArrayList;

/**
 * Visual Novel style messages
 *
 * @author Dewan Mukto
 * @version 2024-10-10
 */
public class MessagePanel extends JPanel implements MouseListener
{
    private String currentMessage;
    private NPC currentNPC;
    private DialogueNode currentNode;
    private int currentCharIndex; // text character index
    private boolean typingComplete = false;
    private Timer typingTimer;
    private List<String> choices;
    private List<Rectangle> choiceBounds;
    
    public MessagePanel(NPC npc, DialogueNode node){
        addMouseListener(this);
        currentNPC = npc;
        currentNode = node;
        this.currentMessage = "";
        this.choices = choices;
        choiceBounds = new ArrayList<>();
        
        typingTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                if(currentMessage != null && currentCharIndex < currentMessage.length()){
                    currentCharIndex++;
                    repaint();
                } else {
                    typingTimer.stop();
                    typingComplete = true;
                }
            }
        });
        typingTimer.start();
    }
    
    public void showMessage(String message, List<String> choices){
        currentMessage = message;
        currentCharIndex = 0;
        this.choices = choices;
    }
    
    public void updateNode(DialogueNode node){
        // Reset typing effect and display new message and choices
    }
    
    // render the visual novel message panel
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, getHeight() - 200, getWidth(), 200);
        g.setColor(Color.WHITE);
        g.drawString(currentMessage.substring(0, currentCharIndex), 50, getHeight() - 150);
        // draw choices (if any)
        if (choices != null) {
            choiceBounds.clear();
            for (int index = 0; index < choices.size(); index++) {
                String choice = choices.get(index);
                int x = 50;
                int y = getHeight() - 100 + (index * 30);
                g.drawString(choice, x, y);
                Rectangle bounds = new Rectangle(x, y - 15, g.getFontMetrics().stringWidth(choice), 20);
                choiceBounds.add(bounds);
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (choices != null) {
            for (int i = 0; i < choiceBounds.size(); i++) {
                if (choiceBounds.get(i).contains(e.getPoint())) {
                    String selectedChoice = choices.get(i);
                    // Notify the Game class of the selected choice
                    DialogueNode nextNode = currentNode.getNextNodes().get(i);
                    ((Game) getParent()).handleChoice(currentNPC, selectedChoice);
                    break;
                }
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
    
    public void actionPerformed(ActionEvent e){
        if (currentCharIndex < currentMessage.length()) {
            currentCharIndex++;
            repaint();
        } else {
            typingTimer.stop();
            typingComplete = true;
        }
    }
}
