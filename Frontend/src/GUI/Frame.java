package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.*;

/**
 * The Tool Shop GUI.
 * @author Oscar Wong, Jeremy Olea
 * @version 1.0
 * @since April 3rd, 2019
 */
public class Frame extends JFrame {
  /**
   * Serial version UID for AccessibleJFrame
   */
  public static final long serialVersionUID = 1L;
  /**
   * Listener to interact with GUI actions.
   */
  private Listener listener;
  /**
   * North Panel, to contain the header for the project.
   */
  private JPanel northPanel;
  /**
   * Center Panel, which displays the list of tools (if applicable)
   */
  private JPanel centerPanel;
  /**
   * South Panel, which holds the buttons for the app.
   */
  private JPanel southPanel;
  /**
   * Text to be displayed by center panel.
   */
  private JTextArea centerText;

  /**
   * Constructs a new Frame.
   */
  public Frame() {
    super();
    setLayout(new BorderLayout(10, 10));
    initNorthPanel();
    initCenterPanel();
    initSouthPanel();
    add("North", northPanel);
    add("Center", centerPanel);
    add("South", southPanel);

    pack();
    setVisible(true);
  }

  /**
   * Sets the listener object for this Frame.
   * @param listener Object to attach to this Frame.
   */
  public void setListener(Listener listener) {
    this.listener = listener;
  }

  /**
   * Set up the South Panel (buttons).
   */
  public void initSouthPanel() {
    southPanel = new JPanel();
    JButton displayAllTools = new JButton("Display All Tools");

    displayAllTools.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String toolsList = listener.actionPerformed("GET/TOOLS");
        // System.out.println(toolsList);
        centerText.setText(toolsList);
      }
    });

    JButton search = new JButton("Search");

    search.addActionListener( new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String searchOption =(String) JOptionPane.showInputDialog("Would you like to search by name or id?").toUpperCase();
        if(searchOption.equals("NAME") || searchOption.equals("ID")) {
          String input = (String) JOptionPane.showInputDialog("Which Item would you like to look for?").toUpperCase();
          String toolInfo = listener.actionPerformed("SEARCH/TOOL" + "/" + searchOption + "/" + input);
          JOptionPane.showMessageDialog(null, toolInfo);
        } else {
          JOptionPane.showMessageDialog(null, "Invalid search option");
        }
      }
    });

    JButton decrease = new JButton("Decrease Quantity");
    decrease.addActionListener( new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        String input = (String) JOptionPane.showInputDialog("Which Item would you like to decrease").toUpperCase();
        String quantity = listener.actionPerformed("DECREASE/TOOL/QUANTITY/" + input);
        JOptionPane.showMessageDialog(null, quantity);
      }
    });

    JButton check = new JButton("Check Item Quantity");
    check.addActionListener( new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String input = (String) JOptionPane.showInputDialog("Which Item would you like to check the quantity for?").toUpperCase();
        String quantity = listener.actionPerformed("GET/TOOL/QUANTITY/" + input);
        JOptionPane.showMessageDialog(null, quantity);
      }
    });
    
    southPanel.add(search);
    southPanel.add(displayAllTools);
    southPanel.add(decrease);
    southPanel.add(check);
  }

  /**
   * Set up the Center Panel (content).
   */
  public void initCenterPanel() {
    centerPanel = new JPanel();
    centerText = new JTextArea(16, 50);
    centerText.setEditable(false);
    centerText.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");  // placeholder
    JScrollPane scrollPane = new JScrollPane(centerText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    centerPanel.add(scrollPane);
  }

  /**
   * Set up the North Panel (headings).
   */
  public void initNorthPanel() {
    northPanel = new JPanel();
    JLabel text = new JLabel("CLIENT: Welcome to Not Home Depot");
    northPanel.add(text);
  }
}