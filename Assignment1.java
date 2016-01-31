// CS 0401 Assignment 1
// Created by Bingjie Wang - UPitt

import java.util.Scanner;

public class Assignment1{

  public static void main(String [] args){

      int n, step = 0;
      //step stores the customer No.; needed for third customer discount
      double priBook = 5., priMark = 1., priPrint = 100., money;

      Scanner sc = new Scanner(System.in);
      System.out.print("More customers in line? (1 = Yes, 2 = No) ");

      while (true){
        n = sc.nextInt();
        step ++;

        int option, pack = 0, perzBook = 0;
        //perzBook is for the option to personalize a book
        int numBooks = 0, numMarks = 0, numPaints = 0, remain = 0;
        double payBook = 0., payMark = 0., payPaint = 0.;
        double tot = 0., subtot = 0., tax = 0., realSub = 0., discount = 0.;
        String message = "";

        while (n != 1 && n != 2){
          //handle invalid values
          System.out.print("More customers in line? (1 = Yes, 2 = No) ");
          n = sc.nextInt();
        }

        if (n == 1){

          do{
            System.out.println(
            "1 - Buy Books - $5.00 each" + '\n' +
            "2 - Buy Bookmarks - $1.00 each, $5.00 for a six-pack" + '\n' +
            "3 - Buy Paintings of Books - $100.00 each" + '\n' +
            "4 - See current order" + '\n' +
            "5 - Checkout");

            System.out.print("Please enter a valid option (1 through 5) > ");
            option = sc.nextInt();

            while (option < 1 || option > 5){
              System.out.print("Please enter a valid option (1 through 5) > ");
              option = sc.nextInt();
            }

            if (option == 1) {
              System.out.println("Currently in cart: " + numBooks + " books.");
              System.out.print("How many do you want to buy? > ");
              int numInpBooks = sc.nextInt();
              numBooks = numBooks + numInpBooks;
              payBook = numBooks * priBook;
            }

            else if (option == 2) {
              System.out.println("Currently in cart: " + numMarks +
              " bookmarks.");
              System.out.print("How many do you want to buy? > ");
              int numInpMarks = sc.nextInt();

              if (numInpMarks < 6) {
                // no 6 pack discount
                numMarks = numMarks + numInpMarks;
                payMark = numMarks * priMark;
              }

              else {
                // buy > 6 bookmarks, always give packs first
                pack = pack + (numInpMarks/6); // int division
                remain = remain + (numInpMarks%6); //remainder
                payMark = (remain * priMark) + (pack * 5.);
                numMarks = numMarks + numInpMarks; // total bookmarks
              }

            }

            else if (option == 3) {
              System.out.println("Currently in cart: " + numPaints +
              " paintings of books.");
              System.out.print("How many do you want to buy? > ");
              int numInpPaints = sc.nextInt();
              numPaints = numPaints + numInpPaints;
              payPaint = numPaints * priPrint;
            }

            else if (option == 4) {
              System.out.println("Currently in cart: ");
              System.out.println("Books: " + numBooks);
              System.out.println("Bookmarks: " + numMarks);
              System.out.println("Paintings of Books: " + numPaints);
            }

          } while (option != 5);

          if (step % 3 != 0) {
            // no discount
            if (numBooks > 0) {
              // add an option to personalize a book
              System.out.print("Would you like to personalize a book for an additional $1.00?"
              + '\n' + "(1 = Yes, 2 = No) ");
              perzBook = sc.nextInt();

              while (perzBook != 1 && perzBook != 2){
                //handle invalid values
                System.out.print("Please enter a valid option (1 = Yes, 2 = No) ");
                perzBook = sc.nextInt();
              }

              if (perzBook == 1) {
                System.out.print("Enter your message > ");
                message = sc.next();
              }
            }

            System.out.println("------------------------------------------");
            System.out.println("You did not get a discount! Better luck next time!");

            // print out receipt
            if (numBooks > 0 && perzBook == 1) {
              System.out.printf("    %d Normal Book(s):         $%.2f\n",
              numBooks-1, payBook-priBook);
              System.out.printf("    1 Personalized Book:      $%.2f\n    (message: %s)\n",
              priBook+1, message);
            }

            else if (numBooks > 0 && perzBook == 2) {
              System.out.printf("    %d Book(s):                $%.2f\n",
              numBooks, payBook);
            }

            if (numMarks > 0) {
              // show packs / single bookmarks
              if (pack > 0 && remain > 0){
                System.out.printf("    %d Bookmark Pack(s):       $%.2f\n",
                pack, pack*5.);
                System.out.printf("    %d Single Bookmark(s):     $%.2f\n",
                remain, remain*priMark);
              }
              else if (pack == 0){
                System.out.printf("    %d Single Bookmark(s):     $%.2f\n",
                numMarks, payMark);
              }
              else {
                // pack > 0 && remain = 0
                System.out.printf("    %d Bookmark Pack(s):       $%.2f\n",
                pack, pack*5.);
              }
            }
            if (numPaints > 0) {
              System.out.printf("    %d Paintings of Book(s):   $%.2f\n",
              numBooks, payPaint);
            }

            // personalized book requires one more $
            if (perzBook == 1) {
              subtot = payBook + 1 + payMark + payPaint;
              tax = subtot * 0.07;
              tot = subtot + tax;
              System.out.println(" ");
              System.out.printf("Subtotal:                     $%.2f\n", subtot);
              System.out.printf("Tax:                          $%.2f\n", tax);
              System.out.println(" ");
              System.out.printf("Total:                        $%.2f\n", tot);
              System.out.println(" ");
              System.out.println("------------------------------------------");
            }

            else {
              subtot = payBook + payMark + payPaint;
              tax = subtot * 0.07;
              tot = subtot + tax;
              System.out.println(" ");
              System.out.printf("Subtotal:                     $%.2f\n", subtot);
              System.out.printf("Tax:                          $%.2f\n", tax);
              System.out.println(" ");
              System.out.printf("Total:                        $%.2f\n", tot);
              System.out.println(" ");
              System.out.println("------------------------------------------");
            }

            // payment
            System.out.println(" ");
            System.out.print("Enter amount paid (no dollar sign) > ");
            money = sc.nextDouble();
            double change = 0;

            while (money < tot) {
              System.out.println("Not enough money, please re-enter.");
              System.out.print("Enter amount paid (no dollar sign) > ");
              money = sc.nextDouble();
            }
            if (money > tot) {
              change = money - tot;
              System.out.printf("Your change is: $%.2f\n", change);
              System.out.println("Thanks for shopping!");
            }
            else if (money == tot) {
              System.out.println("Exact amount received!");
              System.out.println("Thanks for shopping!");
            }

            System.out.print("More customers in line? (1 = Yes, 2 = No) ");
          } // end of if -  no discount ones

          else {
            // discount
            if (numBooks > 0) {
              // add an option to personalize a book
              System.out.print("Would you like to personalize a book for an additional $1.00?"
              + '\n' + "(1 = Yes, 2 = No) ");
              perzBook = sc.nextInt();

              while (perzBook != 1 && perzBook != 2){
                //handle invalid values
                System.out.print("Please enter a valid option (1 = Yes, 2 = No) ");
                perzBook = sc.nextInt();
              }

              if (perzBook == 1) {
                System.out.print("Enter your message > ");
                message = sc.next();
              }
            }

            System.out.println("------------------------------------------");
            System.out.println("You won a 10% discount!");

            // print out receipt
            if (numBooks > 0 && perzBook == 1) {
                System.out.printf("    %d Normal Book(s):         $%.2f\n",
                numBooks-1, payBook-priBook);
                System.out.printf("    1 Personalized Book:      $%.2f\n    (message: %s)\n",
                priBook+1, message);
              }

            else if (numBooks > 0 && perzBook == 2) {
              System.out.printf("    %d Book(s):                $%.2f\n",
              numBooks, payBook);
            }

            if (numMarks > 0) {
              // show packs / single bookmarks
              if (pack > 0 && remain > 0){
                System.out.printf("    %d Bookmark Pack(s):       $%.2f\n",
                pack, pack*5.);
                System.out.printf("    %d Single Bookmark(s):     $%.2f\n",
                remain, remain*priMark);
              }
              else if (pack == 0){
                System.out.printf("    %d Single Bookmark(s):     $%.2f\n",
                numMarks, payMark);
              }
              else {
                // pack > 0 && remain = 0
                System.out.printf("    %d Bookmark Pack(s):       $%.2f\n",
                pack, pack*5.);
              }
            }
            if (numPaints > 0) {
              System.out.printf("    %d Paintings of Book(s):   $%.2f\n",
              numPaints, payPaint);
            }

            // personalized book requires one more $
            if (perzBook == 1) {
              subtot = payBook + 1 + payMark + payPaint;
              discount = subtot * 0.1;
              realSub = subtot - discount;
              tax = realSub * 0.07;
              tot = realSub + tax;
              System.out.printf("Discount! Saved:             -$%.2f\n", discount);
              System.out.println("");
              System.out.printf("Subtotal:                     $%.2f\n", realSub);
              System.out.printf("Tax:                          $%.2f\n", tax);
              System.out.println(" ");
              System.out.printf("Total:                        $%.2f\n", tot);
              System.out.println(" ");
              System.out.println("------------------------------------------");
            }

            else {
              subtot = payBook + payMark + payPaint;
              discount = subtot * 0.1;
              realSub = subtot - discount;
              tax = realSub * 0.07;
              tot = realSub + tax;
              System.out.printf("Discount! Saved:             -$%.2f\n", discount);
              System.out.println("");
              System.out.printf("Subtotal:                     $%.2f\n", realSub);
              System.out.printf("Tax:                          $%.2f\n", tax);
              System.out.println(" ");
              System.out.printf("Total:                        $%.2f\n", tot);
              System.out.println(" ");
              System.out.println("------------------------------------------");
            }

            //payment
            System.out.println(" ");
            System.out.print("Enter amount paid (no dollar sign) > ");
            money = sc.nextDouble();
            double change = 0;

            while (money < tot) {
              System.out.println("Not enough money, please re-enter.");
              System.out.print("Enter amount paid (no dollar sign) > ");
              money = sc.nextDouble();
            }
            if (money > tot) {
              change = money - tot;
              System.out.printf("Your change is: $%.2f\n", change);
              System.out.println("Thanks for shopping!");
            }
            else if (money == tot) {
              System.out.println("Exact amount received!");
              System.out.println("Thanks for shopping!");
            }

            System.out.print("More customers in line? (1 = Yes, 2 = No) ");

          } // end of - discount ones

        }// n == 1 if loop

        else if (n == 2){
          System.out.println("No more customers! Closing!");
          break;}
        }


      }

  }
