package implementation.core;

import implementation.model.*;

public class CheckerLogicImp implements CheckerLogic {
    private Board board;
    private Player player1;
    private Player player2;
    private Player playerActual;

    private int player1CapturedPieces;
    private int player2CapturedPieces;
    private Integer winnerPlayerNumber = null;

    public CheckerLogicImp(){
        startGame();
    }

    private void startGame(){
        board = new Board();
        player1 = new Player(1);
        player2 = new Player(2);
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


        if (movement.isDiagonal()) {
            return false;
        }

        int rowDistance = origin.rowDistance(destination);

        if (peace.isChecker()) {
            if (movement.isDiagonal()) return false;

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

            int direction = destination.getRow() - origin.getRow();

            if ((playerActual.getNumber() == 1 && direction != -1 && direction != -2) ||
                    (playerActual.getNumber() == 2 && direction != 1 && direction != 2)) {
                return false;
            }

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


    void changePlayer(){
        playerActual = (playerActual == player1) ? player2 : player1;
    }

    @Override
    public void executeMovement(PositionCheckers destination, PositionCheckers origin) {
        Peace peace = board.getPeace(origin);

        if (peace == null || peace.isEmpty()) {
            System.out.println("Movimento inválido: sem peça na origem.");
            return;
        }

        Movement movement = new Movement(origin, destination);
        MovementExecutor executor = peace.isChecker()
                ? new CheckerMovementExecutor(board, movement, playerActual, this)
                : new RegularMovementExecutor(board, movement, playerActual, this);

        executor.performMove();
    }

    public boolean canCaptureAgain(PositionCheckers from) {
        Peace piece = board.getPeace(from);

        if (piece.isEmpty() || !piece.belongsToPlayer(playerActual.getNumber())) return false;

        int[][] directions = {
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] dir : directions) {
            int r = from.getRow() + dir[0];
            int c = from.getColumn() + dir[1];

            if (!new PositionCheckers(r, c).isValidate()) continue;

            Peace adjacent = board.getPeace(new PositionCheckers(r, c));
            if (adjacent.isEmpty() || adjacent.belongsToPlayer(playerActual.getNumber())) continue;

            int jumpRow = r + dir[0];
            int jumpCol = c + dir[1];
            PositionCheckers jumpPos = new PositionCheckers(jumpRow, jumpCol);

            if (jumpPos.isValidate() && board.getPeace(jumpPos).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public Movement findFirstCaptureMove(PositionCheckers from) {
        Peace piece = board.getPeace(from);
        if (piece == null || piece.isEmpty() || !piece.belongsToPlayer(playerActual.getNumber())) {
            return null;
        }

        int[][] directions = {
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] dir : directions) {
            int midRow = from.getRow() + dir[0];
            int midCol = from.getColumn() + dir[1];
            int destRow = from.getRow() + dir[0] * 2;
            int destCol = from.getColumn() + dir[1] * 2;

            PositionCheckers middlePos = new PositionCheckers(midRow, midCol);
            PositionCheckers destination = new PositionCheckers(destRow, destCol);

            if (!middlePos.isValidate() || !destination.isValidate()) continue;

            Peace middlePiece = board.getPeace(middlePos);
            Peace destinationPiece = board.getPeace(destination);

            boolean isEnemy = !middlePiece.isEmpty() && !middlePiece.belongsToPlayer(playerActual.getNumber());
            boolean isDestEmpty = destinationPiece.isEmpty();

            if (isEnemy && isDestEmpty) {
                return new Movement(from, destination);
            }
        }

        return null;
    }



    protected void incrementCapturedCount() {
        if (playerActual == player1) {
            player1CapturedPieces++;
        } else {
            player2CapturedPieces++;
        }
    }

    protected void checkVictoryCondition() {
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
