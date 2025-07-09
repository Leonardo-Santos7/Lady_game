package abstraction;

public interface BoardViewer {
    void updateBoard();
    void highlightsHouse(int row, int column, boolean highlights);
    void messages(String message);

    interface BoardView {
        // Métodos para alternar entre as visualizações
        void showInitialMenu();
    }
}
