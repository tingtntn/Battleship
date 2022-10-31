package com.example.battleship.ship;

/**
 * A Carrier ship object. It is one of the types of a Ship.
 * It has constants including the length, lives, and type.
 * @see Ship
 */
public class Carrier extends Ship {
    public static final int LENGTH = 5;
    public static final int LIVES = 5;
    public static final ShipType TYPE = ShipType.CARRIER;
    public Carrier(ShipBuilders.CarrierBuilder builder) {
        super(builder);
    }

}
