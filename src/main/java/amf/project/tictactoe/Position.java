package amf.project.tictactoe;

import java.util.ArrayList;
import java.util.List;

/**
 * Class meant to give the next position depending on the given direction.
 *
 */
public class Position {

    private int i, j, row, column;
    private int k, l;
    private Direction direction;
    private List<Condition> conditions;
    
    public Position(int row, int column, int i, int j, Direction direction) {
        this.i = i;
        this.j = j;
        this.k = column;
        this.l = column +1;
        this.row = row;
        this.column = column;
        this.direction = direction;
    }
    
    public List<Condition> getConditions(int maxRows, int maxColumns) {
        List<Condition> list = new ArrayList<Condition>();
        switch(direction) {
            case COLUMN: 
                list.add(new Condition(i >= 0, i, column));
                list.add(new Condition(j < maxRows, j, column));break;
            case ROW:
                list.add(new Condition(i >= 0, row, i));
                list.add(new Condition(j < maxColumns, row, j)); break;
            case DIAGONALONE: 
                list.add(new Condition(i >= 0 && k >= 0, i, k));
                list.add(new Condition(j < maxRows && l < maxColumns, j, l));break;
            case DIAGONALTWO:
                list.add(new Condition(k >= 0 && i < maxRows, i, k));
                list.add(new Condition(j >= 0 && l < maxColumns, j, l)); break;
        }
        return list;
    }

    public void incOrDec() {
        switch(direction) {
            case COLUMN:
                i--;
                j++;
                break;
            case ROW:
                i--;
                j++;
                break;
            case DIAGONALONE:
                i--;
                k--;
                j++;
                l++;
                break;
            case DIAGONALTWO:
                i++;
                k--;
                j--;
                l++;
                break;
        }
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
