package amf.project.tictactoe;

/**
 * Describes the pieces of the game. Only 2 players.
 *
 */
public enum Piece {

    EMPTY("   "), RED(" O "), BLUE(" X ");

    private final String value;

    private Piece(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
