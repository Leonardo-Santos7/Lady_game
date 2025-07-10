package implementation.core;

import implementation.model.*;

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
        if (movement.isDiagonal()) return;

        if (isCapture()) {
            handleCapture();
            board.movementPeace(movement);
            promoteIfNeeded();

            if (logic.canCaptureAgain(movement.getDestination())) {
                tryMultipleCaptures(movement.getDestination());
            } else {
                logic.changePlayer();
            }

            logic.checkVictoryCondition();
            return;
        }

        board.movementPeace(movement);
        promoteIfNeeded();
        logic.changePlayer();
        logic.checkVictoryCondition();
    }



    protected void tryMultipleCaptures(PositionCheckers currentPosition) {
        while (logic.canCaptureAgain(currentPosition)) {
            Movement next = logic.findFirstCaptureMove(currentPosition);
            if (next == null) break;

            Peace piece = board.getPeace(currentPosition);
            MovementExecutor executor = piece.isChecker()
                    ? new CheckerMovementExecutor(board, next, player, logic)
                    : new RegularMovementExecutor(board, next, player, logic);

            executor.performMove();
            currentPosition = next.getDestination();
        }
    }


    protected abstract boolean isCapture();
    protected abstract void handleCapture();
    protected abstract void promoteIfNeeded();

    protected boolean validateDestination() {
        return board.getPeace(movement.getDestination()).isEmpty();
    }
}
