import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
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
    
    static final String DB_URL = "jdbc:mysql://localhost:3306/textEditorDB?useSSL=false&serverTimezone=UTC";
    static final String DB_USER = "root";
    static final String DB_PASS = "54513";

    EditorFrame() {
    	
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("  Untitled - Notepad");
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
        newFileItem = new JMenuItem("     New File     ");

        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        exitFile.addActionListener(this);
        newFileItem.addActionListener(this);
        
        fileMenu.add(newFileItem);
		fileMenu.add(openFile);
		fileMenu.add(saveFile);
		fileMenu.add(exitFile);
		
		fileEdit  = new JMenu("Edit");
		
		copyItem = new JMenuItem("     Copy                           Ctrl C");
		cutItem = new JMenuItem("     Cut                              Ctrl X");
		pasteItem = new JMenuItem("     Paste                          Ctrl V");
		findItem = new JMenuItem("     Find...       	                  Ctrl F");
		selectAllItem = new JMenuItem("     Select All                   Ctrl A");
		delItem = new JMenuItem("     Delete All");
		undoItem = new JMenuItem("     Undo                           Ctrl Z");
		redoItem = new JMenuItem("     Redo                           Ctrl Y");
		
		
		undoItem.addActionListener(this);
		redoItem.addActionListener(this);
		copyItem.addActionListener(this);
		cutItem.addActionListener(this);
		findItem.addActionListener(this);
		selectAllItem.addActionListener(this);
		delItem.addActionListener(this);
		

		fileEdit.add(undoItem);
		fileEdit.add(redoItem);
		fileEdit.add(copyItem);
		fileEdit.add(cutItem);
		fileEdit.add(delItem);
		fileEdit.add(findItem);
		fileEdit.add(selectAllItem);
		fileEdit.add(pasteItem);
		
		formatItem = new JMenu("Format");
		
		colorItem = new JMenuItem("     Color     ");
		colorItem.addActionListener(this);

		fontItem = new JMenuItem("     Font..");
		fontItem.addActionListener(this);
		
		fontSize = new JMenuItem("     Size");
		fontSize.addActionListener(this);
		
		formatItem.add(fontSize);
		formatItem.add(colorItem);
		formatItem.add(fontItem);
		
		menuBar.add(fileMenu);
		menuBar.add(fileEdit);
		menuBar.add(formatItem);

        this.add(scrollPane);
        this.setVisible(true);
        this.setJMenuBar(menuBar);
    }

	@Override
	public void actionPerformed(ActionEvent e) {


		if(e.getSource() == colorItem) {
			
			JColorChooser colorChooser = new JColorChooser();
			Color color = colorChooser.showDialog(null, "Choose color",Color.black);
			textArea.setForeground(color);
		}
		
		
        if(e.getSource()==fontPicker) {
            textArea.setFont(new Font((String)fontPicker.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }
        
        if(e.getSource()==fontPicker) {
			textArea.setFont(new Font((String)fontPicker.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
		}
        
        if (e.getSource() == openFile) {
            Object[] options = { "Disk", "Database" };
            int choice = JOptionPane.showOptionDialog(this, "Open from:", "Open", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                openFromDisk();
            } else if (choice == JOptionPane.NO_OPTION) {
                openFromDatabase();
            }
        }
        
        if (e.getSource() == saveFile) {
            Object[] options = { "Disk", "Database" };
            int choice = JOptionPane.showOptionDialog(this, "Save to:", "Save", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                saveToDisk();
            } else if (choice == JOptionPane.NO_OPTION) {
                saveToDatabaseWithPasswordPrompt();
            }
        }
        
        if (e.getSource() == exitFile) {
            int response = JOptionPane.showConfirmDialog(this, "save this file?");
            if (response == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
            if (response == JOptionPane.YES_OPTION) {
                Object[] options = { "Disk", "Database" };
                int choice = JOptionPane.showOptionDialog(this, "Save to:", "Save", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (choice == JOptionPane.YES_OPTION) saveToDisk();
                else if (choice == JOptionPane.NO_OPTION) saveToDatabaseWithPasswordPrompt();
            }
        }
		
		if(e.getSource()==delItem) {
			textArea.setText("");
		}
		
		if(e.getSource()==copyItem) {
			textArea.selectAll();
			textArea.copy();
		}
		
		if(e.getSource()==selectAllItem) {
			textArea.selectAll();
		}
		
		if(e.getSource()==cutItem) {
			textArea.selectAll();
			textArea.copy();
			textArea.setText("");
		}
		
		if(e.getSource()==pasteItem ) {
			textArea.paste();
		}
		
		if(e.getSource()==newFileItem) {
			
			int response = JOptionPane.showConfirmDialog(this, "Save this file?");
			
			if(response == JOptionPane.YES_OPTION) {
				save();
				this.dispose();
				new EditorFrame();
			} if(response == JOptionPane.NO_OPTION) {
				
				this.dispose();
				new EditorFrame();
			}
			
		}
		
		if(e.getSource()==fontItem) {
			JOptionPane.showMessageDialog(null, fontPicker);
		} 
		
		if(e.getSource()==fontSize) {
			JOptionPane.showMessageDialog(null, spinnerFont);
		}
		
		if(e.getSource()==findItem) {
			String searchWord = JOptionPane.showInputDialog(null, "Enter ");
			
			if(!searchWord.isEmpty()) {
				searchAndHighlight(searchWord);
			}
			
		} 
		
	}
	
	public void undo() {
		if(undoManager.canUndo()) {
				undoManager.undo();
		}
	}
	
	public void redo() {
		if(undoManager.canRedo()) {
			undoManager.redo();
		}
	}
	
	void saveToDisk() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file;
            PrintWriter fileOut = null;
            file = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".txt");
            try {
                fileOut = new PrintWriter(file);
                fileOut.println(textArea.getText());
                setTitle(file.getName() + " - Notepad");
                JOptionPane.showMessageDialog(this, "File saved successfully.");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } finally {
                if (fileOut != null) fileOut.close();
            }
        }
    }

    void openFromDisk() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            Scanner fileIn = null;
            try {
                fileIn = new Scanner(file);
                if (file.isFile()) {
                    textArea.setText("");
                    while (fileIn.hasNextLine()) {
                        String line = fileIn.nextLine() + "\n";
                        textArea.append(line);
                    }
                }
                setTitle(file.getName() + " - Notepad");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } finally {
                if (fileIn != null) fileIn.close();
            }
        }
    }

    // -------- MySQL integration methods with password protection --------
    void ensureTableExists() {
        String createSQL = "CREATE TABLE IF NOT EXISTS files ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(255),"
                + "content LONGTEXT,"
                + "password VARCHAR(128),"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
                + ") ENGINE=InnoDB;";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement st = conn.createStatement()) {
            st.execute(createSQL);
        } catch (SQLException ex) {
            System.err.println("Warning: could not ensure table exists: " + ex.getMessage());
        }
    }

	
	private void searchAndHighlight(String word) {
        String text = textArea.getText();
        int startIndex = text.indexOf(word);

        if (startIndex >= 0) {
            try {
                // Highlight the found word
                Highlighter highlighter = textArea.getHighlighter();
                highlighter.removeAllHighlights();
                
                highlighter.addHighlight(startIndex, startIndex + word.length(), new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
                
                textArea.setCaretPosition(startIndex); // move the cursor to the start of the word
                
                JOptionPane.showMessageDialog(this, "Word found!");
                            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Word not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
	



}