// CS0401 Assignment 2
// Due 02/22/2016
// Created by Bingjie Wang - UPitt

// This is the Card class as part of Assignment 2
// contains all of the information about a particular card

import java.util.Random;

public class Card {

  Random rng = new Random();

  int _ran = 0, _point = 0;
  String _suit;

  // Constructor - how the class is created
  public Card(int ran) {
    _ran = ran;
  }

  // not static!
  // they are associated with the OBJECT, not the class

  // Accessors - accesses and returns data
  public int getPoint() {
    switch (_ran) {
      // An Ace can be worth either 1 points or 11 points
      // whichever is better for the current hand
      case 1: _point = 11;
              break;
      // Normal non-face cards (2, 3, 4, 5, 6, 7, 8, 9)
      // They are worth as many points as the number on the card
      case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10:
              _point = _ran;
              break;
      // a J for a Jack, a Q for a Queen, a K for a King
      // Face cards (Jack, Queen, or King of any suit) are worth 10 points
      case 11: case 12: case 13:
               _point = 10;
               break;
    }
    return _point;
  }

  // toString method - very common method on Java objects
  // which returns a String representation of an object
  public String toString() {
    String strCard = "", strSuit = "";
    switch (_ran) {
      // An Ace can be worth either 1 points or 11 points
      // whichever is better for the current hand
      case 1: strCard = "A";
              break;
      // Normal non-face cards (2, 3, 4, 5, 6, 7, 8, 9)
      // They are worth as many points as the number on the card
      case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9:
              strCard = Integer.toString(_ran);
              break;
      // A 10 should be printed as T
      case 10: strCard = "T";
               break;
      // a J for a Jack, a Q for a Queen, a K for a King
      // Face cards (Jack, Queen, or King of any suit) are worth 10 points
      case 11: strCard = "J";
               break;
      case 12: strCard = "Q";
               break;
      case 13: strCard = "K";
               break;
    }
    return strCard + getSuit();
  }

  private String getSuit() {
    // spades, hearts, diamonds and clubs
    // suit does not affect the card value
    // so I am just matching them randomly here
    int r2 = rng.nextInt(4);
    String strSuit = "";
    switch (r2) {
      case 0: strSuit = "s";
              break;
      case 1: strSuit = "h";
              break;
      case 2: strSuit = "d";
              break;
      case 3: strSuit = "c";
              break;
    }

    return strSuit;
  }


}
