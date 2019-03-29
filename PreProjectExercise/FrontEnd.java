import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.UIManager;
/**
 * A front end to display a database of student ids, faculties, majors, and years.
 * @author Oscar Ryan Wong, Michael Jeremy Ustaris Olea
 * @version 1.0
 * @since March 28th, 2019
 */
public class FrontEnd extends JFrame {
  public static final long serialVersionUID = 1L;
  public static void main(String[] args) {  
    try { 
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
    } catch(Exception ignored) {
      
    } 
    FrontEnd frontEnd = new FrontEnd();
  }

  /**
   * Text to be displayed
   */
  private JTextArea display;
  /**
   * Center Panel
   */
  private JPanel centre;
  /**
   * South Panel
   */
  private JPanel south;
  /**
   * North Panel
   */
  private JPanel north;
  /**
   * Binary Search Tree
   */
  private BinSearchTree bst;
  
  /**
   * Constructs a new Front End.
   */
  public FrontEnd() {
    super();
    centre = new JPanel();
    south = new JPanel();
    north = new JPanel();
    setLayout(new BorderLayout(10, 10));
    initCenter();
    generateTopPanel();
    generateButtons();

    add("South", south);
    add("North", north);
    add("Center", centre);
    
    pack();
    setVisible(true);
  }
  
  /**
   * Setup the center panel.
   */
  public void initCenter() {
    centre.setLayout(new BorderLayout());
    display = new JTextArea(16, 50);
    display.setEditable(false);
    display.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); //placeholder
    JScrollPane scrollPane = new JScrollPane(display, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    centre.add("Center", scrollPane);
  }
  
  /**
   * Set up the buttons.
   */
  public void generateButtons() {
    JButton insert = new JButton("Insert");
    JButton search = new JButton("Search");
    JButton browse = new JButton("Browse");
    JButton create = new JButton("Create Tree from File");

    insert.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        insert();
        if(bst.root != null)
          displayTree();
      }
    });

    search.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        search();
      }
    });

    browse.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(bst.root != null)
          displayTree();
      }
    });

    create.addActionListener(new ActionListener() { 
          @Override
          public void actionPerformed(ActionEvent e) {
            buildTree();
            if(bst.root != null)
              displayTree();
          }
    });

    
    south.add(insert);
    south.add(search);
    south.add(browse);
    south.add(create);    
  }

  /**
   * Create the top panel.
   */
  public void generateTopPanel() {
    JLabel text = new JLabel("An Application to Maintain Student Records");
    north.add(text);    
  }

  /**
   * Search for an entry.
   */
  public void search() {
    String s = null;
    while (s == null || s.length() == 0) {
      s = (String) JOptionPane.showInputDialog("Please enter an entry id to search:");
    }
    Node result = bst.find(bst.root, s);
    if (result == null) {
      JOptionPane.showMessageDialog(null, "Entry not found!");
    } else {
      JOptionPane.showMessageDialog(null, result.toString());
    }
  }

  /**
   * Insert a new entry.
   */
  public void insert() {
    JTextField idField = new JTextField();
    JTextField facultyField = new JTextField();
    JTextField majorField = new JTextField();
    JTextField yearField = new JTextField();

    Object[] message = {
      "Enter the Student ID", idField,
      "Enter Faculty", facultyField,
      "Enter the Student's Major", majorField,
      "Enter year", yearField
    };

    JOptionPane.showMessageDialog(null, message);
    bst.insert(idField.getText(), facultyField.getText(), majorField.getText(), yearField.getText());
  }

  /**
   * Build the BST from a file.
   */
  public void buildTree() {
    bst = new BinSearchTree();
    String fileName = getInputFileName();    
    try {
      File inFile = new File(fileName);
      Scanner scan = new Scanner(inFile);
      String[] line;
      while(scan.hasNextLine()) {
        String s = scan.nextLine();
        line = s.trim().split("\\s+");
        bst.insert(line[0], line[1], line[2], line[3]);
      }
      scan.close();
      JOptionPane.showMessageDialog(null, "Done!");
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(null, "File does not exist!");
    }
  }
  
  /**
   * Display the Binary Search Tree data.
   */
  public void displayTree() {
    display.setText(bst.getString(bst.root));
  }
  
  /**
   * Retrieve the input file name from the user.
   * @return the input file name.
   */
  public String getInputFileName() {
    String s = null;
    while (s == null || s.length() == 0) {
      s = (String) JOptionPane.showInputDialog("Please enter a file name:");
    }
    return s;
  }
}