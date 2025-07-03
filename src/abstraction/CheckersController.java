package abstraction;

import implementation.core.CheckerLogic;
import implementation.model.PositionCheckers;

public class CheckersController extends CheckersEvent{
    private BoardViewer boardViewer;
    private boolean selectedPeace = false;
    private int rowSelected = -1;
    private int columnSelected = -1;

    public CheckersController(CheckerLogic checkerLogic, BoardViewer boardViewer) {
        super(checkerLogic);
        this.boardViewer = boardViewer;
    }

    @Override
    public void clickProcess(PositionCheckers positionCheckers) {

        if(!selectedPeace){
            if(checkerLogic.containsPeace(positionCheckers)){
                selectedPeace = true;
                rowSelected = positionCheckers.getRow();
                columnSelected = positionCheckers.getColumn();
                boardViewer.highlightsHouse(positionCheckers.getRow(), positionCheckers.getColumn(), true);
            }
        } else {
            if(positionCheckers.getRow() == rowSelected && positionCheckers.getColumn() == columnSelected){
                boardViewer.highlightsHouse(rowSelected, columnSelected, false);
                selectedPeace = false;
                rowSelected = -1;
                columnSelected = -1;
            } else {
                movementProcess(rowSelected, columnSelected, positionCheckers.getRow(), positionCheckers.getColumn());
            }
        }
    }

    @Override
    public void movementProcess(int rowOrigin, int columnOrigin, int rowDestination, int columnDestination) {
        PositionCheckers origin = new PositionCheckers(rowOrigin, columnOrigin);
        PositionCheckers destination = new PositionCheckers(rowDestination, columnDestination);

        if(checkerLogic.validateMovement(origin, destination)){

            checkerLogic.executeMovement(destination, origin);
            boardViewer.updateBoard();
            boardViewer.highlightsHouse(rowSelected, columnSelected, false);


            if(destination.getRow() - origin.getRow() == 2){
                int rowCap = (origin.getRow() + destination.getRow() / 2);
                int columnCap = (origin.getColumn() + destination.getColumn() / 2);
                // processCapute(rowCap, columnCap);
            }

            selectedPeace = false;
            rowSelected = -1;
            columnSelected = -1;
        }

    }

    public CheckerLogic getCheckerLogic() {
        return checkerLogic;
    }

}
