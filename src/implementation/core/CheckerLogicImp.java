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
    private Integer winnerPlayerNumber = null;

    public CheckerLogicImp(){
        startGame();
    }

    private void startGame(){
        board = new Board();
        player1 = new Player(1, "luigi");
        player2 = new Player(2, "Leonardo");
        playerActual = player1;

        player1CapturedPieces = 0;
        player2CapturedPieces = 0;

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
            if (!movement.isDiagonal()) return false;

            int rowDiff = origin.rowDistance(destination);
            int colDiff = origin.columnDistance(destination);

            int rowStep = (destination.getRow() - origin.getRow()) / rowDiff;
            int colStep = (destination.getColumn() - origin.getColumn()) / colDiff;

            int r = origin.getRow() + rowStep;
            int c = origin.getColumn() + colStep;

            boolean foundOpponent = false;

            while (r != destination.getRow() && c != destination.getColumn()) {
                PositionCheckers pos = new PositionCheckers(r, c);
                Peace mid = board.getPeace(pos);

                if (!mid.isEmpty()) {
                    if (mid.belongsToPlayer(playerActual.getNumber())) {
                        return false;
                    }
                    if (foundOpponent) return false;
                    foundOpponent = true;
                }

                r += rowStep;
                c += colStep;
            }

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
        Peace peace = board.getPeace(origin);

        if (peace == null || peace.isEmpty()) {
            System.out.println("Movimento inválido: sem peça na origem.");
            return;
        }

        if (!board.getPeace(destination).isEmpty()) {
            System.out.println("Movimento inválido: a casa de destino já está ocupada.");
            return;
        }

        Movement movement = new Movement(origin, destination);

        if (!movement.isDiagonal()) {
            System.out.println("Movimento inválido: apenas movimentos diagonais são permitidos.");
            return;
        }

        if (peace.isChecker()) {
            int rowDiff = origin.rowDistance(destination);
            int rowStep = (destination.getRow() - origin.getRow()) / rowDiff;
            int colStep = (destination.getColumn() - origin.getColumn()) / rowDiff;

            int r = origin.getRow() + rowStep;
            int c = origin.getColumn() + colStep;

            while (r != destination.getRow() && c != destination.getColumn()) {
                PositionCheckers pos = new PositionCheckers(r, c);
                Peace mid = board.getPeace(pos);

                if (!mid.isEmpty() && !mid.belongsToPlayer(playerActual.getNumber())) {
                    board.setPeace(pos, new Peace());
                    break;
                }

                r += rowStep;
                c += colStep;
            }
        } else if (movement.isCapture()) {
            PositionCheckers mid = movement.getPositionCaptured();
            Peace middlePiece = board.getPeace(mid);

            if (middlePiece.isEmpty() || middlePiece.belongsToPlayer(playerActual.getNumber())) {
                System.out.println("Captura inválida: peça intermediária ausente ou é sua.");
                return;
            }

            if (playerActual == player1) {
                player1CapturedPieces++;
            } else {
                player2CapturedPieces++;
            }

            board.setPeace(mid, new Peace());
        }

        board.movementPeace(movement);

        Peace movedPiece = board.getPeace(destination);
        if (!movedPiece.isChecker()) {
            if ((movedPiece.getPlayer() == 1 && destination.getRow() == 0) ||
                    (movedPiece.getPlayer() == 2 && destination.getRow() == 7)) {
                movedPiece.promoteToChecker();
            }
        }

        changePlayer();

        if (board.countPeace(player1.getNumber()) == 0) {
            winnerPlayerNumber = player2.getNumber();
        } else if (board.countPeace(player2.getNumber()) == 0) {
            winnerPlayerNumber = player1.getNumber();
        }
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

    @Override
    public void checkTimeVictory() {
        if (winnerPlayerNumber != null) return;

        int capturedByPlayer1 = player1CapturedPieces;
        int capturedByPlayer2 = player2CapturedPieces;

        if (capturedByPlayer1 > capturedByPlayer2) {
            winnerPlayerNumber = player1.getNumber();
        } else if (capturedByPlayer2 > capturedByPlayer1) {
            winnerPlayerNumber = player2.getNumber();
        } else {
            winnerPlayerNumber = 0;
        }
    }

    @Override
    public int getWinner() {
        return winnerPlayerNumber != null ? winnerPlayerNumber : -1; // -1 = jogo em andamento
    }

}
