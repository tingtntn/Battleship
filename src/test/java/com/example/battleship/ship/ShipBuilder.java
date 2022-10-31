package com.example.battleship.ship;

import com.example.battleship.Coordinate;

import java.util.List;
import java.util.Map;

public interface ShipBuilder<SELF extends ShipBuilder<SELF, TTarget>,
        TTarget extends Ship> {

    SELF setLength(int length);
    int getLength();

    SELF setType(String type);
    String getType();

    SELF setPlayer(String player);
    String getPlayer();

    SELF setPositions(List<Coordinate> positions);
    List<Coordinate> getPositions();

    SELF setCoordinateToStatus(Map<Coordinate, Boolean> coordinateToStatus);
    Map<Coordinate, Boolean> getCoordinateToStatus();

    TTarget build();
}
