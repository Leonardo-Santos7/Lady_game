package implementation.core;

import implementation.core.CheckerLogicImp;
import implementation.factories.PeaceFactory;
import implementation.model.*;

class CheckerMovementExecutor extends MovementExecutor {
    public CheckerMovementExecutor(Board board, Movement movement, Player player, CheckerLogicImp logic) {
        super(board, movement, player, logic);
    }

    @Override
    protected boolean isCapture() {

        return true;
    }

    @Override
    protected void handleCapture() {
        int rowDiff = movement.getOrigin().rowDistance(movement.getDestination());
        int rowStep = (movement.getDestination().getRow() - movement.getOrigin().getRow()) / rowDiff;
        int colStep = (movement.getDestination().getColumn() - movement.getOrigin().getColumn()) / rowDiff;

        int r = movement.getOrigin().getRow() + rowStep;
        int c = movement.getOrigin().getColumn() + colStep;

        while (r != movement.getDestination().getRow() && c != movement.getDestination().getColumn()) {
            PositionCheckers pos = new PositionCheckers(r, c);
            Peace mid = board.getPeace(pos);

            if (!mid.isEmpty() && !mid.belongsToPlayer(player.getNumber())) {
                board.setPeace(pos, PeaceFactory.createEmpty());
                logic.incrementCapturedCount();
                break;
            }

            r += rowStep;
            c += colStep;
        }
    }

    @Override
    protected void promoteIfNeeded() {}
}
