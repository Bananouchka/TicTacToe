package amf.project.tictactoe;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TicTacToeGameTest {

    private TicTacToeGame tictactoe;
    private InputStream expectedInput;
    private ByteArrayOutputStream expectedOuput;
    private Grid grid;

    @Before
    public void init() {
        expectedOuput = new ByteArrayOutputStream();
        tictactoe = Mockito.spy(new TicTacToeGame());
//        System.setOut(new PrintStream(expectedOuput));
    }

    @After
    public void finalize() throws IOException {
        expectedOuput.flush();
    }

    @Test
    public void startGameSuccessWithProperParamTest() throws IOException {
        expectedInput = new ByteArrayInputStream("2\n2\n2\n1\n0\n0\n0\n0\n1\nN".getBytes());
        tictactoe.setScanner(expectedInput);
        tictactoe.startGame();
        Mockito.verify(tictactoe, Mockito.times(1)).setGrid(2, 2, 2);
        Mockito.verify(tictactoe, Mockito.times(1)).playGrid();
        Mockito.verify(tictactoe, Mockito.times(1)).endGame();
    }

    @Test
    public void startGameSuccessWithWrongParamTest() throws IOException {
        expectedInput = new ByteArrayInputStream("a\n2\n2\n2\n1\n0\n0\n0\n0\n1\nN".getBytes());
        tictactoe.setScanner(expectedInput);
        tictactoe.startGame();
        Mockito.verify(tictactoe, Mockito.times(1)).setGrid(2, 2, 2);
        Mockito.verify(tictactoe, Mockito.times(1)).playGrid();
        Mockito.verify(tictactoe, Mockito.times(1)).endGame();
    }

    @Test
    public void startGameSuccessWithPlayAgainTest() throws IOException {
        expectedInput = new ByteArrayInputStream("2\n2\n2\n1\n0\n0\n0\n0\n1\nY\n2\n2\n2\n1\n0\n0\n0\n0\n1\nN".getBytes());
        tictactoe.setScanner(expectedInput);
        tictactoe.startGame();
        Mockito.verify(tictactoe, Mockito.times(2)).setGrid(2, 2, 2);
        Mockito.verify(tictactoe, Mockito.times(2)).playGrid();
        Mockito.verify(tictactoe, Mockito.times(1)).endGame();
    }

    @Test
    public void playGridSuccessTest() throws IOException {
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\n2\n1\n0\n0\n2\n2\nN".getBytes());
        setMocks();
        int exit = tictactoe.playGrid();
        assertEquals(1, exit);
        Mockito.verify(tictactoe, Mockito.times(1)).hasWon(0, 0);
        Mockito.verify(tictactoe, Mockito.times(1)).hasWon(1, 0);
        Mockito.verify(tictactoe, Mockito.times(1)).hasWon(2, 0);
        Mockito.verify(tictactoe, Mockito.times(1)).hasWon(2, 1);
        Mockito.verify(tictactoe, Mockito.times(1)).hasWon(2, 2);
    }

    @Test
    public void playGridSuccessWithExitInputKeyTest() throws IOException {
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\n-3".getBytes());
        setMocks();
        int exit = tictactoe.playGrid();
        assertEquals(1, exit);
        Mockito.verify(tictactoe, Mockito.times(1)).doChoice(Action.EXIT);
        Mockito.verify(grid, Mockito.times(2)).printCurrentGrid();
    }
    
    @Test
    public void playGridWithRestartKeyTest() throws IOException {
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\n-4".getBytes());
        setMocks();
        int exit = tictactoe.playGrid();
        assertEquals(1, exit);
        Mockito.verify(tictactoe, Mockito.times(1)).doChoice(Action.RESTART);
        Mockito.verify(tictactoe, Mockito.times(1)).resetGame();
    }

    @Test
    public void playGridSuccessWithOneWrongInputTypeTest() throws IOException {
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\nfds\n2\n1\n0\n0\n2\n2\nN".getBytes());
        setMocks();
        int exit = tictactoe.playGrid();
        assertEquals(1, exit);
        Mockito.verify(grid, Mockito.times(6)).printCurrentGrid();
    }

    @Test
    public void playGridSuccessWithReplayTest() throws IOException {
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\n2\n1\n0\n0\n-2\nY".getBytes());
        setMocks();
        int exit = tictactoe.playGrid();
        assertEquals(1, exit);
        Mockito.verify(grid, Mockito.times(4)).printCurrentGrid();
    }

    @Test
    public void hasWonSuccessTest() {
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\n2\n1\n0\n0\n2\n2\nY".getBytes());
        setMocks();
        int exit = tictactoe.playGrid();
        assertEquals(0, exit);
        assertEquals(true, tictactoe.hasWon(2, 2));
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\n2\n2\n2\n1\n1\n1\n1\n2\n0\n0\nY".getBytes());
        setMocks();
        exit = tictactoe.playGrid();
        assertEquals(0, exit);
        assertEquals(true, tictactoe.hasWon(1, 1));
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\n2\n2\n2\n1\n1\n1\n1\n2\n0\n2\nY".getBytes());
        setMocks();
        exit = tictactoe.playGrid();
        tictactoe.getGrid().printCurrentGrid();
        assertEquals(0, exit);
        assertEquals(true, tictactoe.hasWon(1, 1));
        expectedInput = new ByteArrayInputStream("2\n0\n2\n1\n2\n2\n1\n1\n1\n0\n0\n1\nY".getBytes());
        setMocks();
        exit = tictactoe.playGrid();
        assertEquals(0, exit);
        assertEquals(true, tictactoe.hasWon(1, 1));
    }

    @Test
    public void hasWonFalseTest() {
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\n2\n1\n0\n0\n2\n2\nY".getBytes());
        setMocks();
        int exit = tictactoe.playGrid();
        assertEquals(0, exit);
        assertEquals(false, tictactoe.hasWon(1, 1));
        expectedInput = new ByteArrayInputStream("2\n0\n2\n1\n2\n2\n1\n1\n1\n0\n0\n1\nY".getBytes());
        setMocks();
        exit = tictactoe.playGrid();
        assertEquals(0, exit);
        assertEquals(false, tictactoe.hasWon(0, 0));
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\n2\n2\n2\n1\n1\n1\n1\n2\n0\n0\nY".getBytes());
        setMocks();
        exit = tictactoe.playGrid();
        assertEquals(0, exit);
        assertEquals(false, tictactoe.hasWon(2, 0));
        expectedInput = new ByteArrayInputStream("2\n0\n1\n0\n2\n2\n2\n1\n1\n1\n1\n2\n0\n2\nY".getBytes());
        setMocks();
        exit = tictactoe.playGrid();
        assertEquals(0, exit);
        assertEquals(false, tictactoe.hasWon(2, 2));
    }

    // TEST HELPERS

    public void setMocks() {
        grid = Mockito.spy(new Grid(3, 3, 3));
        tictactoe.setScanner(expectedInput);
        tictactoe.setGrid(grid);
    }
}
