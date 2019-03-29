import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

public class FrontEnd extends JFrame {
  public static final long serialVersionUID = 1L;
  public static void main(String[] args) {   
    FrontEnd frontEnd = new FrontEnd();
  }

  private JPanel centre;
  private JPanel south;
  private JPanel north;
  private BinSearchTree bst;

  public FrontEnd() {
    super();
    centre = new JPanel();
    south = new JPanel();
    north = new JPanel();
    setLayout(new BorderLayout(10, 10));
    generateTopPanel();
    generateButtons();

    add("South", south);
    add("North", north);
    add("Center", centre);
    pack();
    setVisible(true);
  }

  public void generateButtons() {
    JButton insert = new JButton("Insert");
    JButton search = new JButton("Search");
    JButton browse = new JButton("Browse");
    JButton create = new JButton("Create Tree from File");

    create.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Button was clicked!");
            buildTree();
          }
    });
    
    south.add(insert);
    south.add(search);
    south.add(browse);
    south.add(create);    
  }

  public void generateTopPanel() {
    JLabel text = new JLabel("An Application to Maintain Student Records");
    north.add(text);    
  }

  public void buildTree() {
    String fileName = getInputFileName();
    JOptionPane.showMessageDialog(null, "Done!");
  }

  public String getInputFileName() {
    String s = null;
    while (s == null || s.length() == 0) {
      s = (String) JOptionPane.showInputDialog("Please enter a file name:");
    }
    return s;
  }
}