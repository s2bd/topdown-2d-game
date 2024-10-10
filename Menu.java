import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Component;
import java.awt.BorderLayout;


/**
 * Handles the main menu screen
 *
 * @author Dewan Mukto
 * @version 2024-10-10
 */
public class Menu extends JPanel {
    public Menu() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Center everything vertically

        // Add title "DELTA"
        JLabel title = new JLabel("DELTA", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);

        // Add subtitle "Telekom" to fit the exact width of the title above
        JLabel subtitle = new JLabel("Telekom", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 32));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(subtitle);

        add(Box.createRigidArea(new Dimension(0, 100))); // Plenty of space

        // Rectangular buttons stacked in a column that change color slightly on hover
        JButton playButton = createButton("Play");
        playButton.addActionListener(e -> {
            removeAll();
            revalidate();
            repaint();
            Game gamePanel = new Game();
            add(gamePanel);
            gamePanel.requestFocusInWindow();
            revalidate();
            repaint();
        });
        add(playButton);

        JButton helpButton = createButton("Help");
        helpButton.addActionListener(e -> {
            removeAll(); // Clear Menu
            showHelpInfo(); // Show Help
            revalidate();
            repaint();
        });
        add(helpButton);

        JButton exitButton = createButton("Exit");
        exitButton.addActionListener(e -> terminate());
        add(exitButton);
    }

    // Quick method to create buttons with hover effect
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(Color.blue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.getHSBColor(2.24f, 1.00f, 0.5882f));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.blue);
            }
        });
        return button;
    }

    private void showHelpInfo() {
        setLayout(new BorderLayout());

        // Add custom scroll bar to the right-side with rectangles
        JEditorPane helpText = new JEditorPane();
        helpText.setEditable(false);
        helpText.setBackground(Color.BLACK);
        helpText.setForeground(Color.WHITE);
        helpText.setContentType("text/html");
        helpText.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        helpText.setText("<html>"
        + "<style>a { text-decoration: none; color: #aaabbd }</style>"
        + "<div style='padding: 10px; color: white;'>CONTROLS</div>"
        + "<div style='padding-left: 10px; color: #aaaaaa;'>"
        + "Use <font color='#ff0a45'>W,A,S,D</font> or <font color='#ff0a45'>arrow keys</font> to move<br>"
        + "Press <font color='#ff0a45'>E</font> to interact, use"
        + "</div><br><br>"
        + "<div style='padding: 10px; color: white;'>ABOUT</div>"
        + "<div style='padding-left: 10px; color: #aaaaaa;'>"
        + "Based on people I met in a real Discord server<br>"
        + "Developed by <font color='#ff0a45'>Dewan Mukto</font> (<a href='https://x.com/dewan_mukto'>@dewan_mukto</a>)<br>"
        + "Character sprites by <font color='#ff0a45'>Glitched Velocity</font> (<a href='https://x.com/VazahatJordan'>@VazahatJordan</a>)<br>"
        + "Published by <font color='#ff0a45'>Muxday</font>"
        + "</div>"
        + "</html>");

        JScrollPane scrollPane = new JScrollPane(helpText);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        add(scrollPane, BorderLayout.CENTER);
        
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> {
            removeAll();
            setLayout(new BoxLayout(Menu.this, BoxLayout.Y_AXIS));
            reinitializeMenu();
            revalidate();
            repaint();
        });
        add(backButton, BorderLayout.SOUTH);
    }

    // Custom scroll bar UI with rectangles
    private class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            thumbColor = Color.GRAY;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createInvisibleButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createInvisibleButton();
        }

        private JButton createInvisibleButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setVisible(false);
            return button;
        }
    }
    
    private void reinitializeMenu() {
    // Add title "DELTA"
    JLabel title = new JLabel("DELTA", SwingConstants.CENTER);
    title.setFont(new Font("SansSerif", Font.BOLD, 48));
    title.setForeground(Color.WHITE);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(title);

    // Add subtitle "Telekom" to fit the exact width of the title above
    JLabel subtitle = new JLabel("Telekom", SwingConstants.CENTER);
    subtitle.setFont(new Font("SansSerif", Font.PLAIN, 32));
    subtitle.setForeground(Color.GRAY);
    subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(subtitle);

    add(Box.createRigidArea(new Dimension(0, 100))); // Plenty of space

    // Rectangular buttons stacked in a column that change color slightly on hover
    JButton playButton = createButton("Play");
    playButton.addActionListener(e -> {
        removeAll();
        revalidate();
        repaint();
        Game gamePanel = new Game();
        add(gamePanel);
        gamePanel.requestFocusInWindow();
        revalidate();
        repaint();
    });
    add(playButton);

    JButton helpButton = createButton("Help");
    helpButton.addActionListener(e -> {
        removeAll(); // Clear Menu
        showHelpInfo(); // Show Help
        revalidate();
        repaint();
    });
    add(helpButton);

    JButton exitButton = createButton("Exit");
    exitButton.addActionListener(e -> terminate());
    add(exitButton);
}


    private void terminate() {
        System.exit(0); // Close everything and quit the program
    }
}
