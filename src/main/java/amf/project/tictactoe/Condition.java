package amf.project.tictactoe;

public class Condition {
    
    private boolean isValid;
    private int i;
    private int j;
    
    public Condition(boolean isValid, int i, int j) {
        this.setI(i);
        this.setJ(j);
        this.setValid(isValid);
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
    
}
