package amf.project.tictactoe;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Sets a game of tic tac toe with the given parameters.
 */
public class TicTacToeGame {

    private Grid grid;
    private Scanner scanner;
    private int nbOfTurns = -1;

    /**
     * Sets up a 3x3 game of tic tac toe.
     */
    public TicTacToeGame() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Sets up a grid for tic tac toe. Based on the given param, a mxn matrix will
     * be created, and the number of attributes of the same color in a row should be
     * equal to k to win.
     * 
     * @param m width
     * @param n height
     * @param k nb of consecutive elements of the same color needed to win
     */
    public void setGrid(int m, int n, int k) {
        grid = new Grid(m, n, k);
    }

    /**
     * Starts a game of tic tac toe.
     */
    public void startGame() {
        int m, n, k, exit = 0;
        do {
            try {
                System.out.print("Choose number of rows : ");
                m = scanner.nextInt();
                System.out.print("Choose number of columns : ");
                n = scanner.nextInt();
                System.out.print("Choose number of elements aligned to win : ");
                k = scanner.nextInt();
                if (m < 0 || n < 0 || k < 0)
                    throw new InputMismatchException();
                setGrid(m, n, k);
                exit = playGrid();
            } catch (InputMismatchException e) {
                System.out.println("\n Parameters should be positive numbers.");
                scanner.nextLine();
            }
        } while (exit == 0);
        endGame();
    }

    /**
     * Ends a game.
     */
    public void endGame() {
        scanner.close();
    }

    /**
     * Plays tic tac toe on the user defined grid.
     * 
     * @return 0 if exiting the game, 1 is playing again
     */
    public int playGrid() {
        int insertedRow;
        Integer givenColumn = null;
        boolean hasWon = false;
        int exit = 0;

        do {
            try {
                nbOfTurns++;
                System.out.print("Player " + getPieceTurn() + " move : (-1 for help)");
                givenColumn = scanner.nextInt();
                insertedRow = grid.placePiece(givenColumn, getPieceTurn());
                hasWon = hasWon(insertedRow, givenColumn);
            } catch (IllegalArgumentException e) {
                nbOfTurns--;
                if (givenColumn < 0 && givenColumn > -5) {
                    exit = doChoice(Action.fromInteger(givenColumn));
                    if (exit == 1 || exit == 2)
                        return 1;
                } else {
                    System.out.println("\nMove not allowed. The column has no empty box or column is out of bound.");
                }
            } catch (InputMismatchException e) {
                nbOfTurns--;
                System.out.println("\nMove should be a column number.");
                scanner.nextLine();
            }
            grid.printCurrentGrid();
        } while (!hasWon && grid.getAvailableBox() != 0 && exit == 0);
        if (hasWon) {
            System.out.println("CONGRATULATIONS PLAYER" + getPieceTurn() + "WON!");
        } else {
            System.out.println("GAME OVER");
        }
        System.out.print("Would you like to play again? Y/N Default to No.");
        String response = scanner.next();
        if (response.equals("Y")) {
            return 0;
        }
        return 1;
    }

    /**
     * Resets the grid.
     */
    public void resetGame() {
        nbOfTurns = 0;
        setGrid(grid.getGridRowsNb(), grid.getGridRowsNb(), grid.getNbOfEltToWin());
    }

    /**
     * Start, exit, restart game or print help.
     * 
     * @param choice
     * @return exit number 0(keep the game going), 1(quit the current game) or
     *         2(stops and exit game)
     */
    public int doChoice(Action choice) {
        int exit = 0;
        switch (choice) {
        case PRINTHELP:
            printHelp();
            break;
        case START:
            exit = 1;
            break;
        case EXIT:
            exit = 2;
            break;
        case RESTART:
            resetGame();
            break;
        default:
            printMenu();
        }
        return exit;
    }

    /**
     * Prints game menu.
     */
    public void printMenu() {
        System.out.println(Action.PRINTHELP.getId() + "\t Help");
        System.out.println(Action.START.getId() + "\t Start game");
        System.out.println(Action.EXIT.getId() + "\t Stop game");
        System.out.println(Action.RESTART.getId() + "\t Restart game");
    }

    /**
     * Prints game help.
     */
    public void printHelp() {
        System.out.println("-------------------------------");
        System.out.println("Players O and X play alternatively.");
        System.out.println(
                "After \"Player move\" appears, type the column number between 0 and " + grid.getGridColumnsNb());
        System.out.println("-------------------------------");
        printMenu();
    }

    /**
     * Gets the player turn depending on the number of turns played. (Assumes there
     * is only 2 players)
     * 
     * @return player's piece
     */
    public Piece getPieceTurn() {
        Piece piece;
        if (nbOfTurns % 2 == 0) {
            piece = Piece.RED;
        } else {
            piece = Piece.BLUE;
        }
        return piece;
    }

    /**
     * Checks if a player has won the game. This method should be called after every
     * turn.
     * 
     * @param row    row where the chip was inserted
     * @param column column where the chip was inserted
     * @return whether or not a player has won a game of tic tac toe
     */
    public boolean hasWon(int row, int column) {
        boolean hasWon = false;
        if (grid.checkColumn(row, column, getPieceTurn())) {
            hasWon = true;
        } else if (grid.checkRow(row, column, getPieceTurn())) {
            hasWon = true;
        } else if (grid.checkDiagonal(row, column, getPieceTurn())) {
            hasWon = true;
        }
        return hasWon;
    }

    // TESTING PURPOSES

    public void setScanner(InputStream in) {
        this.scanner = new Scanner(in);
    }

    public int getNbOfTurns() {
        return nbOfTurns;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
