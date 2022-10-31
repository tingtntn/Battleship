package com.example.battleship;

import com.example.battleship.ship.*;

import java.util.*;

public class BattleshipClient {
    Map<Coordinate, Ship> coordinateToShip = new HashMap<>();
    char[][] board = new char[10][10];
    boolean isGameOver = false;

    public static void main(String args[]) {
        BattleshipClient client = new BattleshipClient();
        client.printGameOpening();
        client.startGame();
        client.printBoard();

        while (!client.isGameOver) {
            Coordinate nextMoveOfA = client.getPlayerNextMove();
            client.registerAttack('A', nextMoveOfA);
            Coordinate nextMoveOfB = client.getComputerNextMove();
            client.registerAttack('B', nextMoveOfB);
        }

    }

    public Coordinate getComputerNextMove() {
        Random rn = new Random();
        int x = rn.nextInt(9);
        int y = rn.nextInt(9);

        System.out.println(String.format(
                "Computer chose to attack coordinate (%s, %s)",
                x,
                y
        ));

        Coordinate attackCoordinate = new Coordinate(x, y);
        return attackCoordinate;
    }

    public void registerAttack(char player, Coordinate coordinate) {
        Ship ship = coordinateToShip.get(coordinate);

        if (ship == null) {
            System.out.println(String.format("Unfortunately, %s miss the attack!", player));
        } else {
            if (ship.getPlayer() == player) {
                System.out.println("CAREFUL!!! This is your own ship!");
            } else {
                if (isAttackAtRightSpot(coordinate)) {
                    System.out.println(String.format("Congrats! %s's attack is successful!", player));
                    // do something
                } else {
                    System.out.println("Very close! But this part of enemy ship is already wounded!");
                }
            }
        }
    }

    public boolean isAttackAtRightSpot(Coordinate coordinate) {
        Ship ship = coordinateToShip.get(coordinate);
        Map<Coordinate, Boolean> coordinateToStatus = ship.getCoordinateToStatus();
        boolean status = coordinateToStatus.get(coordinate);

        if (status == true) {
            coordinateToStatus.put(coordinate, false);
            return true;
        } else {
            return false;
        }
    }

    public int getAxisInput(char axis) {
        Scanner scanner = new Scanner(System.in);
        boolean isInputValid = false;
        int parseInteger = 0;

        while (!isInputValid) {
            String x = scanner.nextLine();

            try {
                parseInteger = Integer.parseInt(x);
            } catch (NumberFormatException e) {
                System.out.println("Please input an input between 0 and 9");
                System.out.print(String.format("Please enter the %s coordinate: ", axis));
                continue;
            }

            if (parseInteger >= 0 && parseInteger <= 9) {
                isInputValid = true;
            } else {
                System.out.println("Please input an input between 0 and 9");
                System.out.print(String.format("Please enter the %s coordinate: ", axis));
            }
        }

        return parseInteger;
    }

    public Coordinate getPlayerNextMove() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the X coordinate: ");
        int x = getAxisInput('X');

        System.out.print("Please enter the Y coordinate: ");
        int y = getAxisInput('Y');

        System.out.println(String.format(
                "You chose to attack coordinate (%d, %d)",
                x,
                y
        ));

        return new Coordinate(x, y);
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean isInputValid = false;

        while (!isInputValid) {

            String answer = scanner.nextLine();

            if (answer.toUpperCase().equals("Y")) {
                isInputValid = true;
                System.out.println();
                System.out.println("============Game Starts============");
                initializeGame();
            } else if (answer.toUpperCase().equals("N")){
                isInputValid = true;
                System.out.println("See You Next Time!");
            } else {
                System.out.println("Please answer Y or N!");
            }
        }
    }

    public void initializeGame() {
        initializeShipsForPlayerA();
        initializeShipsForPlayerB();
        initializeBoard();
    }

    public void initializeShipsForPlayerA() {
        addDestroyer('A', new ArrayList<>(
                Arrays.asList(
                        new Coordinate(0, 0),
                        new Coordinate(0, 1)
                )
        ));

        addSubmarine('A', new ArrayList<>(
                Arrays.asList(
                        new Coordinate(3, 9),
                        new Coordinate(4, 9),
                        new Coordinate(5, 9)
                )
        ));

        addBattleship('A', new ArrayList<>(
                Arrays.asList(
                        new Coordinate(4, 5),
                        new Coordinate(5, 5),
                        new Coordinate(6, 5),
                        new Coordinate(7, 5)
                )
        ));

        addCarrier('A', new ArrayList<>(
                Arrays.asList(
                        new Coordinate(9, 0),
                        new Coordinate(9, 1),
                        new Coordinate(9, 2),
                        new Coordinate(9, 3),
                        new Coordinate(9, 4)
                )
        ));
    }

    public void initializeShipsForPlayerB() {
        addDestroyer('B', new ArrayList<>(
                Arrays.asList(
                        new Coordinate(7, 7),
                        new Coordinate(7, 8)
                )
        ));

        addSubmarine('B', new ArrayList<>(
                Arrays.asList(
                        new Coordinate(5, 1),
                        new Coordinate(5, 2),
                        new Coordinate(5, 3)
                )
        ));

        addBattleship('B', new ArrayList<>(
                Arrays.asList(
                        new Coordinate(2, 6),
                        new Coordinate(2, 7),
                        new Coordinate(2, 8),
                        new Coordinate(2, 9)
                )
        ));

        addCarrier('B', new ArrayList<>(
                Arrays.asList(
                        new Coordinate(0, 2),
                        new Coordinate(1, 2),
                        new Coordinate(2, 2),
                        new Coordinate(3, 2),
                        new Coordinate(4, 2)
                )
        ));
    }

    public void initializeBoard() {
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }

        for (Map.Entry<Coordinate, Ship> entry : coordinateToShip.entrySet()) {
            Coordinate currCoordinate = entry.getKey();
            Ship currShip = entry.getValue();
            board[currCoordinate.getRow()][currCoordinate.getCol()] = currShip.getPlayer();
        }
    }

    public void addDestroyer(char player, List<Coordinate> positions) {
        Map<Coordinate, Boolean> coordinateToStatus = new HashMap<>();

        for (Coordinate curr : positions) {
            coordinateToStatus.put(curr, true);
        }

        Destroyer destroyer = ShipBuilders.newDestroyer()
                .setLength(Destroyer.LENGTH)
                .setType(Destroyer.TYPE)
                .setPlayer(player)
                .setLives(Destroyer.LIVES)
                .setPositions(positions)
                .setCoordinateToStatus(coordinateToStatus)
                .build();

        for (Coordinate curr : positions) {
            coordinateToShip.put(curr, destroyer);
        }
    }

    public void addSubmarine(char player, List<Coordinate> positions) {
        Map<Coordinate, Boolean> coordinateToStatus = new HashMap<>();

        for (Coordinate curr : positions) {
            coordinateToStatus.put(curr, true);
        }

        Submarine submarine = ShipBuilders.newSubmarine()
                .setLength(Submarine.LENGTH)
                .setType(Submarine.TYPE)
                .setPlayer(player)
                .setLives(Submarine.LIVES)
                .setPositions(positions)
                .setCoordinateToStatus(coordinateToStatus)
                .build();

        for (Coordinate curr : positions) {
            coordinateToShip.put(curr, submarine);
        }
    }

    public void addBattleship(char player, List<Coordinate> positions) {
        Map<Coordinate, Boolean> coordinateToStatus = new HashMap<>();

        for (Coordinate curr : positions) {
            coordinateToStatus.put(curr, true);
        }

        Battleship battleship = ShipBuilders.newBattleship()
                .setLength(Battleship.LENGTH)
                .setType(Battleship.TYPE)
                .setPlayer(player)
                .setLives(Battleship.LIVES)
                .setPositions(positions)
                .setCoordinateToStatus(coordinateToStatus)
                .build();

        for (Coordinate curr : positions) {
            coordinateToShip.put(curr, battleship);
        }
    }

    public void addCarrier(char player, List<Coordinate> positions) {
        Map<Coordinate, Boolean> coordinateToStatus = new HashMap<>();

        for (Coordinate curr : positions) {
            coordinateToStatus.put(curr, true);
        }

        Carrier carrier = ShipBuilders.newCarrier()
                .setLength(Carrier.LENGTH)
                .setType(Carrier.TYPE)
                .setPlayer(player)
                .setLives(Carrier.LIVES)
                .setPositions(positions)
                .setCoordinateToStatus(coordinateToStatus)
                .build();

        for (Coordinate curr : positions) {
            coordinateToShip.put(curr, carrier);
        }
    }

    public void printGameOpening() {
        System.out.println("===================================");
        System.out.println();
        System.out.println("Welcome to the World of Battleship!");
        System.out.println();
        System.out.println("===================================");
        System.out.println("");
        System.out.println("Are you ready to play a game? (Y/N)");
    }

    public void printBoard() {
        System.out.println();
        System.out.print("  ");

        for (int i = 0; i < board.length; i++) {
            System.out.print(i);

            if (i != board.length - 1) {
                System.out.print(" ");
            }
        }

        System.out.println();

        for (int i = 0; i < board.length; i++) {
            System.out.print(i);
            System.out.print(" ");

            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]);

                if (j != board[0].length - 1) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }

        System.out.println();
    }
}
