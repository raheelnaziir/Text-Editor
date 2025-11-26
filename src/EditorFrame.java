import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.undo.UndoManager;

public class EditorFrame extends JFrame implements ActionListener{

	JTextArea textArea;
    JScrollPane scrollPane;
    JComboBox fontPicker;
    JSpinner spinnerFont;
    UndoManager undoManager;
    JMenuBar menuBar;
    JMenu fileMenu, fileEdit,formatItem;
    JMenuItem openFile,saveFile, exitFile;
    JMenuItem copyItem,pasteItem,cutItem,undoItem,findItem,selectAllItem,delItem, redoItem, newFileItem,colorItem, fontItem, fontSize;
    String userInput;

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
        this.setResizable(true);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setIconImage(icon.getImage());

        
        textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(null);
		textArea.setFont(new Font("Arial",Font.PLAIN,16));

        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        scrollPane.setBorder(null);


        spinnerFont = new JSpinner();
        spinnerFont.setValue(18);
        spinnerFont.setPreferredSize(new Dimension(50,25));
        spinnerFont.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN,(int) spinnerFont.getValue()));
            }

        });

        fontPicker = new JComboBox(fonts);
        fontPicker.addActionListener(this);
        fontPicker.setBackground(Color.WHITE);
        fontPicker.setSelectedItem("Arial");
        
        menuBar = new JMenuBar();
		menuBar.setBackground(null);

        fileMenu = new JMenu("File");
        openFile = new JMenuItem("     Open...            ");
        saveFile = new JMenuItem("     Save As            ");
        exitFile = new JMenuItem("     Exit            ");
        newFileItem = new JMenuItem("    New File    ");

        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        exitFile.addActionListener(this);
        newFileItem.addActionListener(this);
        
        fileMenu.add(newFileItem);
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		fileMenu.add(exitFile);

        this.add(scrollPane);
        this.setVisible(true);
        this.setJMenuBar(menuBar);
    }

	@Override
	public void actionPerformed(ActionEvent e) {


        if(e.getSource()==fontPicker) {
            textArea.setFont(new Font((String)fontPicker.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }
		
	}




}