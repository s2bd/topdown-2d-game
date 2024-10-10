import javax.swing.JFrame;

/**
 * Launches the main menu of the game
 *
 * @author Dewan Mukto
 * @version 2024-10-10
 */
public class Main
{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Delta Telekom");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Menu());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}