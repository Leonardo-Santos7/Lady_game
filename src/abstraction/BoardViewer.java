package abstraction;

import implementation.core.CheckerLogic;
import implementation.model.PositionCheckers;

public abstract class BoardViewer {
    protected CheckerLogic checkerLogic;

    public BoardViewer(CheckerLogic checkerLogic){
        this.checkerLogic = checkerLogic;
    }

    public abstract void updateBoard();
    public abstract void highlightsHouse(int row, int column, boolean highlights);
    public abstract void messages(String messages);
}
