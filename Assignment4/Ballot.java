/**
    CS0401 Assignment 4
    Due 04/13/2016
    Created by Bingjie Wang - UPitt

    This class contains the data for a ballot,
    the buttons needed to select a candidate for that office,
    and the ActionListener (or listeners) necessary to respond to the candidate choices.
*/

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ballot extends JPanel implements ActionListener {

    private String _eachBallot = "";
    private JLabel _cateLabel;
    private ButtonGroup bg;
    private JToggleButton[] toggleButton;
    int numBallots = 0;
    private boolean _findId = false;
    public String _actText = "";
    private boolean selectedB;

    /**
        Constructor
    */
    public Ballot(String eachBallot) {
        selectedB = false;
        _eachBallot = eachBallot;
        //_findId = findId;
        String[] indBallot = _eachBallot.split("[:,]");
        numBallots = indBallot.length - 2;
        toggleButton = new JToggleButton[numBallots];
        setLayout(new GridLayout(indBallot.length-1, 0));

        _cateLabel = new JLabel(indBallot[1]);
        _cateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // _cateLabel.setVerticalAlignment(SwingConstants.TOP);
        add(_cateLabel);

        // For grouping the buttons.
        bg = new ButtonGroup();
        Icon icon = new ImageIcon("air.png");
        for (int i = 0; i < numBallots; i++) {
            // Initialize radio buttons
            toggleButton[i] = new JToggleButton(indBallot[i+2], false);
            toggleButton[i].setHorizontalAlignment(SwingConstants.LEFT);
            // toggleButton[i].setPreferredSize(new Dimension(50,50));
            toggleButton[i].setIcon(icon);
            toggleButton[i].addActionListener(this);
            // toggleButton[i].addItemListener(this);
            bg.add(toggleButton[i]);
            add(toggleButton[i]);
        }

    }

    public void enableButtons() {
        for (JToggleButton token : toggleButton) {
            token.setEnabled(true);
        }
    }

    public void disableButtons() {
        for (JToggleButton token : toggleButton) {
            token.setEnabled(false);
        }
    }

    public void disSelect() {
        bg.clearSelection(); //button group!!! setSelected(false) does not work!!!
    }

    public void setBlack() {
        for (JToggleButton token : toggleButton) {
            token.setForeground(Color.BLACK);
        }
    }

    public void putIcon(Icon icon) {
        for (JToggleButton token : toggleButton) {
            token.setIcon(icon);
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < numBallots; i++) {
            if (toggleButton[i] == e.getSource()) {
                toggleButton[i].setForeground(Color.RED);
                // if (toggleButton[i].isSelected()) {
                //     this.disSelect();
                //     toggleButton[i].setForeground(Color.BLACK);
                // }
            } else {
                toggleButton[i].setForeground(Color.BLACK);
            }
       }
   }

   public String getSelectedText() {
       String selectedText = "";
       for (int i = 0; i < numBallots; i++) {
           if (toggleButton[i].isSelected()) {
               selectedText = toggleButton[i].getText();
           }
       }
       // System.out.println("selected: " + selectedText);
       return selectedText;
   }

   public JToggleButton[] getToggleList(String eachBallot) {
       String[] indBallot = eachBallot.split("[:,]");
       numBallots = indBallot.length - 2;
       toggleButton = new JToggleButton[numBallots];

       bg = new ButtonGroup();
       for (int i = 0; i < numBallots; i++) {
           // Initialize radio buttons
           toggleButton[i] = new JToggleButton(indBallot[i+2], false);
           toggleButton[i].addActionListener(this);
           //bg.add(toggleButton[i]);
           add(toggleButton[i]);
       }

       return toggleButton;
   }

   public String getBId() {
       String cateId = _eachBallot.split("[:,]")[0];
       return cateId;
   }

   public String[] getBCands() {
       String cateCandPre = _eachBallot.split("[:]")[2];
       // Now I have e.g. Shakespeare,Wilde,Yeats
       String[] cateCands = cateCandPre.split("[,]");
       return cateCands;
   }


}
