package com.example.battleship.ship;

import com.example.battleship.Coordinate;

import java.util.List;
import java.util.Map;

/**
 * An interface describes what functions a specific Ship builder class should have.
 * @param <SELF> specific type of ship builder
 * @param <TTarget> specific type of ship
 */
public interface ShipBuilder<SELF extends ShipBuilder<SELF, TTarget>,
        TTarget extends Ship> {

    SELF setLength(int length);
    int getLength();

    SELF setType(ShipType type);
    ShipType getType();

    SELF setPlayer(char player);
    char getPlayer();

    SELF setLives(int lives);
    int getLives();

    SELF setPositions(List<Coordinate> positions);
    List<Coordinate> getPositions();

    SELF setCoordinateToStatus(Map<Coordinate, Boolean> coordinateToStatus);
    Map<Coordinate, Boolean> getCoordinateToStatus();

    TTarget build();
}
