import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class EditorFrame extends JFrame implements ActionListener{

    int WIDTH = 885;
    int HEIGHT = 674;
    String []fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    ImageIcon icon = new ImageIcon("notepad.png");

    EditorFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("  Notepad");
        this.setBackground(Color.white);
        this.setSize(900,660);
        this.getContentPane().setBackground(Color.white);
        this.setResizable(false);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.setIconImage(icon.getImage());


        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }



}