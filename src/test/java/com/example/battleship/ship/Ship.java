package com.example.battleship.ship;

import com.example.battleship.Coordinate;

import java.util.List;
import java.util.Map;

public abstract class Ship {
   private int length;
   private String type;
   private String player;
   private List<Coordinate> positions;
   private Map<Coordinate, Boolean> coordinateToStatus;

   Ship(ShipBuilders.AbstractShipBuilder builder) {
      this.length = builder.getLength();
      this.type = builder.getType();
      this.player = builder.getPlayer();
      this.positions = builder.getPositions();
      this.coordinateToStatus = builder.getCoordinateToStatus();
   }
}
