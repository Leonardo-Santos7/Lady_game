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
    public boolean validateMovement(PositionCheckers origin, PositionCheckers destination) {
        Peace peace = board.getPeace(origin);
        if (peace == null || peace.isEmpty() || !peace.belongsToPlayer(playerActual.getNumber())) {
            return false;
        }

        if (!board.getPeace(destination).isEmpty()) {
            return false;
        }

        Movement movement = new Movement(origin, destination);


        if (!movement.isDiagonal()) {
            return false;
        }

        int rowDistance = origin.rowDistance(destination);

        if (peace.isChecker()) {

            return true;
        } else {

            if (rowDistance == 1) {
                return true;
            } else if (rowDistance == 2) {

                PositionCheckers middle = movement.getPositionCaptured();
                Peace midPeace = board.getPeace(middle);

                return !midPeace.isEmpty() && !midPeace.belongsToPlayer(playerActual.getNumber());
            } else {
                return false;
            }
        }
    }


    private void changePlayer(){
        playerActual = (playerActual == player1) ? player2 : player1;
    }

    @Override
    public void executeMovement(PositionCheckers destination, PositionCheckers origin) {

        Peace peaceAtDestination = board.getPeace(destination);
        if (peaceAtDestination != null && !peaceAtDestination.isEmpty()) {
            System.out.println("Movimento inválido: a casa de destino já está ocupada.");
            return;
        }

        Movement movement = new Movement(origin, destination);

        if (!movement.isDiagonal()) {
            System.out.println("Movimento inválido: apenas movimentos diagonais são permitidos.");
            return;
        }

        if (movement.isCapture()) {
            PositionCheckers mid = movement.getPositionCaptured();
            Peace middlePiece = board.getPeace(mid);

            if (middlePiece.isEmpty() || middlePiece.belongsToPlayer(playerActual.getNumber())) {
                System.out.println("Captura inválida: peça intermediária ausente ou é sua.");
                return;
            }
        }

        board.movementPeace(movement);
        changePlayer();
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
