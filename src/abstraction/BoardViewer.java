package abstraction;

public interface BoardViewer {
    void updateBoard();
    void highlightsHouse(int row, int column, boolean highlights);
    void messages(String message);
}
