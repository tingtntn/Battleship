package com.example.battleship.ship;

/**
 * A Battleship ship object. It is one of the types of a Ship.
 * It has constants including the length, lives, and type.
 * @see Ship
 */
public class Battleship extends Ship {
    public static final int LENGTH = 4;
    public static final int LIVES = 4;
    public static final ShipType TYPE = ShipType.BATTLESHIP;
    public Battleship(ShipBuilders.BattleshipBuilder builder) {
        super(builder);
    }
}
