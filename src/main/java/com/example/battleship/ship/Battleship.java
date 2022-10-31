package com.example.battleship.ship;

public class Battleship extends Ship {
    public static final int LENGTH = 4;
    public static final int LIVES = 4;
    public static final ShipType TYPE = ShipType.BATTLESHIP;
    public Battleship(ShipBuilders.BattleshipBuilder builder) {
        super(builder);
    }
}
