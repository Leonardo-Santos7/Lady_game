package implementation.core;

import implementation.model.*;

public class CheckerLogicImp implements CheckerLogic {
    private Board board;
    private Player player1;
    private Player player2;
    private Player playerActual;

    public CheckerLogicImp(){
        startGame();
    }

    private void startGame(){
        board = new Board();
        player1 = new Player(1, "luigi");
        player2 = new Player(2, "Leonardo");
        playerActual = player1;

        board.start();
    }

    @Override
    public boolean containsPeace(PositionCheckers positionPeace) {
        Peace peace = board.getPeace(positionPeace);

        if(peace == null){
            return false;
        }

        return peace.getPlayer() == playerActual.getNumber();
    }

    @Override
    public boolean validateMovement(PositionCheckers positionCheckers) {
        return true;
    }

    @Override
    public void executeMovement(PositionCheckers positionDestination, PositionCheckers positionOrigin) {
       // boolean isCaputure = positionOrigin.rowDistance(positionDestination) == 2;

       // PositionCheckers capturedPosition = null;

        Movement movement = new Movement(positionOrigin, positionDestination);

        board.movementPeace(movement);
    }

    @Override
    public void restartGame() {
        startGame();
    }



    @Override
    public int getPlayerActual() {
        return playerActual.getNumber()     ;
    }

    @Override
    public int getTypePeace(PositionCheckers positionCheckers) {
        Peace peace =  board.getPeace(positionCheckers);

        Peace.Type type = peace.getType();

        return type.getValue();

    }
}
