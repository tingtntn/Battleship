package com.example.battleship.ship;

public class Carrier extends Ship {
    public static final int LENGTH = 5;
    public static final int LIVES = 5;
    public static final ShipType TYPE = ShipType.CARRIER;
    public Carrier(ShipBuilders.CarrierBuilder builder) {
        super(builder);
    }
}
