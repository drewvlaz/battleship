/* Drew Vlasnik
Pd 3
This class will represent a Battleship player */

import java.util.Scanner;

public class Player {
    // Instance variables
    private String name;
    private Board board;

    // Constructor
    public Player() {}
    public Player(String name) {
        this.name = name;
        board = new Board();
    }

    // Accessors
    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    // Mutators
    public void setName(String name) {
        this.name = name;
    }

    /* Guess players ships
    @param player whose ships to guess
    @return whether a ship was hit */
    public boolean takeTurn(Player p, Scanner sc) {
        System.out.print("Enter a guess (Ex. A7): ");
        return p.hitAt(sc.nextLine());
    }

    /* Guess players ships 
    @param player whose ships to guess
    @return whether a ship was hit */
    public boolean takeTurn(Player p) {
        String alphabet = "ABCDEFGHIJ";
        String coordinate = "";

        boolean newGuess = false;

        // Randomly hit
        coordinate += alphabet.charAt((int)(Math.random() * alphabet.length()));
        coordinate += (new Integer((int)(Math.random() * 10) + 1)).toString();

        // Make sure coordinate was not already guessed
        while (!newGuess) {
            int[] coordinatePair = Board.coordinateToPair(coordinate);
            if (p.board.getBoard()[coordinatePair[1]][coordinatePair[0]] == '~' || p.board.getBoard()[coordinatePair[1]][coordinatePair[0]]  == '#') {
                newGuess = true;
            }
            else {
                coordinate = "";
                coordinate += alphabet.charAt((int)(Math.random() * alphabet.length()));
                coordinate += (new Integer((int)(Math.random() * 10) + 1)).toString();
            }
        }

        return p.hitAt(coordinate);
    }

    /* Check if player still has ships */
    public boolean shipsAfloat() {
        for (Ship s : board.getShips()) {
            if (!s.isSunk()) {
                return true;
            }
        }

        return false;
    }

    /* Get the winner 
    @param other player
    @return winning player */
    public Player getWinner(Player p) {
        if (p.shipsAfloat()) {
            return p;
        }
        else if (this.shipsAfloat()) {
            return this;
        }

        return new Player();
    }

    /* Format both players' boards side by side
    @param other player
    @return side by side boards as string */
    public String sideBySide(Player p) {
        String board1 = this.toString();
        String board2 = p.asEnemy();
        String sideBySide = "";
        int i1 = 0, i2 = 0;

        for (int i = 0; board1.indexOf("\n") > 0; i++) {
            i1 = board1.indexOf("\n");
            i2 = board2.indexOf("\n");
            sideBySide += "\n\t" + board1.substring(0, i1);
            sideBySide += "\t\t" + board2.substring(0, i2);

            // Add separation for names
            if (i == 0) {
                sideBySide += "\n";
            }

            board1 = board1.substring(i1 + 1);
            board2 = board2.substring(i2 + 1);
        }

        // Add final rows
        sideBySide += "\n\t" + board1;
        sideBySide += "\t\t" + board2;

        return sideBySide;
    }

    /* Format for print statement
    @return formatted board as string */
    public String toString() {
        return UsefulMethods.center(board.toString().indexOf("\n"), name + "'s Board:") + "\n" + board.toString();
    }

    /* Format board as enemy without visible ships
    @return formatted board as string */
    public String asEnemy() {
        return this.toString().replaceAll("#", "~");
    }

    /* Get hit at a certain coordinate and update board accordingly
    @param coordinate of hit */
    private boolean hitAt(String coordinate) {
        return board.hit(coordinate);
    }
}
