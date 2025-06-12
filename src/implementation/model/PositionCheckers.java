package implementation.model;

public class PositionCheckers {
    private int row;
    private int column;

    public PositionCheckers(int row, int column){
        this.row = row;
        this.column = column;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public int rowDistance(PositionCheckers other){
        return Math.abs(row - other.row);
    }
    public int columnDistance(PositionCheckers other){
        return Math.abs(column - other.column);
    }

    public boolean isDiagonalOf(PositionCheckers other){
        return Math.abs(row - other.row) == Math.abs(column - other.column);
    }

    public boolean isValidate(){
        return row >= 0 && row < 8 && column >= 0 && column < 8;
    }
}
