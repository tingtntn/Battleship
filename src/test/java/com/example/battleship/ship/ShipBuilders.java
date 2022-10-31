package com.example.battleship.ship;

import com.example.battleship.Coordinate;
import java.util.List;
import java.util.Map;

public class ShipBuilders {

    public static BattleshipBuilder newBattleship() {
        return new BattleshipBuilder();
    }

    public static CarrierBuilder newCarrier() {
        return new CarrierBuilder();
    }

    public static SubmarineBuilder newSubmarine() {
        return new SubmarineBuilder();
    }

    public static DestroyerBuilder newDestroyer() {
        return new DestroyerBuilder();
    }

    public static class BattleshipBuilder extends AbstractShipBuilder<BattleshipBuilder, Battleship> {
        @Override
        protected Battleship internalBuild() {
            return new Battleship(this);
        }
    }

    public static class CarrierBuilder extends AbstractShipBuilder<CarrierBuilder, Carrier> {
        @Override
        protected Carrier internalBuild() {
            return new Carrier(this);
        }
    }

    public static class SubmarineBuilder extends AbstractShipBuilder<SubmarineBuilder, Submarine> {
        @Override
        protected Submarine internalBuild() {
            return new Submarine(this);
        }
    }

    public static class DestroyerBuilder extends AbstractShipBuilder<DestroyerBuilder, Destroyer> {
        @Override
        protected Destroyer internalBuild() {
            return new Destroyer(this);
        }
    }

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

        @Override
        public TTarget build() {
            return internalBuild();
        }

        protected abstract TTarget internalBuild();

        private SELF self() {
            return (SELF) this;
        }
    }

}
