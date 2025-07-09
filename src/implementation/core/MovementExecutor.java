package implementation.core;

import implementation.model.Board;
import implementation.model.Movement;
import implementation.model.Player;

abstract class MovementExecutor {
    protected final Board board;
    protected final Movement movement;
    protected final Player player;
    protected final CheckerLogicImp logic;

    public MovementExecutor(Board board, Movement movement, Player player, CheckerLogicImp logic) {
        this.board = board;
        this.movement = movement;
        this.player = player;
        this.logic = logic;
    }

    public final void performMove() {
        if (!validateDestination()) return;
        if (!movement.isDiagonal()) return;

        if (isCapture()) {
            handleCapture();
        }

        board.movementPeace(movement);
        promoteIfNeeded();
        logic.changePlayer();
        logic.checkVictoryCondition();
    }

    protected abstract boolean isCapture();
    protected abstract void handleCapture();
    protected abstract void promoteIfNeeded();

    protected boolean validateDestination() {
        return board.getPeace(movement.getDestination()).isEmpty();
    }
}
