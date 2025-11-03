import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class EditorFrame extends JFrame{
	
	int WIDTH = 885;
	int HEIGHT = 674;
	
	EditorFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("  Notepad");
		this.setBackground(Color.white);
		this.setSize(900,660);
		this.getContentPane().setBackground(Color.white);
		this.setResizable(false);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		
		
		this.setVisible(true);
		
	}

}
