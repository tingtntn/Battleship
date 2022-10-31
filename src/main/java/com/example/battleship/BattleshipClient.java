package com.example.battleship;

import com.example.battleship.ship.*;

import java.util.*;

public class BattleshipClient {
    Map<Coordinate, Ship> coordinateToShip;
    Map<Character, Player> characterToPlayer;
    char[][] board = new char[10][10];
    boolean isGameOver;
    boolean wantToPlay;

    public BattleshipClient() {
        coordinateToShip = new HashMap<>();
        characterToPlayer = new HashMap<>();
    }

    public static void main(String[] args) throws InterruptedException {
        BattleshipClient client = new BattleshipClient();
        client.printGameOpening();
        client.inquireToPlay();

        while (client.wantToPlay) {
            client.printBoard();
            client.startGame();
        }

        Scanner scanner = new Scanner(System.in);
        scanner.close();
    }

    public void startGame() throws InterruptedException {
        isGameOver = false;

        while (!isGameOver) {
            Coordinate nextMoveOfA = getPlayerNextMove('A');
            registerAttack('A', nextMoveOfA);
            printBoard();
            checkGameStatus();

            if (isGameOver) {
                break;
            }

            Coordinate nextMoveOfB = getComputerNextMove();
            registerAttack('B', nextMoveOfB);
            printBoard();
            checkGameStatus();
        }
    }

    public void checkGameStatus() {
        if (characterToPlayer.get('A').getShips().size() == 0) {
            isGameOver = true;
            System.out.println("Player B wins the game!");
        } else if (characterToPlayer.get('B').getShips().size() == 0) {
            isGameOver = true;
            System.out.println("Player A wins the game!");
        }

        if (isGameOver) {
            Scanner scanner = new Scanner(System.in);
            inquireToPlay();
        }
    }

    public Coordinate getComputerNextMove() throws InterruptedException {
        Random rn = new Random();
        int x = rn.nextInt(9);
        int y = rn.nextInt(9);

        System.out.println("It is B's turn!");
        System.out.println("Player B is thinking... Please wait");
        Thread.currentThread().sleep(2000);

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
                    ship.setLives(ship.getLives() - 1);

                    if (ship.getLives() == 0) {
                        System.out.println(String.format("%s destroys enemy's ship!", player));
                        updateMapWithShipSink(ship);
                        updateBoardWithShipSink(ship);
                        updatePlayerShipsWithShipSink(player, ship);
                    }
                    // do something
                } else {
                    System.out.println("Very close! This part of enemy ship is already wounded!");
                }
            }
        }
    }

    public void updatePlayerShipsWithShipSink(char player, Ship ship) {
        char enemy = player == 'A' ? 'B' : 'A';

        Set<Ship> ships = characterToPlayer.get(enemy).getShips();
        ships.remove(ship);
    }

    public void updateMapWithShipSink(Ship ship) {
        List<Coordinate> positions = ship.getPositions();

        for (Coordinate coordinate : positions) {
            coordinateToShip.remove(coordinate);
        }
    }

    public void updateBoardWithShipSink(Ship ship) {
        List<Coordinate> positions = ship.getPositions();

        for (Coordinate coordinate : positions) {
            board[coordinate.getRow()][coordinate.getCol()] = '.';
        }
    }

    public boolean isAttackAtRightSpot(Coordinate coordinate) {
        Ship ship = coordinateToShip.get(coordinate);
        Map<Coordinate, Boolean> coordinateToStatus = ship.getCoordinateToStatus();
        boolean status = coordinateToStatus.get(coordinate);

        if (status) {
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

//        scanner.close();
        return parseInteger;
    }

    public Coordinate getPlayerNextMove(char player) {
        System.out.println(String.format("It is %s's turn!", player));

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

    public void inquireToPlay() {
        Scanner scanner = new Scanner(System.in);
        boolean isInputValid = false;

        System.out.println("Are you ready to play a new game? (Y/N)");

        while (!isInputValid) {

            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("Y")) {
                isInputValid = true;
                System.out.println();
                System.out.println("============Game Starts============");
                wantToPlay = true;
                initializeGame();
            } else if (answer.equalsIgnoreCase("N")){
                isInputValid = true;
                wantToPlay = false;
                System.out.println("See You Next Time!");
                return;
            } else {
                System.out.println("Please answer Y or N!");
            }
        }

//        scanner.close();
    }

    public void initializeGame() {
        initializePlayer();
        initializeShipsForPlayerA();
        initializeShipsForPlayerB();
        initializeBoard();
    }

    public void initializePlayer() {
        Player A = new Player();
        Player B = new Player();
        characterToPlayer.put('A', A);
        characterToPlayer.put('B', B);
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

        for (Coordinate coordinate : positions) {
            coordinateToStatus.put(coordinate, true);
        }

        Destroyer destroyer = ShipBuilders.newDestroyer()
                .setLength(Destroyer.LENGTH)
                .setType(Destroyer.TYPE)
                .setPlayer(player)
                .setLives(Destroyer.LIVES)
                .setPositions(positions)
                .setCoordinateToStatus(coordinateToStatus)
                .build();

        for (Coordinate coordinate : positions) {
            coordinateToShip.put(coordinate, destroyer);
        }

        characterToPlayer.get(player).getShips().add(destroyer);
    }

    public void addSubmarine(char player, List<Coordinate> positions) {
        Map<Coordinate, Boolean> coordinateToStatus = new HashMap<>();

        for (Coordinate coordinate : positions) {
            coordinateToStatus.put(coordinate, true);
        }

        Submarine submarine = ShipBuilders.newSubmarine()
                .setLength(Submarine.LENGTH)
                .setType(Submarine.TYPE)
                .setPlayer(player)
                .setLives(Submarine.LIVES)
                .setPositions(positions)
                .setCoordinateToStatus(coordinateToStatus)
                .build();

        for (Coordinate coordinate : positions) {
            coordinateToShip.put(coordinate, submarine);
        }

        characterToPlayer.get(player).getShips().add(submarine);
    }

    public void addBattleship(char player, List<Coordinate> positions) {
        Map<Coordinate, Boolean> coordinateToStatus = new HashMap<>();

        for (Coordinate coordinate : positions) {
            coordinateToStatus.put(coordinate, true);
        }

        Battleship battleship = ShipBuilders.newBattleship()
                .setLength(Battleship.LENGTH)
                .setType(Battleship.TYPE)
                .setPlayer(player)
                .setLives(Battleship.LIVES)
                .setPositions(positions)
                .setCoordinateToStatus(coordinateToStatus)
                .build();

        for (Coordinate coordinate : positions) {
            coordinateToShip.put(coordinate, battleship);
        }

        characterToPlayer.get(player).getShips().add(battleship);
    }

    public void addCarrier(char player, List<Coordinate> positions) {
        Map<Coordinate, Boolean> coordinateToStatus = new HashMap<>();

        for (Coordinate coordinate : positions) {
            coordinateToStatus.put(coordinate, true);
        }

        Carrier carrier = ShipBuilders.newCarrier()
                .setLength(Carrier.LENGTH)
                .setType(Carrier.TYPE)
                .setPlayer(player)
                .setLives(Carrier.LIVES)
                .setPositions(positions)
                .setCoordinateToStatus(coordinateToStatus)
                .build();

        for (Coordinate coordinate : positions) {
            coordinateToShip.put(coordinate, carrier);
        }

        characterToPlayer.get(player).getShips().add(carrier);
    }

    public void printGameOpening() {
        System.out.println("===================================");
        System.out.println();
        System.out.println("Welcome to the World of Battleship!");
        System.out.println();
        System.out.println("===================================");
        System.out.println("");
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

    public void printPlayerShips(char player) {
        Set<Ship> ships = characterToPlayer.get(player).getShips();

        for (Ship ship : ships) {
            System.out.println(ship);
        }
    }
}
