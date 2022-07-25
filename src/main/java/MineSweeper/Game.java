package MineSweeper;

import MineSweeper.GameBoard;
import MineSweeper.Position;

import java.util.Scanner;

public class Game {

    private final Scanner myScanner;
    private final GameBoard gameBoard;

    public Game() {
        this.myScanner = new Scanner(System.in);
        this.gameBoard = new GameBoard(10, 10);
        this.gameBoard.spawnBombs(10);
    }

    public void startGame() {

        System.out.println("Welcome to MineSweeper!");
        System.out.println("Enter your X/Y co-ordinates separated by a space");
        System.out.println("Type \"flag\" after your co-ordinates to mark a pesky bomb!");
        System.out.println("Type \"exit\" if you want to stop playing!");

        // Input flag after co-ords to flag tile.
        //Check if already placed and toggle if need be
        boolean isFlagging;
        Position inputPosition;
        do {
            this.gameBoard.printBoard();
            this.gameBoard.printStatus();
            inputPosition = this.getPositionInput();
            isFlagging = this.getStringOrExit().equalsIgnoreCase("flag");
            if (isFlagging) {
                this.gameBoard.flagTile(inputPosition);
            } else if (this.gameBoard.isTileFlagged(inputPosition)) {
                System.out.println("You need to remove the flag first!");
            } else {
                this.gameBoard.revealTile(inputPosition);
            }
        } while (!this.gameBoard.isWon() && (isFlagging || !this.gameBoard.isTileBomb(inputPosition)));

        this.gameBoard.showAll();
        this.gameBoard.printBoard();
        if (this.gameBoard.isWon()) {
            System.out.println("WINNER! You've avoided all of the bombs!!");
        } else {
            System.out.println("LOSER!! YOU DIED IN A FIREY EXPLOSION!!");
        }
    }

    public boolean validateInput(Position position) {
        if (!this.gameBoard.validPosition(position)) {
            System.out.println("This is outside the play area!");
            return false;
        } else if (this.gameBoard.isTileShown(position)) {
            System.out.println("This tile has already been selected!");
            return false;
        } else {
            return true;
        }
    }

    // Checks input for Ints & validates if valid input, looks for "exit" if not
    public Position getPositionInput() {
        Position input = new Position(0, 0);

        do {
            System.out.println("Enter the X & Y Co-ordinates separated by a space.");
            if (!this.myScanner.hasNextInt()) {
                this.getStringOrExit();
                System.out.println("Invalid X coordinate.");
            } else {
                input.x = this.myScanner.nextInt();
                if (!this.myScanner.hasNextInt()) {
                    this.getStringOrExit();
                    System.out.println("Invalid Y coordinate");
                } else {
                    input.y = this.myScanner.nextInt();
                    --input.x;
                    --input.y;
                }
            }
        } while (!this.validateInput(input));
        return input;
    }

    public String getStringOrExit() {
        String input = this.myScanner.nextLine().trim();
        if (input.equalsIgnoreCase("exit")) {
            System.out.println("Thank you for playing MineSweeper!");
            System.exit(0);
        }
        return input;

    }
}
