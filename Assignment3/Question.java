// CS0401 Assignment 3
// Due 03/28/2016
// Created by Bingjie Wang - UPitt

// This contains the Question class for the program

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Question {

    private String _quesString = "";
    private int _numChoices = 0;
    private int _answer = -1;
    private int _inputAns = -1;
    private int _timesTried = -1, _corTimes = -1;

    // Constructor
    public Question(String quesString, int numChoices) {
        _quesString = quesString;
        _numChoices = numChoices;
    }

    // Accessors - accesses and returns data
    public String getQues() {
        return _quesString;
    }

    public int getNumChoices() {
        return _numChoices;
    }

    public int getTimesTried() {
        String[] arrOneQues = getOneQuestion(_quesString, _numChoices);
        _timesTried = Integer.parseInt(arrOneQues[_numChoices+3]);
        return _timesTried;
    }

    public int getCorTimes() {
        String[] arrOneQues = getOneQuestion(_quesString, _numChoices);
        _corTimes = Integer.parseInt(arrOneQues[_numChoices+4]);
        return _corTimes;
    }

    public int correctAns() {
        String[] arrOneQues = getOneQuestion(_quesString, _numChoices);
        _answer = Integer.parseInt(arrOneQues[_numChoices+2]);
        return _answer;
    }

    public String[] choices() {
        String[] arrOneQues = getOneQuestion(_quesString, _numChoices);
        String[] arrChoices = new String[_numChoices];

        for (int j = 0; j < _numChoices; j++) {
            arrChoices[j] = arrOneQues[j+2];
        }

        return arrChoices;
    }

    // Mutator - modifies the state of the object
    public void writeAnsTried(int tries) {
        String[] arrOneQues = getOneQuestion(_quesString, _numChoices);
        arrOneQues[_numChoices+3] = Integer.toString(tries);
    }

    public void writeAnsCorrect(int triesCorr) {
        String[] arrOneQues = getOneQuestion(_quesString, _numChoices);
        arrOneQues[_numChoices+4] = Integer.toString(triesCorr);
    }

    // Method - not associated with an object
    private static String[] getOneQuestion(String _quesString, int _numChoices) {
        int queSize = 5 + _numChoices;
        String[] oneQuestion = new String[queSize];

        try {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br = new BufferedReader(new FileReader("questions.txt"));

            // read the first line from the text file
            String line = br.readLine();
            while (line != null) {
                if (line.contains(_quesString)) {
                    for (int i = 0; i < queSize; i++) {
                        oneQuestion[i] = line;
                        line = br.readLine();
                    }
                    break;
                }
                line = br.readLine();
            }

            // close file stream
            br.close();
        }

        // handle exceptions
        catch (FileNotFoundException fnfe) {
            System.out.println("file not found");
        }

        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return oneQuestion;
    }

}
