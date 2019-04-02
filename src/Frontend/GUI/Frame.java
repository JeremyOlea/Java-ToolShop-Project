package Frontend.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Frontend.Controller.*;

public class Frame extends JFrame {
  public static final long serialVersionUID = 1L;
  private Listener listener;

  /**
   * South Panel, which holds the buttons for the app.
   */
  private JPanel southPanel;

  public Frame() {
    super();
    setLayout(new BorderLayout(10, 10));

    initSouthPanel();
    add("South", southPanel);

    pack();
    setVisible(true);
  }

  public void setListener(Listener listener) {
    this.listener = listener;
  }

  public void initSouthPanel() {
    southPanel = new JPanel();
    JButton displayAllTools = new JButton("Display All Tools");
    displayAllTools.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String toolsList = listener.actionPerformed("GET/TOOLS");
        System.out.println(toolsList);
      }
    });

    southPanel.add(displayAllTools);
  }
}