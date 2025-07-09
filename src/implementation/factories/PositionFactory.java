package implementation.factories;

import implementation.model.PositionCheckers;

public class PositionFactory {

    public static PositionCheckers fromCoordinates(int row, int column) {
        return new PositionCheckers(row, column);
    }

    public static PositionCheckers fromNotation(String notation) {
        if (notation == null || notation.length() != 2) return null;

        char colChar = Character.toUpperCase(notation.charAt(0));
        int rowNum = Character.getNumericValue(notation.charAt(1));

        int col = colChar - 'A';
        int row = 8 - rowNum;

        return new PositionCheckers(row, col);
    }

    public static PositionCheckers fromUIPosition(int pixelX, int pixelY, int cellSize) {
        int row = pixelY / cellSize;
        int column = pixelX / cellSize;
        return new PositionCheckers(row, column);
    }
}
