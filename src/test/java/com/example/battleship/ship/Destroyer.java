package com.example.battleship.ship;

public class Destroyer extends Ship {
    public static final int LENGTH = 2;
    public static final int LIVES = 2;
    public static final ShipType TYPE = ShipType.DESTROYER;
    public Destroyer(ShipBuilders.DestroyerBuilder builder) {
        super(builder);
    }
}
