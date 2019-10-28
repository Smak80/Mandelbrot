import javax.swing.*;
import java.awt.*;

public class AltWindow extends JFrame{
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JCheckBox cbColor;
    private JCheckBox cbProp;
    private JButton btnExit;
    private JPanel window;

    public AltWindow(){
        setContentPane(window);
        this.setMinimumSize(new Dimension(450, 450));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

}