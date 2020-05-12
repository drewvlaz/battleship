/* Drew Vlasnik
Pd 3
This class will represent the board for Battleship */

import java.util.Scanner;

public class Board {
    // Instance variables
    private char[][] board = new char[10][10];
    private Ship[] ships = new Ship[5];

    // Constructor
    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '~';
            }
        }
    }

    // Accessors
    public char[][] getBoard() {
        return board;
    }

    public Ship[] getShips() {
        return ships;
    }

    /* Fill the board with ships using coordinates from user */
    public void populate(Scanner sc) {
        int[] lengths = {5, 4, 3, 3, 2};
        String[] shipNames = {"Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"};

        for (int i = 0; i < ships.length; i++) {
            do {
                // Display ship name and length to be placed next
                System.out.println("\nShip: " + shipNames[i] + "\nLength: " + lengths[i]);

                // Get starting coordinate
                System.out.print("Enter its starting coordinate (Ex. A7): ");
                String coordinate = validateCoordinate(sc.nextLine().toUpperCase());

                // Get direction
                System.out.print("Enter the direction of the ship - (H)orizontal or (V)ertical: ");
                char direction = validateDirection(sc.nextLine());

                // Create ship
                ships[i] = new Ship(shipNames[i], lengths[i], coordinate, direction);

                // Ensure ship doesn't overlap or go off board
                if (!validLocation()) {
                    System.out.println("\n\tERROR - Not a valid ship location!\n");
                }
            } while (!validLocation());
            
            // Mark location on board
            for (int[] coordinatePair : ships[i].getCoordinates()) {
                board[coordinatePair[1]][coordinatePair[0]] = '#';
            }

            // Display updated board
            System.out.println(this.toString() + "\n");
        }
    }

    /* Fill the board randomly with ships */
    public void populate() {
        int[] lengths = {5, 4, 3, 3, 2};
        String[] shipNames = {"Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"};
        String alphabet = "ABCDEFGHIJ";

        for (int i = 0; i < ships.length; i++) {
            do {
                // Randomly assign starting coordinate and direction
                String coordinate = "";
                coordinate += alphabet.charAt((int)(Math.random() * alphabet.length()));
                coordinate += (new Integer((int)(Math.random() * 10) + 1)).toString();
                char direction = (int)(Math.random() * 2) == 0 ? 'H' : 'V';

                // Create ship
                ships[i] = new Ship(shipNames[i], lengths[i], coordinate, direction);

            } while (!validLocation());

            // Mark location on board
            for (int[] coordinatePair : ships[i].getCoordinates()) {
                board[coordinatePair[1]][coordinatePair[0]] = '#';
            }
        }
    }

    /* Check if there is a ship at coordinates and update board 
    @param letter-number coordinates
    @return boolean of ship */
    public boolean hit(String coordinate) {
        coordinate = validateHit(coordinate);

        for (Ship s : ships) {
            for (int[] coordinatePair : s.getCoordinates()) {
                if (pairToCoordinate(coordinatePair).equalsIgnoreCase(coordinate)) {
                    // Hit ship and update board
                    s.hit(coordinate);
                    board[coordinatePair[1]][coordinatePair[0]] = 'X';

                    // Display hit
                    System.out.print("HIT at " + coordinate + "!");
                    if (s.isSunk()) {
                        System.out.print(" - " + s.getName() + " has been SUNK!");
                    }
                    System.out.println();

                    return true;
                }
            }
        }

        // Display miss
        System.out.println("MISS at " + coordinate + "!");
        int[] hit = coordinateToPair(coordinate);
        board[hit[1]][hit[0]] = 'O';

        return false;
    }

    /* Format for print statement
    @return formatted board as string */
    public String toString() {
        String formattedBoard = "    ";

        // Letter row
        for (int i = 0; i < board.length; i++) {
            formattedBoard += (char)(i + 65) + " ";
        }
        formattedBoard.trim();

        // Numbered grid
        for (int i = 0; i < board.length; i++) {
            // Add number with special formatting for 10
            String number = (new Integer(i + 1)).toString();
            formattedBoard += "\n" + ((i + 1) < 10 ? " " : "") + number + "  ";

            // Add board
            for (int j = 0; j < board[i].length; j++) {
                formattedBoard += board[i][j] + " ";
            }
            formattedBoard.trim();
        }

        return formattedBoard;
    }

    /* Translate letter-number coordinate to coordinate pair
    @param letter-number coordinate
    @return coordinate pair */
    public static int[] coordinateToPair(String coordinate) {
        int[] coordinatePair = new int[2];
        String alphabet = "ABCDEFGHIJ";

        coordinatePair[0] = alphabet.indexOf(coordinate.toUpperCase().charAt(0));
        coordinatePair[1] = Integer.parseInt(coordinate.substring(1)) - 1;

        return coordinatePair;
    }

    /* Translate coordinate pair to letter-number coordinate
    @param coordinate pair
    @return letter-number coordinate */
    public static String pairToCoordinate(int[] coordinatePair) {
        String coordinate = "";
        String alphabet = "ABCDEFGHIJ";

        coordinate += alphabet.charAt(coordinatePair[0]);
        coordinate += (new Integer(coordinatePair[1] + 1));

        return coordinate;
    }

    /* Validate the ship location from player
    @param player input
    @return validated location */
    private boolean validLocation() {
        int[][] coordinateCount = new int[10][10];

        for (Ship s : ships) {
            if (s != null) {
                for (int[] coordinatePair : s.getCoordinates()) {
                    for (int c : coordinatePair) {
                        if (c > 9) {
                            return false;
                        }
                    }
                    coordinateCount[coordinatePair[0]][coordinatePair[1]]++;
                }
            }
        }

        for (int[] coordinatePair : coordinateCount) {
            for (int c : coordinatePair) {
                if (c > 1) {
                    return false;
                }
            }
        }

        return true;
    }

    /* Validate the hit from player and make sure it wasn't already guessed
    @param player input
    @return validated coordinate */
    private String validateHit(String input) {
        Scanner sc = new Scanner(System.in);
        boolean newGuess = false;
        input = validateCoordinate(input);

        while (!newGuess) {
            int[] coordinatePair = coordinateToPair(input);
            if (board[coordinatePair[1]][coordinatePair[0]] == '~' || board[coordinatePair[1]][coordinatePair[0]]  == '#') {
                newGuess = true;
            }
            else {
                System.out.print("\n\tYou already guessed that, enter a new guess: ");
                input = validateCoordinate(sc.nextLine());
            }
        }

        return input;
    }

    /* Validate the coordinate from player
    @param player input
    @return validated coordinate */
    private String validateCoordinate(String input) {
        Scanner sc= new Scanner(System.in);
        String alphabet = "ABCDEFGHIJ";
        boolean valid = false;

        while (!valid) {
            // Try catch block for parsing integer from user input
            try {
                if (alphabet.indexOf(input.toUpperCase().charAt(0)) < 0 || Integer.parseInt(input.substring(1)) > 10) {
                    System.out.print("\n\tERROR - Enter a valid coordinate: ");
                    input = sc.nextLine().toUpperCase().trim();
                }
                else {
                    valid = true;
                }
            }
            catch (Exception e) {
                System.out.print("\n\tERROR - Enter a valid coordinate: ");
                input = sc.nextLine().toUpperCase().trim();
            }
        }

        // Spacing
        System.out.println();

        return input.toUpperCase().trim();
    }

    /* Validate the direction
    @param player input
    @return validated direction */
    private char validateDirection(String input) {
        Scanner sc= new Scanner(System.in);
        char direction = input.toUpperCase().charAt(0);

        while (direction != 'H' && direction != 'V') {
            System.out.print("\n\tERROR - Enter a valid direction: ");
            direction = sc.nextLine().toUpperCase().charAt(0);
        }

        // Spacing
        System.out.println();

        return direction;
    }

}