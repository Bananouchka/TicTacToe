package amf.project.tictactoe;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GridTest {

    private ByteArrayOutputStream expectedOuput;
    private Grid grid;

    @Before
    public void init() {
        grid = Mockito.spy(new Grid(4, 4, 3));
        expectedOuput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(expectedOuput));
    }

    @After
    public void finalize() throws IOException {
        expectedOuput.flush();
    }

    @Test
    public void initGridTest() {
        grid.initGrid();
        for (int i = 0; i < grid.getGridRowsNb(); i++) {
            for (int j = 0; j < grid.getGridColumnsNb(); j++) {
                assertEquals(Piece.EMPTY, grid.getGrid()[i][j]);
            }
        }
    }

    @Test
    public void placePieceTest() {
        assertEquals(3, grid.placePiece(0, Piece.RED));
        assertEquals(2, grid.placePiece(0, Piece.RED));
        assertEquals(1, grid.placePiece(0, Piece.RED));
        assertEquals(3, grid.placePiece(1, Piece.BLUE));
        assertEquals(3, grid.placePiece(3, Piece.BLUE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void placePieceOutBoundExceptionTest() {
        grid.placePiece(12, Piece.RED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placePieceExceptionTest() {
        grid.placePiece(0, Piece.RED);
        grid.placePiece(0, Piece.RED);
        grid.placePiece(0, Piece.RED);
        grid.placePiece(0, Piece.RED);
        grid.placePiece(0, Piece.RED);
    }

    @Test
    public void checkColumnTest() {
        grid.placePiece(0, Piece.RED);
        grid.placePiece(0, Piece.RED);
        grid.placePiece(0, Piece.RED);

        assertEquals(true, grid.checkColumn(1, 0, Piece.RED));
        assertEquals(true, grid.checkColumn(2, 0, Piece.RED));
        assertEquals(true, grid.checkColumn(3, 0, Piece.RED));

        assertEquals(false, grid.checkColumn(0, 1, Piece.RED));
        assertEquals(false, grid.checkColumn(0, 2, Piece.RED));
        assertEquals(false, grid.checkColumn(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(2, 0, Piece.BLUE));

        assertEquals(false, grid.checkRow(0, 1, Piece.RED));
        assertEquals(false, grid.checkRow(0, 2, Piece.RED));
        assertEquals(false, grid.checkRow(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(2, 0, Piece.BLUE));

        assertEquals(false, grid.checkDiagonal(0, 1, Piece.RED));
        assertEquals(false, grid.checkDiagonal(0, 2, Piece.RED));
        assertEquals(false, grid.checkDiagonal(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(2, 0, Piece.BLUE));
    }

    @Test
    public void checkRowTest() {
        grid.placePiece(0, Piece.RED);
        grid.placePiece(1, Piece.RED);
        grid.placePiece(2, Piece.RED);
        assertEquals(true, grid.checkRow(3, 0, Piece.RED));
        assertEquals(true, grid.checkRow(3, 1, Piece.RED));
        assertEquals(true, grid.checkRow(3, 2, Piece.RED));

        assertEquals(false, grid.checkRow(0, 1, Piece.RED));
        assertEquals(false, grid.checkRow(0, 2, Piece.RED));
        assertEquals(false, grid.checkRow(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(2, 0, Piece.BLUE));

        assertEquals(false, grid.checkColumn(0, 1, Piece.RED));
        assertEquals(false, grid.checkColumn(0, 2, Piece.RED));
        assertEquals(false, grid.checkColumn(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(2, 0, Piece.BLUE));

        assertEquals(false, grid.checkDiagonal(0, 1, Piece.RED));
        assertEquals(false, grid.checkDiagonal(0, 2, Piece.RED));
        assertEquals(false, grid.checkDiagonal(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(2, 0, Piece.BLUE));
    }

    @Test
    public void checkDiagonalOneTest() {
        grid.placePiece(0, Piece.RED);
        grid.placePiece(0, Piece.BLUE);
        grid.placePiece(0, Piece.RED);
        grid.placePiece(1, Piece.BLUE);
        grid.placePiece(1, Piece.RED);
        grid.placePiece(1, Piece.BLUE);
        grid.placePiece(2, Piece.RED);
        assertEquals(true, grid.checkDiagonal(3, 2, Piece.RED));
        assertEquals(true, grid.checkDiagonal(2, 1, Piece.RED));
        assertEquals(true, grid.checkDiagonal(1, 0, Piece.RED));

        assertEquals(false, grid.checkDiagonal(3, 1, Piece.RED));
        assertEquals(false, grid.checkDiagonal(0, 1, Piece.RED));
        assertEquals(false, grid.checkDiagonal(0, 2, Piece.RED));
        assertEquals(false, grid.checkDiagonal(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(2, 0, Piece.BLUE));

        assertEquals(false, grid.checkColumn(0, 1, Piece.RED));
        assertEquals(false, grid.checkColumn(0, 2, Piece.RED));
        assertEquals(false, grid.checkColumn(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(2, 0, Piece.BLUE));

        assertEquals(false, grid.checkRow(0, 1, Piece.RED));
        assertEquals(false, grid.checkRow(0, 2, Piece.RED));
        assertEquals(false, grid.checkRow(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(2, 0, Piece.BLUE));
    }

    @Test
    public void checkDiagonalTwoTest() {
        grid.placePiece(0, Piece.RED);
        grid.placePiece(2, Piece.BLUE);
        grid.placePiece(0, Piece.RED);
        grid.placePiece(1, Piece.BLUE);
        grid.placePiece(1, Piece.RED);
        grid.placePiece(2, Piece.BLUE);
        grid.placePiece(2, Piece.RED);

        assertEquals(true, grid.checkDiagonal(3, 0, Piece.RED));
        assertEquals(true, grid.checkDiagonal(2, 1, Piece.RED));
        assertEquals(true, grid.checkDiagonal(1, 2, Piece.RED));

        assertEquals(false, grid.checkDiagonal(3, 1, Piece.RED));
        assertEquals(false, grid.checkDiagonal(0, 1, Piece.RED));
        assertEquals(false, grid.checkDiagonal(0, 2, Piece.RED));
        assertEquals(false, grid.checkDiagonal(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkDiagonal(2, 0, Piece.BLUE));

        assertEquals(false, grid.checkColumn(0, 1, Piece.RED));
        assertEquals(false, grid.checkColumn(0, 2, Piece.RED));
        assertEquals(false, grid.checkColumn(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkColumn(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkColumn(2, 0, Piece.BLUE));

        assertEquals(false, grid.checkRow(0, 1, Piece.RED));
        assertEquals(false, grid.checkRow(0, 2, Piece.RED));
        assertEquals(false, grid.checkRow(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 1, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 2, Piece.BLUE));
        assertEquals(false, grid.checkRow(0, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(1, 0, Piece.BLUE));
        assertEquals(false, grid.checkRow(2, 0, Piece.BLUE));
    }
}
