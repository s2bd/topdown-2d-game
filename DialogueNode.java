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
    public String getDialogue(){
        return message;
    }
    public List<String> getChoices(){
        return choices;
    }
    public List<DialogueNode> getNextNodes(){
        return nextNodes;
    }
    public DialogueNode getNextNode(String selectedChoice) {
        if (choices != null && nextNodes != null && choices.size() == nextNodes.size()) {
            int index = choices.indexOf(selectedChoice); // index of the selected choice
            if (index != -1) {
                return nextNodes.get(index); // if the choice is valid, return the corresponding next DialogueNode
            }
        }
        return null; // if the selected choice is not valid
    }
    
    public void setNextNodes(List<DialogueNode> nextNodes){
        this.nextNodes = nextNodes;
    }
}
