/* Drew Vlasnik
Pd 3
This class will represent a game of Battleship */

import java.util.Scanner;

public class Battleship {
    public static void main(String[] args) {
        // Declare variables
        Scanner sc = new Scanner(System.in);
        boolean rerun = false;

        // Display welcome message
        System.out.println("\n\t\tWELCOME TO BATTLESHIP\n");

        do {
            // Get name from user and create players
            System.out.print("Enter your name: ");
            Player user = new Player(UsefulMethods.validateNotEmpty(sc.nextLine()));
            Player cpu = new Player("Computer");

            // Display game directions
            System.out.print(
                "\nHOW TO PLAY: "
                + "\n\n\tBegin by entering the starting coordinate for your ship and its"
                + "\n\thorizontal or vertical orientation. The computer will automatically"
                + "\n\tplace its ships."
                + "\n\n\tNext, you will take turns guessing coordinates of each other's ships."
                + "\n\tThe first player to sink all of the other's ships is the winner."
                + "\n\n\tIcon Key:"
                + "\n\t# - Ship"
                + "\n\t~ - Water"
                + "\n\tX - Hit"
                + "\n\tO - Miss"
                + "\n\n\tPress ENTER to continue..."
            );
            sc.nextLine();

            // Display user's board
            System.out.println("\n" + user + "\n");

            // Fill each player's boards with ships
            user.getBoard().populate(sc);
            cpu.getBoard().populate();

            // Play until one player wins
            System.out.println("\nBEGIN!");
            while (user.shipsAfloat() && cpu.shipsAfloat()) {
                // Display both boards
                System.out.println("\n" + user.sideBySide(cpu));

                // User's turn
                System.out.println("\n\n" + user.getName() + "'s turn\n");
                user.takeTurn(cpu, sc);
                System.out.print(user.sideBySide(cpu) + "\n\nPress ENTER to continue...");
                sc.nextLine();

                // Computer's turn
                System.out.println("\n\n" + cpu.getName() + "'s turn");
                cpu.takeTurn(user);
                System.out.print(user.sideBySide(cpu) + "\n\nPress ENTER to continue...");
                sc.nextLine();
            }

            // Display the winner
            System.out.println("\n\n\t" + user.getWinner(cpu).getName() + " is the winner!");

            // Ask to play again
            System.out.print("\nWould you like to play again? (y/n): ");
            rerun = UsefulMethods.validateYesNo(sc.nextLine());

            // Spacing
            System.out.println("\n");

        } while (rerun);

        // Display thank you message
        System.out.println("\n\n\tTHANK YOU FOR PLAYING BATTLESHIP");
    }
}