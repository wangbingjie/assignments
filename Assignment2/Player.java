// CS0401 Assignment 2
// Due 02/22/2016
// Created by Bingjie Wang - UPitt

// This is the Player class as part of Assignment 2
// contains all player information
// also includes accessors and mutators for three attributes (hand, won, money)
// contains methods to load information from a file and to save the information to a file

import java.util.*;
import java.io.*;

public class Player {

  int _hand = 0, _won = 0;
  double _money = 100.00;
  String _name;

  // Constructor - how the class is created
  public Player(String name) {
    _name = name;
  }

  // Accessors - accesses and returns data
  // all static - associated with the object, not the class
  public String getName() {
    return _name;
  }

  public int getHand() {
    return _hand;
  }

  public int getWon() {
    return _won;
  }

  public double getMoeny() {
    return _money;
  }

  // Mutators - modifies the state of the object
  public void setName(String newName) {
    _name = newName;
  }

  public void setHand(int newHand) {
    _hand = newHand;
  }

  public void setWon(int newWon) {
    _won = newWon;
  }

  public void setMoney(double newMoney) {
    _money = newMoney;
  }

  // - load information from a file
  // - save the information to a file
  public int writeInfo(String name) throws IOException {
    PrintWriter out = new PrintWriter(name);
    out.println(_hand);
    out.println(_won);
    out.println(_money);
    out.close();
    return 1;
  }

  public int loadHand(String name) throws IOException {
    File f = new File(name);
    Scanner scFile = new Scanner(f);
    int _hand = scFile.nextInt();
    return _hand;
  }

  public int loadWon(String name) throws IOException {
    File f = new File(name);
    Scanner scFile = new Scanner(f);
    int _hand = scFile.nextInt();
    int _won = scFile.nextInt();
    return _won;
  }
  public double loadMoney(String name) throws IOException {
    File f = new File(name);
    Scanner scFile = new Scanner(f);
    int _hand = scFile.nextInt();
    int _won = scFile.nextInt();
    double _money = scFile.nextDouble();
    return _money;
  }

}
