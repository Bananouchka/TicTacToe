package amf.project.tictactoe;

import java.util.List;

/**
 * Class used to create the grid to play a game of tic tac toe.
 *
 */
public class Grid {

    private Piece[][] grid;
    private int nbOfEltToWin = 0;
    private int availableBox = 0;

    /**
     * Creates a 3x3x3 grid
     * 
     */
    public Grid() {
        this(3, 3, 3);
    }

    /**
     * Creates a grid with the given values;
     * 
     * @param m number of rows
     * @param n number of columns
     * @param k number of element aligned to win the game
     */
    public Grid(int m, int n, int k) {
        grid = new Piece[m][n];
        availableBox = m * n;
        nbOfEltToWin = k;
        initGrid();
    }

    /*
     * Initializes the platform with empty values.
     */
    public void initGrid() {
        for (int i = 0; i < getGridRowsNb(); i++) {
            for (int j = 0; j < getGridColumnsNb(); j++) {
                grid[i][j] = Piece.EMPTY;
            }
        }
    }

    /**
     * Places a piece into the first available spot.
     * 
     * @param column column the piece will be placed on
     * @param piece  piece that has to be placed
     * @return true if inserted
     * @throws IllegalArgumentException
     */
    public boolean placePiece(int row, int column, Piece piece) throws IllegalArgumentException {
        if (row >= getGridRowsNb() || row < 0 
        		|| column >= getGridColumnsNb() || column < 0 
        		|| piece == null || piece == Piece.EMPTY
                || grid[row][column] != Piece.EMPTY)
            throw new IllegalArgumentException();
        
        grid[row][column] = piece;
        availableBox--;
        return true;
    }

    /**
     * Prints the status of the current grid.
     */
    public void printCurrentGrid() {
        System.out.print("|");
        for (int j = 0; j < getGridColumnsNb(); j++) {
            System.out.print("---");
        }
        System.out.println();
        for (int i = 0; i < getGridRowsNb(); i++) {
            for (int j = 0; j < getGridColumnsNb(); j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        for (int j = 0; j < getGridColumnsNb(); j++) {
            System.out.print("---");
        }
        System.out.print("|");
        System.out.println();
    }


    /**
     * Check if column has k continuous element of the same piece.
     * 
     * @param row
     * @param column
     * @param piece
     * @return true or false
     */
    public boolean checkColumn(int row, int column, Piece piece) {
        int i = row, j = row + 1;
        return check(row, column, i, j, Direction.COLUMN, piece);
    }
    
    
    /**
     * Helper method taking into account direction of the checks, horizontal, vertical or diagonal.
     * 
     * @param row
     * @param columns
     * @param i
     * @param j
     * @param direction
     * @param piece
     * @return
     */
    public boolean check(int row, int column, int i, int j, Direction direction, Piece piece) {
        int count = 0;
        Position position = new Position(row, column, i, j, direction);
        List<Condition> conditions = position.getConditions(getGridRowsNb(), getGridColumnsNb());
        boolean keepGoing = conditions.get(0).isValid();
        for (int m = 1; m < conditions.size(); m++) {
            keepGoing = keepGoing || conditions.get(m).isValid();
        }
   
        while (keepGoing && count < nbOfEltToWin) {
            for (Condition condition: conditions) {
                if (condition.isValid()
                        && grid[condition.getI()][condition.getJ()] == piece) 
                    count++;
            }
            position.incOrDec();
            conditions = position.getConditions(getGridRowsNb(), getGridColumnsNb());
            keepGoing = conditions.get(0).isValid();
            for (int m = 1; m < conditions.size(); m++) {
                keepGoing = keepGoing || conditions.get(m).isValid();
            }
        }
        return count >= nbOfEltToWin;
    }
    

    /**
     * Check if row has k continuous element of the same piece.
     * 
     * @param row
     * @param column
     * @param piece
     * @return true or false
     */
    public boolean checkRow(int row, int column, Piece piece) {
        int i = column, j = column + 1;
        return check(row, column, i, j, Direction.ROW, piece);
    }


    /**
     * Check if diagonals have k continuous element of the same piece.
     * 
     * @param row
     * @param column
     * @param piece
     * @return true or false
     */
    public boolean checkDiagonal(int row, int column, Piece piece) {
        int i = row, j = row + 1;
        if(check(row, column, i, j, Direction.DIAGONALONE, piece)) return true;

        i = row;
        j = row - 1;
        return check(row, column, i, j, Direction.DIAGONALTWO, piece);
    }

    // --- Getters

    public Piece[][] getGrid() {
        return grid;
    }

    public int getGridColumnsNb() {
        if (grid == null)
            return 0;
        return grid[0].length;
    }

    public int getGridRowsNb() {
        if (grid == null)
            return 0;
        return grid.length;
    }

    public int getAvailableBox() {
        return availableBox;
    }

    public int getNbOfEltToWin() {
        return nbOfEltToWin;
    }

}
