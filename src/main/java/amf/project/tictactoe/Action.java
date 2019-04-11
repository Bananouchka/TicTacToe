package amf.project.tictactoe;

public enum Action {

    START(-2), RESTART(-4), EXIT(-3), PRINTHELP(-1);

    private final int id;

    Action(int id) {
        this.id = id;
    }

    public static Action fromInteger(int x) {
        switch (x) {
        case -1:
            return PRINTHELP;
        case -2:
            return START;
        case -3:
            return EXIT;
        case -4:
            return RESTART;
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
