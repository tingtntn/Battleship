package com.example.battleship.ship;

import com.example.battleship.Coordinate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Ship {
   private int length;
   private ShipType type;
   private char player;
   private List<Coordinate> positions;
   private Map<Coordinate, Boolean> coordinateToStatus;
   private int lives;

   Ship(ShipBuilders.AbstractShipBuilder builder) {
      this.length = builder.getLength();
      this.type = builder.getType();
      this.player = builder.getPlayer();
      this.lives = builder.getLives();
      this.positions = builder.getPositions();
      this.coordinateToStatus = builder.getCoordinateToStatus();
   }

   public int getLength() {
      return length;
   }

   public void setLength(int length) {
      this.length = length;
   }

   public ShipType getType() {
      return type;
   }

   public void setType(ShipType type) {
      this.type = type;
   }

   public char getPlayer() {
      return player;
   }

   public void setPlayer(char player) {
      this.player = player;
   }

   public List<Coordinate> getPositions() {
      return positions;
   }

   public void setPositions(List<Coordinate> positions) {
      this.positions = positions;
   }

   public Map<Coordinate, Boolean> getCoordinateToStatus() {
      return coordinateToStatus;
   }

   public void setCoordinateToStatus(Map<Coordinate, Boolean> coordinateToStatus) {
      this.coordinateToStatus = coordinateToStatus;
   }

   public int getLives() {
      return lives;
   }

   public void setLives(int lives) {
      this.lives = lives;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Ship ship = (Ship) o;
      System.out.println(length == ship.length);
      System.out.println("hellohellhoehoeoheoho");
      return length == ship.length && player == ship.player && type == ship.type && Objects.equals(positions, ship.positions);
   }

   @Override
   public int hashCode() {
      return Objects.hash(length, type, player, positions);
   }

   @Override
   public String toString() {
      return "Ship{" +
              "length=" + length +
              ", type=" + type +
              ", player=" + player +
              ", positions=" + positions +
              ", coordinateToStatus=" + coordinateToStatus +
              ", lives=" + lives +
              '}';
   }


}
