package implementation.core;

import implementation.factories.PeaceFactory;
import implementation.model.*;

class RegularMovementExecutor extends MovementExecutor {
    public RegularMovementExecutor(Board board, Movement movement, Player player, CheckerLogicImp logic) {
        super(board, movement, player, logic);
    }

    @Override
    protected boolean isCapture() {
        return movement.isCapture();
    }

    @Override
    protected void handleCapture() {
        PositionCheckers mid = movement.getPositionCaptured();
        Peace middlePiece = board.getPeace(mid);

        if (!middlePiece.isEmpty() && !middlePiece.belongsToPlayer(player.getNumber())) {
            board.setPeace(mid, PeaceFactory.createEmpty());
            logic.incrementCapturedCount();
        }
    }

    @Override
    protected void promoteIfNeeded() {
        PositionCheckers destination = movement.getDestination();
        Peace movedPiece = board.getPeace(destination);

        if (!movedPiece.isChecker()) {
            if ((movedPiece.getPlayer() == 1 && destination.getRow() == 0) ||
                    (movedPiece.getPlayer() == 2 && destination.getRow() == 7)) {
                movedPiece.promoteToChecker();
            }
        }
    }
}
