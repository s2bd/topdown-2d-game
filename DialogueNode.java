import java.util.List;

/**
 * Custom data structure for NPC messages
 *
 * @author Dewan Mukto
 * @version 2024-10-10
 */
public class DialogueNode {
    private String speaker;
    private String message;
    private List<String> choices;
    private List<DialogueNode> nextNodes;

    public DialogueNode(String speaker, String message, List<String> choices, List<DialogueNode> nextNodes){
        this.speaker = speaker;
        this.message = message;
        this.choices = choices;
        this.nextNodes = nextNodes;
    }
    
    public String getSpeaker(){
        return speaker;
    }
    public List<String> getChoices(){
        return choices;
    }
    public List<DialogueNode> getNextNodes(){
        return nextNodes;
    }
    
    public void setNextNodes(List<DialogueNode> nextNodes){
        this.nextNodes = nextNodes;
    }
}
