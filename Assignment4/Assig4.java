/**
    CS0401 Assignment 4
    Due 04/13/2016
    Created by Bingjie Wang - UPitt

    A graphic voting program
    This contains the main method for the program
*/

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Assig4 extends JFrame {

    private Ballot ballotPanel; // Ballots panel

    private JPanel buttonPanel; // To hold the buttons
    private JButton logButton;
    private JButton castButton;
    private String eachBallot = "";
    private static String _fileName = "";

    private static String _inputId = "";
    List<String> voterFile = new ArrayList<>();

    ArrayList<Ballot> ballotObjList;

    int _totBigPanel = 0;
    JToggleButton[] buttonList;

    public static void main(String[] args) throws IOException {
        // read ballots txt file from command line
        if (args.length > 0) {
            // System.out.println("First arg is: " + args[0]);
        } else {
            System.out.println("No args given!");
            System.exit(0);
        }
        String fileName = args[0];

        new Assig4(fileName);

    }

    /**
        Constructor
    */
    public Assig4(String fileName) {

        _fileName = fileName;

        setTitle("E-Vote Version 1.0");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(200, 200);

        List<String> ballotList = readBallot(_fileName);
        _totBigPanel = ballotList.size() + 2;
        setLayout(new GridLayout(0, _totBigPanel));

        ballotObjList = new ArrayList();

        buildButtonPanel();
        for (int i = 0; i < ballotList.size(); i++) {
            String eachBallot = ballotList.get(i);
            Ballot b = new Ballot(eachBallot);
            ballotObjList.add(b);
            add(b);
        }

        for (Ballot b : ballotObjList) {
            b.disableButtons();
        }
        add(buttonPanel);
        pack();
        setVisible(true);

    }

    /**
        The buildButtonPanel method builds the button panel.
    */
    private void buildButtonPanel() {
        // Create a panel for the buttons.
        buttonPanel = new JPanel();

        // Create the buttons.
        logButton = new JButton("Login to Vote");
        castButton = new JButton("Cast Your Vote");

        // Register the action listeners.
        logButton.addActionListener(new LogButtonListener());
        castButton.addActionListener(new CastButtonListener());

        // Add the buttons to the button panel.
        buttonPanel.add(logButton);
        buttonPanel.add(castButton);
        castButton.setEnabled(false);

    }

    /**
        Private method that returns ballots in an ArrayList
    */
    private static List<String> readBallot(String fileName) {
        _fileName = fileName;

        List<String> ballotList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(_fileName));
            // read the first line from the text file
            int numBallot = Integer.parseInt(br.readLine());
            String line = null;
            for (int i = 0; i < numBallot; i++) {
                line = br.readLine();
                ballotList.add(line);
            }
            br.close();
        }
        // handle exceptions
        catch (FileNotFoundException fnfe) {
            System.out.println("File not found!");
            System.exit(0);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(0);
        }

        return ballotList;
    }

    /**
        Private inner class that handles the event when
        the user clicks the logButtonbutton.
    */
    private class LogButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Show a dialog asking the user to type in a String:
            String inputId = JOptionPane.showInputDialog("Please enter your voter id.");

            _inputId = inputId;
            if (checkId(inputId) && !checkVoted(inputId)) {
                // id found in file & not voted
                castButton.setEnabled(true);
                logButton.setEnabled(false);
                JOptionPane.showMessageDialog(null, getName(inputId)+", please make your choices.", "Message", JOptionPane.INFORMATION_MESSAGE);
                for (Ballot b : ballotObjList) {
                    b.enableButtons();
                }

            } else if (!checkId(inputId)) {
                JOptionPane.showMessageDialog(null, "ID not found!", "Message", JOptionPane.INFORMATION_MESSAGE);
                castButton.setEnabled(false);
                logButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, getName(inputId)+", you have already voted!", "Message", JOptionPane.INFORMATION_MESSAGE);
                castButton.setEnabled(false);
                logButton.setEnabled(true);
            }
            revalidate();
            repaint();

        }
    }

    private class CastButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // "Select an Option" (JOptionPane.INFORMATION_MESSAGE) is the title
            int confirm = JOptionPane.showConfirmDialog(null,
            "Confirm vote?", "Select an Option",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                castButton.setEnabled(false);
                logButton.setEnabled(true);
                for (Ballot b : ballotObjList) {
                    b.disSelect();
                    b.setBlack();
                    b.disableButtons();
                }
                setVisible(true);
                JOptionPane.showMessageDialog(null, "Thank you for your vote!", "Message", JOptionPane.INFORMATION_MESSAGE);

                // update voters file
                List<String> voterList = getVoters();
                String[][] voterListUp = new String[voterList.size()][];
                List<String> indVoterList = new ArrayList<>();
                //String
                for (int i = 0; i < voterList.size(); i++) {

                    String[] indVoter = voterList.get(i).split(":");

                    //String[] updateVoter = new String[voterList.size()]；
                    if (_inputId.equals(indVoter[0])) {
                        indVoter[2] = "true";
                    }
                    String[] indVoter2 = new String [indVoter.length];
                    for (int j = 0; j < indVoter.length; j++) {
                        if (j < indVoter.length-1) {
                            indVoter2[j] = indVoter[j]+":";
                        } else {
                            indVoter2[j] = indVoter[j];
                        }
                    }
                    for (int k = 0; k < indVoter2.length; k++) {
                        voterListUp[i] = indVoter2;
                    }
                }

                try {
                    File fileVoterUp = new File("tempVoters.txt");
                    BufferedWriter bw = null;
                    bw = new BufferedWriter(new FileWriter(fileVoterUp));
                    for (int i = 0; i < voterListUp.length; i++) {
                        for (int j = 0; j < voterListUp[i].length; j++) {
                            bw.write(voterListUp[i][j]);
                        }
                        bw.newLine();
                    }
                    bw.flush();
                    bw.close();

                    fileVoterUp.renameTo(new File("voters.txt"));

                    // candidates results file
                    String[] cateCands; // a string[] of all candidates under one ballot id

                    for (Ballot b : ballotObjList) {
                        List<String> lineCandsArrUp = new ArrayList();
                        String selectedText = b.getSelectedText();
                        String candId = b.getBId();

                        File bFile = new File(candId+".txt");
                        //System.out.println("BId" + b.getBId());
                        boolean hasFile = bFile.exists();

                        if (!hasFile) {
                            // no file associate with this ballots id
                            // creat one
                            PrintWriter out = new PrintWriter(bFile);
                            cateCands = b.getBCands();
                            for (String cand : cateCands) {
                                if (selectedText.equals(cand)) {
                                    out.println(cand + ":1");
                                } else {
                                    out.println(cand + ":0");
                                }
                            }
                            out.close();
                        } else {
                            // has file - read in first, then update
                            BufferedReader br = new BufferedReader(new FileReader(candId+".txt"));
                            // read the first line from the text file
                            String lineCands = null;
                            while ((lineCands = br.readLine()) != null) {
                                //lineCands = br.readLine();
                                // lineCands is, e.g. clash:1
                                String[] lineCandsArr = lineCands.split("[:]");
                                // lineCandsArr[0] = clash, lineCandsArr[1] = 1;
                                if (selectedText.equals(lineCandsArr[0])) {
                                    int numVotes = Integer.parseInt(lineCandsArr[1]);
                                    numVotes += 1;
                                    lineCandsArr[1] = Integer.toString(numVotes);
                                } else {
                                    // nothing?
                                }
                                lineCandsArrUp.add(lineCandsArr[0]+":"+lineCandsArr[1]);
                            }
                            br.close();

                            File bFileUp = new File("tempResults.txt");
                            PrintWriter out2 = new PrintWriter(bFileUp);
                            for (String candsUp : lineCandsArrUp) {
                                out2.println(candsUp);
                            }
                            out2.close();

                            bFileUp.renameTo(new File(candId+".txt"));
                        }
                    }
                }
                catch (IOException ex) {
                    System.err.println("Caught IOException: " +  ex.getMessage());
                }

            } // if yes - confirmed
        }
    }

    private List<String> getVoters() {
        List<String> voterList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("voters.txt"));
            // read the first line from the text file
            String line = null;
            while ((line = br.readLine()) != null) {
                voterList.add(line);
            }
            br.close();
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("Voter file not found!");
            System.exit(0);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(0);
        }

        return voterList;
    }

    /**
        Private method that check the voter
        Returns true if finds id
    */
    private boolean checkId(String input) {
        boolean booCheckId = false;
        List<String> voterList = getVoters();

        for (int i = 0; i < voterList.size(); i++) {
            String[] indVoter = voterList.get(i).split(":");
            if (input.equals(indVoter[0])) {
                booCheckId = true;
            }
        }

        return booCheckId;
    }

    private boolean checkVoted(String input) {
        boolean status = false;
        String strStatus = "";
        List<String> voterList = getVoters();

        for (int i = 0; i < voterList.size(); i++) {
            String[] indVoter = voterList.get(i).split(":");

            if (input.equals(indVoter[0])) {
                strStatus = indVoter[2];
                if (strStatus.equals("true")) {
                    status = true;
                } else {
                    status = false;
                }
            }

        }
        return status;
    }

    private String getName(String input) {
        String name = "";
        List<String> voterList = getVoters();

        for (int i = 0; i < voterList.size(); i++) {
            String[] indVoter = voterList.get(i).split(":");
            if (input.equals(indVoter[0])) {
                name = indVoter[1];
            }
        }
        return name;
    }


}
