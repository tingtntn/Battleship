package com.example.battleship.ship;

import com.example.battleship.Coordinate;
import java.util.List;
import java.util.Map;

/**
 * A builder manager that manages all the specific ship builder classes.
 */
public class ShipBuilders {

    /**
     *
     * @return a builder for Destroyer
     */
    public static DestroyerBuilder newDestroyer() {
        return new DestroyerBuilder();
    }

    /**
     *
     * @return a builder for Submarine
     */
    public static SubmarineBuilder newSubmarine() {
        return new SubmarineBuilder();
    }

    /**
     *
     * @return a builder for Battleship
     */
    public static BattleshipBuilder newBattleship() {
        return new BattleshipBuilder();
    }

    /**
     *
     * @return a builder for Carrier
     */
    public static CarrierBuilder newCarrier() {
        return new CarrierBuilder();
    }

    /**
     * A builder that overrides the internalBuild for Destroyer.
     * With further development, it can have its own actions, which is specific to Destroyer.
     */
    public static class DestroyerBuilder extends AbstractShipBuilder<DestroyerBuilder, Destroyer> {
        @Override
        protected Destroyer internalBuild() {
            return new Destroyer(this);
        }
    }

    /**
     * A builder that overrides the internalBuild for Submarine.
     * With further development, it can have its own actions, which is specific to Submarine.
     */
    public static class SubmarineBuilder extends AbstractShipBuilder<SubmarineBuilder, Submarine> {
        @Override
        protected Submarine internalBuild() {
            return new Submarine(this);
        }
    }

    /**
     * A builder that overrides the internalBuild for Battleship.
     * With further development, it can have its own actions, which is specific to Battleship.
     */
    public static class BattleshipBuilder extends AbstractShipBuilder<BattleshipBuilder, Battleship> {
        @Override
        protected Battleship internalBuild() {
            return new Battleship(this);
        }
    }

    /**
     * A builder that overrides the internalBuild for Carrier.
     * With further development, it can have its own actions, which is specific to Carrier.
     */
    public static class CarrierBuilder extends AbstractShipBuilder<CarrierBuilder, Carrier> {
        @Override
        protected Carrier internalBuild() {
            return new Carrier(this);
        }
    }

    /**
     * Abstract class for specific builder class. It defines basic attributes a ships should have.
     * @param <SELF> specific type of ship builder
     * @param <TTarget> specific type of ship
     *
     * @see DestroyerBuilder
     * @see SubmarineBuilder
     * @see BattleshipBuilder
     * @see CarrierBuilder
     */
    abstract static class AbstractShipBuilder<SELF extends ShipBuilder<SELF, TTarget>,
            TTarget extends Ship> implements ShipBuilder<SELF, TTarget> {

        private int length;
        private ShipType type;
        private char player;
        private List<Coordinate> positions;
        private Map<Coordinate, Boolean> coordinateToStatus;
        private int lives;

        @Override
        public SELF setLength(int length) {
            this.length = length;
            return self();
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public SELF setType(ShipType type) {
            this.type = type;
            return self();
        }

        @Override
        public ShipType getType() {
            return this.type;
        }

        @Override
        public SELF setPlayer(char player) {
            this.player = player;
            return self();
        }

        @Override
        public char getPlayer() {
            return this.player;
        }

        @Override
        public SELF setPositions(List<Coordinate> positions) {
            this.positions = positions;
            return self();
        }

        @Override
        public List<Coordinate> getPositions() {
            return this.positions;
        }

        @Override
        public SELF setLives(int lives) {
            this.lives = lives;
            return self();
        }

        @Override
        public int getLives() {
            return this.lives;
        }

        @Override
        public SELF setCoordinateToStatus(Map<Coordinate, Boolean> coordinateToStatus) {
            this.coordinateToStatus = coordinateToStatus;
            return self();
        }

        @Override
        public Map<Coordinate, Boolean> getCoordinateToStatus() {
            return this.coordinateToStatus;
        }

        /**
         * Builds a specific type of ship.
         * @return a specific type of ship
         */
        @Override
        public TTarget build() {
            return internalBuild();
        }

        /**
         * Builds a specific type of ship. Needed to override in a specific builder class.
         * @return a specific type of ship
         */
        protected abstract TTarget internalBuild();

        /**
         *
         * @return a specific builder class
         */
        private SELF self() {
            return (SELF) this;
        }
    }

}
