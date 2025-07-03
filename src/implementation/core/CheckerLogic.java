package implementation.core;

import implementation.model.PositionCheckers;

public interface CheckerLogic {
    boolean containsPeace(PositionCheckers positionPeace);

    boolean validateMovement(PositionCheckers origin, PositionCheckers destination);


    void executeMovement(PositionCheckers positionDestination, PositionCheckers positionOrigin);

    void restartGame();

    int getPlayerActual();

    int getTypePeace(PositionCheckers positionCheckers);
}