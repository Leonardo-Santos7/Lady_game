package abstraction;

import implementation.core.CheckerLogic;
import implementation.model.PositionCheckers;

public abstract class CheckersEvent {
    protected CheckerLogic checkerLogic;

    public CheckersEvent(CheckerLogic checkerLogic){
        this.checkerLogic = checkerLogic;
    }

    public abstract void clickProcess(PositionCheckers positionCheckers);

    public abstract void movementProcess(int rowOrigin, int columnOrigin, int rowDestination, int columnDestination);

}
