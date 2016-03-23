// CS0401 Assignment 3
// Due 03/28/2016
// Created by Bingjie Wang - UPitt

// A simple multiple choice quiz
// This contains the main method for the program

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    public static void main(String[] args) throws IOException {

        // read quiz txt file from command line
        if (args.length > 0) {
            // System.out.println("First arg is: " + args[0]);
        } else {
            System.out.println("No args given!");
            System.exit(0);
        }
        String fileName = args[0];

        Scanner sc = new Scanner(System.in);
        List<String> queItem = new ArrayList<>();
        List<String> numAnsItem = new ArrayList<>();
        int intNumChoices = -1, intNumChoices2 = -1, intNumChoices3 = -1;
        int inputAns = -1;
        boolean incorrectInput = false;
        List<String> txtfile = new ArrayList<>();

        String p1 = "Right:", p2 = "Wrong:", p3 = "Pct:";
        String p4 = "Times Tried:", p5 = "Times Correct:", p6 = "Percent Correct:";

        try {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            for (String next, line = br.readLine(); line != null; line = next) {
                next = br.readLine();
                // System.out.println("Current line: " + line);
                // System.out.println("Next line: " + next);

                if (line.contains("?")) {
                    queItem.add(line); // put questions into one array
                    numAnsItem.add(next); // the number of answer choices of each question
                }
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

        String[] arrInputAns = new String[queItem.size()];

        System.out.println("Welcome to the Quiz Program! Good Luck!");

        // how many times get correct/wrong in this quiz
        int corrQuiz = 0, wrongQuiz = 0;

        for (int i = 0; i < queItem.size(); i++) {

            System.out.println('\n' + "Question " + i + ":");
            System.out.println(queItem.get(i));

            intNumChoices = Integer.parseInt(numAnsItem.get(i));

            Question oneQues = new Question(queItem.get(i), intNumChoices);

            // cumulative number of times that this question is answered correctly
            int corrTimesCum = oneQues.getCorTimes();
            // cumulative number of times that this question is answered
            int totTimesCum = oneQues.getTimesTried();

            String[] arrChoices = oneQues.choices();
            int intCorrectAns = oneQues.correctAns();

            System.out.println("Answers:");

            for (int j = 0; j < intNumChoices; j++) {
                System.out.println( j + ": " + arrChoices[j]);
            }

            System.out.print('\n' + "Your answer? (enter a number): ");

            incorrectInput = true;
            while (incorrectInput) {
                if (sc.hasNextInt()) {
                    int inputAns_if = sc.nextInt();
                    if (inputAns_if < 0 || inputAns_if >= intNumChoices) {
                        System.out.print("Your answer? (enter a number): ");
                    } else {
                        incorrectInput = false;
                    }
                    // get local variable inputAns_if outside if-loop
                    inputAns = inputAns_if;
                } else {
                    sc.next();
                    System.out.print("Your answer? (enter a number): ");
                }
            }

            totTimesCum++;
            arrInputAns[i] = arrChoices[inputAns];

            if (inputAns == oneQues.correctAns()) {
                corrTimesCum++;
                corrQuiz++;
            } else {
                wrongQuiz++;
            }

            // modify values in the object
            oneQues.writeAnsCorrect(corrTimesCum);
            oneQues.writeAnsTried(totTimesCum);

            // prepare for updating the txt file
            txtfile.add(oneQues.getQues());
            txtfile.add(Integer.toString(oneQues.getNumChoices()));
            for (int j = 0; j < intNumChoices; j++) {
                txtfile.add(arrChoices[j]);
            }
            txtfile.add(Integer.toString(oneQues.correctAns()));
            txtfile.add(Integer.toString(totTimesCum));
            txtfile.add(Integer.toString(corrTimesCum));

        } // end of reading all the questions

        // update the txt file
        BufferedWriter bw = null;
        bw = new BufferedWriter(new FileWriter("questions.txt"));
        for (int i = 0; i < txtfile.size(); i++) {
            bw.write(txtfile.get(i));
            bw.newLine();
        }
        bw.flush();
        bw.close();

        System.out.println('\n' + "Thanks for your answers!");
        System.out.println("Here are your results:" + '\n');

        for (int i = 0; i < queItem.size(); i++) {
            System.out.println('\n' + "Question: " + queItem.get(i));
            intNumChoices2 = Integer.parseInt(numAnsItem.get(i));

            Question oneQues2 = new Question(queItem.get(i), intNumChoices2);
            String[] arrChoices2 = oneQues2.choices();
            int intCorrectAns2 = oneQues2.correctAns();
            System.out.println("Answer: " + arrChoices2[intCorrectAns2]);
            System.out.println("Player Guess: " + arrInputAns[i]);
            if (arrInputAns[i].equalsIgnoreCase(arrChoices2[intCorrectAns2])) {
                System.out.println("       Result: CORRECT! Great Work!");
            } else {
                System.out.println("       Result: INCORRECT! Remember the answer for next time!");
            }

        }

        System.out.println('\n' + "Your overall performance was:");
        // String p1 = "Right:", p2 = "Wrong:", p3 = "Pct:";
        double douPct = (((double)corrQuiz)/(corrQuiz + wrongQuiz)) * 100;
        System.out.printf("%14s %4d %n", p1, corrQuiz);
        System.out.printf("%14s %4d %n", p2, wrongQuiz);
        System.out.printf("%12s %5.1f%% %n", p3, douPct);

        System.out.println('\n' + "Here are some cumulative statistics:");
        double[] arrPctCum = new double[queItem.size()];

        for (int i = 0; i < queItem.size(); i++) {
            System.out.println("Question: " + queItem.get(i));
            intNumChoices3 = Integer.parseInt(numAnsItem.get(i));

            Question oneQues3 = new Question(queItem.get(i), intNumChoices3);
            // cumulative number of times that this question is answered correctly
            int corrTimesCumNew = oneQues3.getCorTimes();
            // cumulative number of times that this question is answered
            int totTimesCumNew = oneQues3.getTimesTried();

            // String p4 = "Times Tried:", p5 = "Times Correct:", p6 = "Percent Correct:";
            double douPctCum = ((double)corrTimesCumNew/totTimesCumNew) * 100;
            // put all pct right in an array for comparing later to find out easiest/hardest
            arrPctCum[i] = douPctCum;

            System.out.printf("%20s %d %n", p4, totTimesCumNew);
            System.out.printf("%22s %d %n", p5, corrTimesCumNew);
            System.out.printf("%24s %.1f%% %n", p6, douPctCum);
        }

        System.out.println('\n' + "Easiest Question:");
        int maxIdx = findMaxIdx(arrPctCum);
        // System.out.println(maxIdx);
        System.out.println("Question: " + queItem.get(maxIdx));
        Question oneQuesMax = new Question(queItem.get(maxIdx), Integer.parseInt(numAnsItem.get(maxIdx)));
        int corrTimesCumMax = oneQuesMax.getCorTimes();
        int totTimesCumMax = oneQuesMax.getTimesTried();
        // String p4 = "Times Tried:", p5 = "Times Correct:", p6 = "Percent Correct:";
        double douPctCumMax = ((double)corrTimesCumMax/totTimesCumMax) * 100;

        System.out.printf("%20s %d %n", p4, totTimesCumMax);
        System.out.printf("%22s %d %n", p5, corrTimesCumMax);
        System.out.printf("%24s %.1f%% %n", p6, douPctCumMax);

        System.out.println("Hardest Question:");
        int minIdx = findMinIdx(arrPctCum);
        // System.out.println(minIdx);
        System.out.println("Question: " + queItem.get(minIdx));
        Question oneQuesMin = new Question(queItem.get(minIdx), Integer.parseInt(numAnsItem.get(minIdx)));

        // overwrite - b/c only printing out here, not a problem
        corrTimesCumMax = oneQuesMin.getCorTimes();
        totTimesCumMax = oneQuesMin.getTimesTried();
        douPctCumMax = ((double)corrTimesCumMax/totTimesCumMax) * 100;

        System.out.printf("%20s %d %n", p4, totTimesCumMax);
        System.out.printf("%22s %d %n", p5, corrTimesCumMax);
        System.out.printf("%24s %.1f%% %n", p6, douPctCumMax);

    }

    public static int findMinIdx (double[] arr) {
        int index = 0;
        double min = arr[index];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min ) {
                min = arr[i];
                index = i;
            }
        }
        return index;
    }

    public static int findMaxIdx (double[] arr) {
        int index = 0;
        double max = arr[index];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max ) {
                max = arr[i];
                index = i;
            }
        }
        return index;
    }

}
