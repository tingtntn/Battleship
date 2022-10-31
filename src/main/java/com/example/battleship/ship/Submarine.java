package com.example.battleship.ship;

/**
 * A Submarine ship object. It is one of the types of a Ship.
 * It has constants including the length, lives, and type.
 * @see Ship
 */
public class Submarine extends Ship {
    public static final int LENGTH = 3;
    public static final int LIVES = 3;
    public static final ShipType TYPE = ShipType.SUBMARINE;
    public Submarine(ShipBuilders.SubmarineBuilder builder) {
        super(builder);
    }

}
