package com.example.battleship.ship;


/**
 * A Destroyer ship object. It is one of the types of a Ship.
 * It has constants including the length, lives, and type.
 * @see Ship
 */
public class Destroyer extends Ship {
    public static final int LENGTH = 2;
    public static final int LIVES = 2;
    public static final ShipType TYPE = ShipType.DESTROYER;
    public Destroyer(ShipBuilders.DestroyerBuilder builder) {
        super(builder);
    }
}
