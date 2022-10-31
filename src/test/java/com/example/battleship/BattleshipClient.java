package com.example.battleship;

import com.example.battleship.ship.Battleship;
import com.example.battleship.ship.Carrier;
import com.example.battleship.ship.Ship;
import com.example.battleship.ship.ShipBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BattleshipClient {
    Map<Coordinate, Ship> map = new HashMap<>();

    public static void main(String args[]) {
        BattleshipClient client = new BattleshipClient();
        client.printGameOpening();
        client.startGame();
    }

    public void startGame() {
        boolean isAnswerValid = false;

        while (!isAnswerValid) {
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();

            if (answer.toUpperCase().equals("Y")) {
                isAnswerValid = true;
                System.out.println("Game Starts!");
                initializeGame();
            } else if (answer.toUpperCase().equals("N")){
                isAnswerValid = true;
                System.out.println("See You Next Time!");
            } else {
                System.out.println("Please input a valid answer!");
            }
        }
    }

    public void initializeGame() {
        Battleship battleship = ShipBuilders.newBattleship().setLength(5).build();
        Carrier carrier = ShipBuilders.newCarrierBuilder().setLength(5).build();
    }

    public void printGameOpening() {
        System.out.println("===================================");
        System.out.println("");
        System.out.println("Welcome to the World of Battleship!");
        System.out.println("");
        System.out.println("===================================");
        System.out.println("");
        System.out.println("Are you ready to play a game? (Y/N)");
    }

    public void printBoard() {

    }
}
