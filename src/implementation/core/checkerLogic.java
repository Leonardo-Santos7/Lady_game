package implementation.core;

import implementation.model.PositionCheckers;

public interface checkerLogic {
    boolean containsPeace(PositionCheckers positionPeace);

    boolean validateMovement(PositionCheckers positionPeace);


    void executeMovement(PositionCheckers positionDestination, PositionCheckers positionOrigin);

    void restartGame();

    int getPlayerActual();

    int getTypePeace(PositionCheckers positionCheckers);
}