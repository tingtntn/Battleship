package com.example.battleship.ship;

public class Submarine extends Ship {
    public static final int LENGTH = 3;
    public static final int LIVES = 3;
    public static final ShipType TYPE = ShipType.SUBMARINE;
    public Submarine(ShipBuilders.SubmarineBuilder builder) {
        super(builder);
    }

}
