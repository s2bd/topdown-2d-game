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
    private int currentCharIndex = 0; // text character index
    private boolean typingComplete = false;
    private Timer typingTimer;
    private List<String> choices;
    private List<Rectangle> choiceBounds;
    
    public MessagePanel(NPC npc, DialogueNode node){
        this.currentNPC = npc;
        this.currentNode = node;
        this.currentMessage = node.getDialogue();
        this.choices = node.getChoices();
        choiceBounds = new ArrayList<>();
        
        setBackground(new Color(0,0,0,200)); // semi-transparent
        addMouseListener(this);
        
        typingTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a){
                if(currentMessage != null && currentCharIndex < currentMessage.length()){
                    currentCharIndex++;
                } else {
                    typingTimer.stop();
                    typingComplete = true;
                }
                repaint();
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
    
    private void handleChoice(String selectedChoice){
        DialogueNode nextNode = currentNode.getNextNode(selectedChoice);
        if (nextNode != null) {
            updateNode(nextNode);
        } else {
            endDialogue(); // End dialogue if no more nodes exist
        }
    }
    
    private void endDialogue() {
        currentNPC.setInteracting(false);
        remove(this); // Remove the message panel
        revalidate();
        repaint();
    }

    
    // render the visual novel message panel
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawString(currentMessage.substring(0, currentCharIndex), 10, 20);
        // draw choices (if any)
        if (choices != null) {
            choiceBounds.clear();
            for (int index = 0; index < choices.size(); index++) {
                String choice = choices.get(index);
                Rectangle choiceRectangle = new Rectangle(10, 40 + index * 30, 200, 25);
                choiceBounds.add(choiceRectangle);
                g.drawRect(choiceRectangle.x, choiceRectangle.y, choiceRectangle.width, choiceRectangle.height);
                g.drawString(choice, choiceRectangle.x + 5, choiceRectangle.y + 20);
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(typingComplete){
            for(int index = 0; index < choiceBounds.size(); index++){
                if(choiceBounds.get(index).contains(e.getPoint())){
                    String selectedChoice = choices.get(index);
                    handleChoice(selectedChoice);
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
}
