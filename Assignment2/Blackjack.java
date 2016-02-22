// CS0401 Assignment 2
// Due 02/22/2016
// Created by Bingjie Wang - UPitt

// A game of infinite blackjack
// This contains the main method for the program

import java.util.*;
import java.io.*;

public class Blackjack {

    public static void main(String[] args) throws IOException {

      Random rng = new Random();
      Scoring classScore = new Scoring();
      Scanner sc = new Scanner(System.in);

      System.out.println("Welcome to Infinite Blackjack!");
      System.out.println(" ");
      System.out.print("Enter name > ");
      String name  = sc.next();
      String priName = "Name:", priHand = "Total Hands:";
      String priWon = "Hands Won:", priMoney = "Money:";

      File f = new File(name);
      String fileName;
      boolean hasFile = false;
      // get file name or directory name
      // fileName = f.getName();
      // true if the file exists
      hasFile = f.exists();
      Player objPlayer = new Player(name);
      if (!hasFile) {
        // user info does not exist
        // a new file is created with that user's info
        System.out.println("Welcome, " + name + "!");
        System.out.println("Have fun on your first time playing Infinite Blackjack.");
        // set initial values
        objPlayer._hand = 0;
        objPlayer._won = 0;
        objPlayer._money = 100.00;
        objPlayer.writeInfo(name);
      } else {
        // user info exists
        // load user's info from that file
        System.out.println("Welcome back, " + name + "!");
        System.out.println("Everything is just how you left it.");

        objPlayer._hand = objPlayer.loadHand(name);
        objPlayer._won = objPlayer.loadWon(name);
        objPlayer._money = objPlayer.loadMoney(name);
      }

      boolean endGame = false;
      while (endGame == false) {
      System.out.println("");
      System.out.printf("%-15s %15s %n", priName, name);
      System.out.printf("%-15s %15d %n", priHand, objPlayer._hand);
      System.out.printf("%-15s %15d %n", priWon, objPlayer._won);
      System.out.printf("%-15s %15.2f %n", priMoney, objPlayer._money);
      System.out.println("");

      System.out.print("Play a hand? (Y/N) > ");
      while (!sc.hasNext("Y") && !sc.hasNext("N") && !sc.hasNext("y") && !sc.hasNext("n")) {
        System.out.print("Play a hand? (Y/N) > ");
        sc.next(); // IMPORTANT!!
      }
      String choice  = sc.next();
      if (choice.equalsIgnoreCase("Y")) {
        objPlayer._hand ++;
        int numAces = 0, numAcesDealer = 0;
        int points = 0, pointsDealer = 0;

        System.out.println("");
        System.out.print("Enter amount to bet > ");

        // This can be anywhere from $0.01 to the amount of money they have on hand.
        // If they try to bet a negative amount or more than they have on hand,
        // the system should not allow them to do so, and ask for a valid amount.
        // also handles invalid data input type
        double betAmount = 0.0;
        boolean incorrectInput = true;
        while (incorrectInput) {
          if (sc.hasNextDouble()) {
            double betAmount_if = sc.nextDouble();
            if (betAmount_if < 0.01 || betAmount_if > objPlayer._money) {
              System.out.println("Please enter a valid value, from 0.01 to " + objPlayer._money);
              System.out.println(" ");
              System.out.print("Enter amount to bet > ");
            } else {
              incorrectInput = false;
            }
            // get local variable betAmount_if outside if-loop
            betAmount = betAmount_if;
          } else {
            sc.next();
            System.out.println("Please enter a value in valid format (e.g. 4.50).");
            System.out.println(" ");
            System.out.print("Enter amount to bet > ");
          }
        }

        System.out.println("PLAYER DEAL");
        int r = rng.nextInt(13); // 0-12
        r += 1; // 1-13 -> value of the card
        Card c = new Card(r);
        //System.out.println(c.getPoint());
        System.out.print(c.toString());

        // get number of Aces
        if (r == 1) {
          numAces += 1;
        }

        int r2 = rng.nextInt(13); // 0-12
        r2 += 1; // 1-13 -> value of the card
        Card c2 = new Card(r2);
        //System.out.println(c2.getPoint());
        System.out.println(" " + c2.toString());

        // get number of Aces
        if (r2 == 1) {
          numAces += 1;
        }
        points = c.getPoint()+c2.getPoint();
        int score = classScore.getScore(points, numAces);
        System.out.println("Score: " + score);

        // The player has a Blackjack (exactly 21 points from only two cards)
        // The player receives all of their money back, plus 1.5 times their bet.
        boolean blackjack = false;
        if (score == 21) {
          blackjack = true;
        }

        // Dealer's first deal
        r = rng.nextInt(13); // 0-12
        r += 1; // 1-13 -> value of the card
        Card cDealer = new Card(r);

        // get number of Aces
        if (r == 1) {
          numAcesDealer += 1;
        }

        r2 = rng.nextInt(13); // 0-12
        r2 += 1; // 1-13 -> value of the card
        Card cDealer2 = new Card(r2);
        //System.out.println(c2.getPoint());
        //System.out.println(" " + cDealer2.toString());

        // get number of Aces
        if (r2 == 1) {
          numAcesDealer += 1;
        }
        pointsDealer = cDealer.getPoint()+cDealer2.getPoint();
        int scoreDealer = classScore.getScore(pointsDealer, numAcesDealer);

        // The house player will follow a simple set of rules:
        // If the score is greater than 21, the house loses
        // Otherwise, if the score is greater than or equal to 18 but
        // less than or equal to 21, the house will stay
        // If the score is exactly 17, and at least one card is an ace
        // (a "soft" 17), the house will hit.
        // If no cards are aces (a "hard" 17), the house will stay.
        // Otherwise, the house will hit

        boolean houseStay = false;
        if (scoreDealer > 21) {
          houseStay = true;
          System.out.println("Dealer busted!");
        } else if (scoreDealer >= 18 && scoreDealer <=21 ) {
          houseStay = true;
        } else if (scoreDealer == 17 && numAcesDealer >= 1) {
          houseStay = false;
        } else if (scoreDealer == 17 && numAcesDealer == 0) {
          houseStay = true;
        } else {
          houseStay = false;
        }

        boolean keepDeal = true;
        while (keepDeal) {
          System.out.print("[H]it or [S]tay? > ");
          while (!sc.hasNext("H") && !sc.hasNext("h")
          && !sc.hasNext("Hit") && !sc.hasNext("hit")
          && !sc.hasNext("S") && !sc.hasNext("s")
          && !sc.hasNext("Stay") && !sc.hasNext("stay")) {
            System.out.print("[H]it or [S]tay? > ");
            sc.next();
          }
          String action = sc.next();
          // "hit" - accept another card
          if (action.equalsIgnoreCase("H") || action.equalsIgnoreCase("Hit")) {
            keepDeal = true;
            int r3 = rng.nextInt(13); // 0-12
            r3 += 1; // 1-13 -> value of the card
            Card cHit = new Card(r3);
            if (r3 == 1) {
              numAces += 1;
            }
            points += cHit.getPoint();
            score = classScore.getScore(points, numAces);
            System.out.println("Card dealt: " + cHit.toString());
            System.out.println("Score: " + score);

            if (score > 21) {
              keepDeal = false;
            } else {
              keepDeal = true;
            }

            // Dealer deal
            if (houseStay == false && score <= 21) {
              int r4 = rng.nextInt(13); // 0-12
              r4 += 1; // 1-13 -> value of the card
              Card cDealerHit = new Card(r4);

              if (r4 == 1) {
                numAcesDealer += 1;
              }

              pointsDealer += cDealerHit.getPoint();
              scoreDealer = classScore.getScore(pointsDealer, numAcesDealer);

              if (score <= 21 && scoreDealer > 21) {
                keepDeal = false;
                houseStay = true;
              } else if (score <= 21 && scoreDealer >= 18 && scoreDealer <= 21 ) {
                houseStay = true;
              } else if ( score <= 21 && scoreDealer == 17 && numAcesDealer >= 1) {
                houseStay = false;
                System.out.println("Hit!");
              } else if (score <= 21 && scoreDealer == 17 && numAcesDealer == 0) {
                houseStay = true;
              } else if (score <= 21){
                houseStay = false;
                System.out.println("Hit!");
              }
            } else {// if (houseStay == false) loop
              if (score < 21) {
              System.out.println("Stay!");
            }
          }

        } else {
          // player stay
          keepDeal = false;
          // Once the player stays, the dealer will turn over their cards.
          System.out.println("Dealer score: " + scoreDealer);
        }
      } // while (keepDeal) loop

      // Now get the game results
      // At the end of each hand, the amount of money won or lost,
      // along with the result, should be displayed
      if (blackjack == true) {
        // The player has a Blackjack (exactly 21 points from only two cards)
        // The player receives all of their money back, plus 1.5 times their bet.
        System.out.println("The player has a blackjack!");
        objPlayer._money = objPlayer._money + betAmount * 1.5;
        System.out.println("You won $" + (betAmount*1.5) + "!");
      } else if (score == scoreDealer) {
        // the player does not lose or gain any money
        System.out.println("Push!");
      } else if (score > scoreDealer && score <= 21 ) {
        // The player receives all of their money back,
        // plus wins that exact same amount they bet.
        objPlayer._money = objPlayer._money + betAmount;
        System.out.println("Player won!");
        System.out.println("You won $" + betAmount + "!");
      } else if (score < scoreDealer && scoreDealer <= 21 ){
        // score < scoreDealer
        objPlayer._money = objPlayer._money - betAmount;
        System.out.println("Dealer won!");
        System.out.println("You lost $" + betAmount + "!");
      } else if (scoreDealer > 21) {
        System.out.println("Dealer busted!");
        System.out.println("Player won!");
        System.out.println("You won $" + betAmount + "!");
      } else if (score > 21) {
        System.out.println("Player busted!");
        objPlayer._money = objPlayer._money - betAmount;
        System.out.println("Dealer won!");
        System.out.println("You lost $" + betAmount + "!");
      }
      endGame = false;

        if (objPlayer._money == 0) {
          System.out.println("You have no more money to bet!");
          endGame = true;
          objPlayer.writeInfo(name);
          System.out.println("Thank you for playing Infinite Blackjack!");
        }
      } // choice = y
      else if (choice.equalsIgnoreCase("N")) {
        endGame = true;
        // save player's information to a file
        objPlayer.writeInfo(name);
        System.out.println("Thank you for playing Infinite Blackjack!");
      }
    }// while endGame

  }
}
