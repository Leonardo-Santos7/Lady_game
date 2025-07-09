package implementation.core;

import implementation.model.*;

public class CheckerLogicImp implements CheckerLogic {
    private Board board;
    private Player player1;
    private Player player2;
    private Player playerActual;

    // Novos campos para contagem de peças capturadas
    private int player1CapturedPieces;
    private int player2CapturedPieces;

    public CheckerLogicImp(){
        startGame();
    }

    private void startGame(){
        board = new Board();
        player1 = new Player(1, "luigi");
        player2 = new Player(2, "Leonardo");
        playerActual = player1;

        player1CapturedPieces = 0; // Inicializa contagem de peças capturadas
        player2CapturedPieces = 0; // Inicializa contagem de peças capturadas

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
        Peace peace = board.getPeace(positionCheckers);

        return peace != null && !peace.isEmpty() && peace.belongsToPlayer(playerActual.getNumber());
    }

    private void changePlayer(){
        playerActual = (playerActual == player1) ? player2 : player1;
    }

    @Override
    public void executeMovement(PositionCheckers positionDestination, PositionCheckers positionOrigin) {
        Movement movement = new Movement(positionOrigin, positionDestination);

        if(!movement.isDiagonal()) {
            System.out.println("Movimento invalido");
            return;
        }

        // Verifica se houve captura
        if(movement.isCapture()){
            Peace capturedPeace = board.getPeace(movement.getPositionCaptured());
            if(capturedPeace != null && !capturedPeace.isEmpty()){
                if(capturedPeace.belongsToPlayer(player1.getNumber())){
                    player2CapturedPieces++; // Jogador 2 capturou peça do jogador 1
                } else if (capturedPeace.belongsToPlayer(player2.getNumber())){
                    player1CapturedPieces++; // Jogador 1 capturou peça do jogador 2
                }
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
        return playerActual.getNumber();
    }

    @Override
    public int getTypePeace(PositionCheckers positionCheckers) {
        Peace peace =  board.getPeace(positionCheckers);

        Peace.Type type = peace.getType();

        return type.getValue();

    }

    @Override
    public int getCapturedPieces(int playerNumber) {
        if (playerNumber == player1.getNumber()) {
            return player1CapturedPieces;
        } else if (playerNumber == player2.getNumber()) {
            return player2CapturedPieces;
        }
        return 0;
    }
}
