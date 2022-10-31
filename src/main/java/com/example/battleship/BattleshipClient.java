package com.example.battleship;

import com.example.battleship.ship.*;

import java.util.*;

/**
 * The main class to run the application, where the main method locates.
 * Functions include start a new game, get computer's next move, initialize game, update game
 * status after each move, add ships, and so on.
*/
public class BattleshipClient {
    Map<Coordinate, Ship> coordinateToShip;
    Map<Character, Player> characterToPlayer;
    char[][] board;
    boolean isGameOver;
    boolean wantToPlay;

    public BattleshipClient() {
        coordinateToShip = new HashMap<>();
        characterToPlayer = new HashMap<>();
        board = new char[10][10];
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

    /** Starts a new game after a player hits Yes
     *
     * @throws InterruptedException
     */
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

    /**
     * Checks whether the game is over after a player makes a move.
     */
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

    /**
     * Returns a random move from computer.
     * @return coordinate the computer chooses to move
     * @throws InterruptedException
     */
    public Coordinate getComputerNextMove() throws InterruptedException {
        Random rn = new Random();
        int x = rn.nextInt(9);
        int y = rn.nextInt(9);

        System.out.println("It is B's turn!");
        System.out.println("Player B is thinking... Please wait");
        Thread.currentThread().sleep(2000);

        System.out.println(String.format(
                "Computer chooses to attack coordinate (%s, %s)",
                x,
                y
        ));

        Coordinate attackCoordinate = new Coordinate(x, y);
        return attackCoordinate;
    }

    /** Registers the attack after a player chooses where to attack. It shows indication whether a player
     * hits a ship or not. Additionally, it updates the ship status after it is sunk.
     *
     * @param player the character represents the player (for example, a, b...)
     * @param coordinate the coordinate a player chooses to attack
     */
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

    /**
     * Updates player's ship set after a ship is sunk.
     * @param player the character represents the player (for example, a, b...)
     * @param ship the ship that is sunk
     */
    public void updatePlayerShipsWithShipSink(char player, Ship ship) {
        char enemy = player == 'A' ? 'B' : 'A';

        Set<Ship> ships = characterToPlayer.get(enemy).getShips();
        ships.remove(ship);
    }

    /**
     * Updates the map (helper variable to see whose ship a specific coordinate has) after a ship is sunk.
     * @param ship the ship that is sunk
     */
    public void updateMapWithShipSink(Ship ship) {
        List<Coordinate> positions = ship.getPositions();

        for (Coordinate coordinate : positions) {
            coordinateToShip.remove(coordinate);
        }
    }

    /**
     * Updates the board after a ship is sunk.
     * @param ship the ship that is sunk
     */
    public void updateBoardWithShipSink(Ship ship) {
        List<Coordinate> positions = ship.getPositions();

        for (Coordinate coordinate : positions) {
            board[coordinate.getRow()][coordinate.getCol()] = '.';
        }
    }

    /**
     * Checks whether the attack is really effective.
     * It is possible that a player attacks the same part of enemy's one ship multiple times.
     * @param coordinate the coordinate a player chooses to attack
     * @return true if the part of ship is not damaged. Otherwise, false.
     */
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

    /**
     * Gets input value for a coordinate from a player.
     * @param axis
     * @return value entered by the player
     */
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

    /**
     * Gets the coordinate a player chooses to attack.
     * @param player the character represents the player (for example, a, b...)
     * @return the coordinate the player chooses to attack
     */
    public Coordinate getPlayerNextMove(char player) {
        System.out.println(String.format("It is %s's turn!", player));

        System.out.print("Please enter the X coordinate: ");
        int x = getAxisInput('X');

        System.out.print("Please enter the Y coordinate: ");
        int y = getAxisInput('Y');

        System.out.println(String.format(
                "You choose to attack coordinate (%d, %d)",
                x,
                y
        ));

        return new Coordinate(x, y);
    }

    /**
     * Inquires player whether he/she wants to play a game.
     */
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

    /**
     * Initializes the whole game, including the players, ships, and board.
     */
    public void initializeGame() {
        initializePlayer();
        initializeShipsForPlayerA();
        initializeShipsForPlayerB();
        initializeBoard();
    }

    /**
     * Initializes the players.
     */
    public void initializePlayer() {
        Player A = new Player();
        Player B = new Player();
        characterToPlayer.put('A', A);
        characterToPlayer.put('B', B);
    }

    /**
     * Initializes the ships and coordinates for player A.
     */
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

    /**
     * Initializes the ships and coordinates for player B.
     */
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

    /**
     * Initializes the board (visual presentation of the game).
     */
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

    /**
     * Adds a Destroyer ship onto the board for a specific player with a set of coordinates
     * where it is located at.
     * @param player the character represents the player (for example, a, b...)
     * @param positions the coordinates the destroyer is located at
     */
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

    /**
     * Adds a Submarine ship onto the board for a specific player with a set of coordinates
     * where it is located at.
     * @param player the character represents the player (for example, a, b...)
     * @param positions the coordinates the destroyer is located at
     */
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

    /**
     * Adds a Battleship ship onto the board for a specific player with a set of coordinates
     * where it is located at.
     * @param player the character represents the player (for example, a, b...)
     * @param positions the coordinates the destroyer is located at
     */
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

    /**
     * Adds a Carrier ship onto the board for a specific player with a set of coordinates
     * where it is located at.
     * @param player the character represents the player (for example, a, b...)
     * @param positions the coordinates the destroyer is located at
     */
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

    /**
     * Prints the game opening when a player opens the game.
     */
    public void printGameOpening() {
        System.out.println("===================================");
        System.out.println();
        System.out.println("Welcome to the World of Battleship!");
        System.out.println();
        System.out.println("===================================");
        System.out.println("");
    }

    /**
     * Prints and visualizes how the game look like currently.
     */
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

    /**
     * Prints the ships set a player has.
     * @param player the character represents the player (for example, a, b...)
     */
    public void printPlayerShips(char player) {
        Set<Ship> ships = characterToPlayer.get(player).getShips();

        for (Ship ship : ships) {
            System.out.println(ship);
        }
    }
}
