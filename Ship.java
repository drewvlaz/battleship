/* Drew Vlasnik
Pd 3
This class will represent a Battleship ship */

public class Ship {
    // Instance variables
    private String name;
    private int[][] coordinates;
    private int length;
    private char direction;
    private boolean[] hits;

    // Constructors
    public Ship(String name, int length, String coordinate, char direction) {
        this.name = name;
        this.length = length;
        this.direction = direction;

        coordinates = new int[length][2];
        hits = new boolean[length];

        coordinates[0] = Board.coordinateToPair(coordinate);
        fillCoordinates();
    }

    // Accessors
    public String getName() {
        return name;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public int getLength() {
        return length;
    }

    public char getDirection() {
        return direction;
    }

    public boolean[] getHits() {
        return hits;
    }

    /* Ship hit by other player */
    public void hit(String coordinate) {
        for (int i = 0; i < length; i++) {
            if (Board.pairToCoordinate(coordinates[i]).equalsIgnoreCase(coordinate)) {
                hits[i] = true;
            }
        }
    }

    /* Check if the ship is sunk
    @return boolean of sunk status */
    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) {
                return false;
            }
        }

        return true;
    }

    /* Fill the coordinates based on starting coordinate and direction of ship */
    private void fillCoordinates() {
        for (int i = 1; i < coordinates.length; i++) {
            coordinates[i][direction == 'H' ? 0 : 1] = coordinates[i - 1][direction == 'H' ? 0 : 1] + 1;
            coordinates[i][direction == 'H' ? 1 : 0] = coordinates[i - 1][direction == 'H' ? 1 : 0];
        }
    }
}