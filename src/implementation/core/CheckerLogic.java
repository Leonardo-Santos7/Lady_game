package implementation.core;

import implementation.model.PositionCheckers;

public interface CheckerLogic {
    boolean containsPeace(PositionCheckers positionPeace);

    boolean validateMovement(PositionCheckers positionPeace);


    void executeMovement(PositionCheckers positionDestination, PositionCheckers positionOrigin);

    void restartGame();

    int getPlayerActual();

    int getTypePeace(PositionCheckers positionCheckers);

    // Novo metodo para obter o número de peças capturadas
    int getCapturedPieces(int playerNumber);
}