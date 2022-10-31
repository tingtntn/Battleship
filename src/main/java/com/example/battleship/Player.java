package com.example.battleship;

import com.example.battleship.ship.Ship;

import java.util.*;

/**
 * An object that denotes an individual player.
 * Player has Information including how many ships he/she has left.
 */
public class Player {
    private Set<Ship> ships;

    public Player() {
        ships = new HashSet<>();
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player player = (Player) o;
        return Objects.equals(ships, player.ships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ships);
    }
}
