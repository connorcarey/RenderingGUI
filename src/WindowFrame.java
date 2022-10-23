import javax.swing.*;
import java.awt.*;

public class WindowFrame extends JFrame {

    /**
     * Constructor for WindowFrame.
     */
    public WindowFrame() {
        // Creating the projection method panel.
        DrawPanel dPanel = new DrawPanel(800, 800);
        // Creating a menu panel to switch between different rendering methods and view their options. {options not available}
        JPanel menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(300, 800));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        menuPanel.setLayout(new CardLayout());

        // Boilerplate code that constructs the window of the program.
        setTitle("Connor's 3D Rendering");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(dPanel, BorderLayout.CENTER);
        add(menuPanel, BorderLayout.EAST);
        pack();
        setVisible(true);
        dPanel.run();
    }

    /**
     * Main method of the program.
     * When the object is created the frame is automatically constructed.
     *
     * @param args input
     */
    public static void main(String[] args) {
        WindowFrame frame = new WindowFrame();
    }

}